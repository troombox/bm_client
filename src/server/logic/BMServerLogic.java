package server.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import server.db_logic.BusinessClientDBController;
import server.db_logic.BusinessDBController;
import server.db_logic.ClientDBController;
import server.db_logic.DBController;
import server.db_logic.DishesDBController;
import server.db_logic.HRDBController;
import server.db_logic.OpenReoprtDBController;
import server.db_logic.OrderDBController;
import server.db_logic.RestaurantDBController;
import server.db_logic.SupplierDBController;
import server.db_logic.UserDBController;
import server.exceptions.BMServerException;
import server.gui.ServerMainWindowController;
import utility.entity.Business;
import utility.entity.BusinessClientData;
import utility.entity.Client;
import utility.entity.ClientRefundsData;
import utility.entity.Dish;
import utility.entity.Order;
import utility.entity.Restaurant;
import utility.entity.Supplier;
import utility.entity.User;
import utility.enums.DataType;
import utility.enums.ErrorType;
import utility.enums.OrderType;
import utility.enums.RequestType;
import utility.message_parsers.MessageParser;
import utility.message_parsers.MessageParserBranchManager;
import utility.message_parsers.MessageParserBusinessClientData;
import utility.message_parsers.MessageParserClientRefundData;
import utility.message_parsers.MessageParserError;
import utility.message_parsers.MessageParserHR;
import utility.message_parsers.MessageParserOrder;
import utility.message_parsers.MessageParserTextString;
import utility.message_parsers.MessageParserUser;
import utility.message_parsers.MessegeParserDishes;
import utility.message_parsers.MessegeParserRestaurants;



/**
 * The Class BMServerLogic take care of requests from client
 * for get data back or for changing data in db.
 */
public class BMServerLogic extends AbstractServer {

	/** The gui controller. */
	ServerMainWindowController guiController;

	/** The db controller. */
	DBController dbController;
	
	/** The order DB controller. */
	OrderDBController orderDBController;
	
	/** The user DB controller. */
	UserDBController userDBController;
	
	/** The business DB controller. */
	BusinessDBController businessDBConteroller;
	
	/** The client DB controller. */
	ClientDBController clientDBController;
	
	/** The supplier DB controller. */
	SupplierDBController supplierDBController;
	
	/** The restaurant DB controller. */
	RestaurantDBController restaurantDBController;
	
	/** The hr DB controller. */
	HRDBController hrDBController;
	
	/** The dishes DB controller. */
	DishesDBController dishesDBController;
	
	/** The open reoprt DB controller. */
	OpenReoprtDBController openReoprtDBController;
	
	/** The bud DB controller. */
	BusinessClientDBController budDBController;
	
	/** The shared delivery. */
	Thread sharedDelivery;
	
	/** The thread. */
	Thread thread;
//	 ReportsDBController reportDBCOntroller;
	
	
	/** The flag file incoming. */
boolean flagFileIncoming = false;
	
	/** The file sender client. */
	ConnectionToClient fileSenderClient = null;

	/**
	 * Constructor initialize all db controllers and one thread
	 * for creation of forms. Scheduler is thread that run when the 
	 * server is run and his responsibility is to create the forms
	 * once in a month, after 24 hours we check if today is the 1th 
	 * day the month and if yes we create the report.
	 *  SharedDeliveryThread is thread for take care of Shared delivery.
	 *
	 * @param port the port
	 * @param dbName the db name
	 * @param dbUser the db user
	 * @param dbPassword the db password
	 * @throws Exception the exception
	 */
	public BMServerLogic(int port, String dbName, String dbUser, String dbPassword) throws Exception {
		super(port);

		// Initializing controllers used on server side:
		this.dbController = DBController.getDBControllerInstanceFor(dbName, dbUser, dbPassword);
		dbController.connectToDBServer();
		if (!dbController.isConnected()) {
			// error connecting to db, no reason to go on. TODO: change error handling
			System.out.println("Error Connecting to DB, can't init Server logic");
			throw new Exception("Can't Connect To DB, Server not started");
		}
		this.orderDBController = new OrderDBController(dbController);
		this.userDBController = new UserDBController(dbController);
		this.clientDBController = new ClientDBController(dbController);
		this.supplierDBController = new SupplierDBController(dbController);
		this.restaurantDBController = new RestaurantDBController(dbController);
		this.hrDBController = new HRDBController(dbController);
		this.dishesDBController = new DishesDBController(dbController);
		this.businessDBConteroller = new BusinessDBController(dbController);
		this.openReoprtDBController = new OpenReoprtDBController(dbController);
		this.budDBController = new BusinessClientDBController(dbController);
		userDBController.setAllUsersToLoggedOut();
	    thread = new Thread(new Scheduler(dbController));
	    sharedDelivery = new Thread(new SharedDeliveryThread(orderDBController,null));
	    thread.start();
	}

	/**
	 * Handle message from client.
	 * Getting the message from client and handle it 
	 * according to the request type of the message. using general message 
	 * parser for getting the request type of the message.
	 * Each case handle different request from server, there are many 
	 * cases that take care of some purpose. handling the request with rihgt method.
	 *
	 * @param msg the msg
	 * @param client the client
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
//		//TEST:----------------
//			if(fileIncoming == true && client == clientSendingFile) {
//				//take care of file
//				//filenIncoming = false;
//				//return;
//			}
//		//-*------------------
		
		// we are assuming message is ArrayList<String>
		RequestType actionRequired = MessageParser.parseMessage_RequestType(msg);
		if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA) {
			// if client requests data - we call the request function to handle
			handleGetRequest(actionRequired, msg, client);
		} else if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_WRITE_NEW_TO_DB
				|| actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB) {
			// if client wants to write data - we call the write function to handle
			handleWriteRequest(actionRequired, msg, client);
		} else if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_CONNECTION_STATUS) {
			handleConnectionRequest(actionRequired, msg, client);
		} else if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_DEBUG_MESSAGE) {
			// this is a debug case
			handleDebugRequest(actionRequired, msg, client);
		} else if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_LOGIN_REQUEST
				|| actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_LOGOUT_REQUEST) {
			handleLoginRequest(actionRequired, msg, client);
		} else if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_W4C_REQUEST) {
			handleW4CRequest(actionRequired, msg, client);
		} else if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_CLIENT) {
			handleRegisterClientRequest(actionRequired, msg, client);
		} else if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_SUPPLIER) {
			handleRegisterSupplierRequest(actionRequired, msg, client);
		} else if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_SEARCH_RESTAURANT_REQUEST) {
			handleSearchRequest(actionRequired, msg, client);
		} else if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_INCOMING_FILE) {
			handleFileIncomingRequest(actionRequired, msg, client);
		}else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_CATEGORY_RESTAURANT_REQUEST) {
			handleGetRestaurantsByCategoryRequest(actionRequired, msg, client);
		}else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_MENU_REQUEST) {
			handleGetMenuRequest(actionRequired, msg, client);
		}else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_BUSINESS_CLIENT_DATA 
				|| actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_BUSINESS_CLIENT_BUDGED_UPDATE) {
			handleGetBusinessClientData(actionRequired, msg, client);
		}else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_GET_CLIENT_REFUNDS_DATA 
				|| actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_UPDATE_REFUND_AMOUNT_AFTER_REFUNDS_USED) {
			handleCRFRequest(actionRequired, msg, client);
		}else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA_BUSINESSES_NAMES) {
			handleGetDataOfBusiness(actionRequired, msg, client);
		}else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_APPROVE_BUSINESS) {
			handleApproveBusiness(actionRequired, msg, client);
		}else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA_CLIENT_INFO) {
			handleGetDataOfClient(actionRequired, msg, client);
		}else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_CHANGE_PERMISSION) {
			handleChangePermission(actionRequired, msg, client);
		}else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_OPEN_REPORT) {
			handleOpenReportRequest(actionRequired, msg, client);
		}else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_SEND_REPORT_TO_CEO) {
			handleSendReportToCEO(actionRequired, msg, client);
		}else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_APRROVE_BUSINESS) {
            handleApproveBusinessRequest(actionRequired,msg,client);
        }
        else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_CHECK_APRROVE_BUSINESS) {
            handleCheckApproveBusinessRequest(actionRequired,msg,client);
        }
        else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_GET_APPROVED_BUSINESS_CLIENTS) {
            handleGetRequest(actionRequired, msg, client);
        }else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_GET_RESTAURANT_BY_SUPPLIER_REQUEST) {
            handleGetResBySupplierRequest(actionRequired, msg, client);
        }else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_ADD_DISH_TO_MENU_REQUEST) {
            handleAddDishToMenuRequest(actionRequired, msg, client);
        }else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_UPDATE_DISH_IN_MENU_REQUEST) {
            handleUpdateDishInMenuRequest(actionRequired, msg, client);
        }else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_DELETE_DISH_FROM_MENU_REQUEST) {
            handleDeleteDishFromMenuRequest(actionRequired, msg, client);
        }else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST) {
            handleGetOrdersFromSupplierRequest(actionRequired, msg, client);
        } else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_SUPPLIER_CANCEL_ORDER) {
            handleCancelOrder(actionRequired, msg, client);
        } else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_SUPPLIER_UPDATE_ORDER) {
            handleOrdersStatus(actionRequired, msg, client);
        }else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_CEO_QUARTERLY_REPORT) {
        	handleQuarterlyReportRequest(actionRequired, msg, client);
        }else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_CEO_GET_BRANCHES_REPORTS) {
        	handleGetReportFromCEO(actionRequired, msg, client);
        }else if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_OPEN_REPORT_FROM_BRANCH) {
        	handleOpenReportFromBranch(actionRequired, msg, client);
        }else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_OPEN_SUPPLIER_INCOME_REPORT) {
        	handleOpenIncomeReportForSupplier(actionRequired, msg, client);
        }else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_NEW_BUSINESS) {
            handleApproveBusinessRequest(actionRequired,msg,client);
        }
		serverPrintToGuiLog("Message From Client Handled, action: " + actionRequired.toString(), true);
	}

	
	

	


	/**
	 * Send message to given client.
	 *
	 * @param msg the msg
	 * @param client the client
	 */
	public void sendMessageToGivenClient(Object msg, ConnectionToClient client) {
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			// TODO: HANDLE ERROR
			e.printStackTrace();
		}
	}

	/**
	 * Server started.
	 */
	protected void serverStarted() {
		serverPrintToGuiLog("Server listening for connections on port " + getPort(), true);
	}

	/**
	 * Server stopped.
	 */
	protected void serverStopped() {
		serverPrintToGuiLog("Server has stopped listening for connections.", true);
	}

	/**
	 * Client connected.
	 *
	 * @param client the client
	 */
	protected void clientConnected(ConnectionToClient client) {
		serverPrintToGuiNumberOfClients(getNumberOfClients());
		serverPrintToGuiLog("Client Connected: " + client.toString(), true);
	}


	/**
 * Client disconnected non abstract.
 *
 * @param client the client
 */
protected void clientDisconnectedNonAbstract(ConnectionToClient client) {
		serverPrintToGuiLog("Client Disconnected: " + client.toString(), true);
		int numOfClients = getNumberOfClients() - 1;
		serverPrintToGuiNumberOfClients(numOfClients);
	}

	// ---------------------- HELPER FUNCTIONS --------------------------

	/**
	 * Handle write new order request to db.
	 * If this is first order asked for shred delivery we start thread 
	 * sharedDelivery that take care of it.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleWriteRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		DataType messageDataType = MessageParser.parseMessage_DataType(msg);
		switch (messageDataType) {
		case ORDER:
			Order orderData = MessageParserOrder.handleMessageExtractDataType_Order(msg);
			if(orderData.getTypeOfOrder() == OrderType.SHARED.toString()) {
				if(!sharedDelivery.isAlive()) { // thread dosen't alive, mean this is the first shred order delivery  
					sharedDelivery = new Thread(new SharedDeliveryThread(orderDBController,orderData));
					sharedDelivery.start();
				}
			}
			orderDBController.writeOrderDataToDB(orderData);//method for write new order in db 
			break;
		case HR_MANAGER:
			boolean response = HRDBController.updateUsersInDB((ArrayList<String>)msg);
			if(response == false) {//handle error message 
				sendMessageToGivenClient(ErrorType.COULD_NOT_UPDATE_BUSINESS_CLIENT,client);
			}
			break;
		case UNKNOWN:
			
		default:
			
		}
		
	}

	/**
	 * Handle get data request from db.
	 * Get data about business clients ask for approve.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleGetRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		DataType messageDataType = MessageParser.parseMessage_DataType(msg);
		switch (messageDataType) {
//		case ORDER:
//old code was removed
//			break;
		case HR_MANAGER:
            if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_GET_APPROVED_BUSINESS_CLIENTS) {
                ArrayList<String> gotFromClient = MessageParserHR.handleMessageFromClient_HRGetData(msg);
                Object response = HRDBController.getApprovedUsers(Integer.parseInt(gotFromClient.get(2)));
                sendMessageToGivenClient(response,client);
            }
            else {
                ArrayList<String> gotFromClient = MessageParserHR.handleMessageFromClient_HRGetData(msg);
                Object response = HRDBController.getUsersToApproveFromDB(Integer.parseInt(gotFromClient.get(2)));
                sendMessageToGivenClient(response,client);
            }
			break; 
		case UNKNOWN:
			
		default:
		
		}
	}

	/**
	 * Handle connection request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleConnectionRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		DataType messageDataType = MessageParser.parseMessage_DataType(msg);
		switch (messageDataType) {
		case SINGLE_TEXT_STRING:
			String message = MessageParserTextString.handleMessageExtractDataType_SingleTextString(msg);
			if (message.equals("disconnected")) {
				try {
					clientDisconnectedNonAbstract(client);
					client.close();
				} catch (IOException e) {
					// TODO: add error handling
					e.printStackTrace();
				}
			}
			break;
		default:
		
		}
	}

	/**
	 * Handle login request.
	 * Getting user from client for log in process.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleLoginRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		User user = MessageParserUser.handleMessageExtractDataType_User(msg);//parse the given data to user 
		if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_LOGIN_REQUEST) {
			Object response;
			try {
				User result = userDBController.authenticateAndGetFullUserData(user);//method for login process
				userDBController.setUserToLoggedIn(result);
				response = MessageParserUser.prepareMessageWithDataType_User(result,
						RequestType.SERVER_MESSAGE_TO_CLIENT_LOGIN_SUCCESS);//response for success process
				sendMessageToGivenClient(response, client);
			} catch (BMServerException e) { // handle error from login process
				response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
						e.getMessage());
				sendMessageToGivenClient(response, client);
			}
		} else if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_LOGOUT_REQUEST) {
			userDBController.setUserToLoggedOut(user);
		}
	}

	/**
	 * Handle W4C request.
	 * Get client data using w4c. 
	 *
	 * @param actionRequired the action required
	 * @param msg is the message
	 * @param client
	 */
	private void handleW4CRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		User user = MessageParserUser.handleMessageExtractDataType_User(msg);
		if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_W4C_REQUEST) {
			Object response;
			try {
				User result = userDBController.getCodesAccordingToW4C(user);//method for getting the data from db 
				response = MessageParserUser.prepareMessageWithDataType_User(result,
						RequestType.SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED);
				sendMessageToGivenClient(response, client);
			} catch (BMServerException e) {//handle error
				response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
						e.getMessage());
				sendMessageToGivenClient(response, client);
			}
		}
	}

	/**
	 * Handle register new client request.
	 * 
	 *
	 * @param actionRequired the action required
	 * @param msg the message
	 * @param client
	 */
	private void handleRegisterClientRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		Client newClient = MessageParserBranchManager.handleMessageExtractDataType_Client(msg);//parser the nessage for get client for registration 
		System.out.println(newClient.getBusinessId());
		if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_CLIENT) {
			Object response;
			try {
				// newClient
				clientDBController.setNewClient(newClient);
				response = MessageParserBranchManager.prepareMessageWithResultOfRegisterion_Client(
						RequestType.SERVER_MESSAGE_TO_CLIENT_REGISTER_SUCCESS);//response about success process
				sendMessageToGivenClient(response, client);
				
			} catch (BMServerException e) {//handle error 
				response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
						e.getMessage());
				sendMessageToGivenClient(response, client);
			}
		}
	}

	/**
	 * Handle register new supplier request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleRegisterSupplierRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		Supplier newSupplier = MessageParserBranchManager.handleMessageExtractDataType_Supplier(msg);//parser the message for get supplier for registration
		if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_SUPPLIER) {
			Object response;
			try {
				supplierDBController.setNewSupplier(newSupplier);
				response = MessageParserBranchManager.prepareMessageWithResultOfRegisterion_Supplier(
						RequestType.SERVER_MESSAGE_TO_CLIENT_SUPPLIER_REGISTER_SUCCESS);//response about success process
				sendMessageToGivenClient(response, client);
			} catch (BMServerException e) {//handle error
				response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
						e.getMessage());
				sendMessageToGivenClient(response, client);
			}
		}
	}
	
	/**
	 * Handle get data of business.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleGetDataOfBusiness(RequestType actionRequired, Object msg, ConnectionToClient client) {
		ArrayList<String> message = (ArrayList<String>)msg;
		String branch = message.get(2);
		Object response;
			message = businessDBConteroller.getBusinessesNames(branch);
			if(message != null) {//handle error
			response = MessageParserBranchManager.prepareMessageWithResultOfGettingData_Business(RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA_BUSINESSES_NAMES,message);
			sendMessageToGivenClient(response, client);
	
			}
		}
	
	/**
	 * Handle approve business.
	 *
	 * @param actionRequired the action required
	 * @param msg the message
	 * @param client
	 */
	private void handleApproveBusiness(RequestType actionRequired, Object msg, ConnectionToClient client) {
		Business business = MessageParserBranchManager.handleMessageExtractDataType_Business(msg);//parser message for get business to approve
		Object response;
		try {
			businessDBConteroller.approveBusiness(business);
			response = MessageParserBranchManager.prepareMessageWithResultOfApproveBusiness(
					RequestType.SERVER_MESSAGE_TO_CLIENT_APPROVE_BUSINESS_SUCCESS);//response about success process
			sendMessageToGivenClient(response, client);
		} catch (BMServerException e) {//handle error
			response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
					e.getMessage());
			sendMessageToGivenClient(response, client);
		}
		
	}
	
	/**
	 * Handle get data of client.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleGetDataOfClient(RequestType actionRequired, Object msg, ConnectionToClient client) {
		ArrayList<String> message = (ArrayList<String>)msg;
		ArrayList<String> result;
		Object response;
		try {
			result = clientDBController.getClientInfo(message.get(2));//method for get data if client from db  
			//response about success process
			response = MessageParserBranchManager.prepareMessageWithDataType_GetDataOfClient(result,actionRequired);
			sendMessageToGivenClient(response, client);
			//handle error
		} catch (BMServerException e) {
			response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
					e.getMessage());
			sendMessageToGivenClient(response, client);
		}
		
	}
	
	/**
	 * Handle change permission.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleChangePermission(RequestType actionRequired, Object msg, ConnectionToClient client) {
		ArrayList<String> message = (ArrayList<String>)msg;
		Object response;
		
		try {
			clientDBController.changePermissionRequest(message.get(5),message.get(6));
			//response about success process
			response = MessageParserBranchManager.prepareMessageWithResultOfChangePermission(
					RequestType.SERVER_MESSAGE_TO_CLIENT_CHANGE_PERMISSION_SUCCESS);
			sendMessageToGivenClient(response, client);
		} catch (BMServerException e) {	//handle error
			response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
					e.getMessage());
			sendMessageToGivenClient(response, client);
		}
		
		
	}
	
	/**
	 * Handle open report request by the date and the branch name.
	 *
	 * @param actionRequired the action required
	 * @param msg the message 
	 * @param client 
	 */
	private void handleOpenReportRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		ArrayList<String> message = (ArrayList<String>)msg;
		String type = message.get(2);
		String year = message.get(3);
		String month = message.get(4);
		String path = message.get(5);
		String branch = message.get(6);
		Object response;
		try {
			String result = openReoprtDBController.openReoprt(type,year,month,path,branch);// get from method string of array byte we encode 
																							//in order to send it back to client.
			//response about success process
			response = MessageParserTextString.prepareMessageWithDataType_SingleTextString(result,RequestType.SERVER_MESSAGE_TO_CLIENT_OPEN_REPORT_SUCCESS);
			sendMessageToGivenClient(response, client);
		}catch(BMServerException e) {	//handle error
			response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
					e.getMessage());
			sendMessageToGivenClient(response, client);
		}	
	}
	

	/**
	 * Handle open income report for supplier.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleOpenIncomeReportForSupplier(RequestType actionRequired, Object msg, ConnectionToClient client) {
		ArrayList<String> message = (ArrayList<String>)msg;
		String resIdString = message.get(2);
		int resId = Integer.parseInt(resIdString);
		String path = message.get(3);
		Object response;
		try {
			String result = openReoprtDBController.openIncomReoprtSupplier(resId,path);// get from method string of array byte we encode 
																						//in order to send it back to client.
			//response about success process
			response = MessageParserTextString.prepareMessageWithDataType_SingleTextString(result,RequestType.SERVER_MESSAGE_TO_CLIENT_OPEN_REPORT_SUCCESS);
			sendMessageToGivenClient(response, client);
		}catch(BMServerException e) {//handle error
			response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
					e.getMessage());
			sendMessageToGivenClient(response, client);
		}	
		
	}
	
	/**
	 * Handle open report from branch by date and branch.
	 * The method go to table in db and get the right file 
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleOpenReportFromBranch(RequestType actionRequired, Object msg, ConnectionToClient client) {
		ArrayList<String> message = (ArrayList<String>)msg;
		String[] msgArr = message.get(2).split("\\s+");
		String[] date = msgArr[0].split("-");
		String year = date[0];
		String month = date[1];
		String branch = msgArr[1];
		String path = message.get(3);
		Object response;
		try {
			String result = openReoprtDBController.openReoprt("reportFromBranch",year,month,path,branch);
			//response about success process
			response = MessageParserTextString.prepareMessageWithDataType_SingleTextString(result,RequestType.SERVER_MESSAGE_TO_CLIENT_OPEN_REPORT_SUCCESS);
			sendMessageToGivenClient(response, client);
		}catch(BMServerException e) {//handle error
			response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
					e.getMessage());
			sendMessageToGivenClient(response, client);
		}
		
	}

	/**
	 * Handle quarterly report request.
	 * The method go to table in db and get the right file
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleQuarterlyReportRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		ArrayList<String> message = (ArrayList<String>)msg;
		String quarter = message.get(2);
		String year = message.get(3);
		String path = message.get(4);
		Object response;
		try {
			String result = openReoprtDBController.openQuarterlyReport(quarter,year,path);
			//response about success process
			response = MessageParserTextString.prepareMessageWithDataType_SingleTextString(result,RequestType.SERVER_MESSAGE_TO_CLIENT_OPEN_REPORT_SUCCESS);
			sendMessageToGivenClient(response, client);
		}catch(BMServerException e) {//handle error
			response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
					e.getMessage());
			sendMessageToGivenClient(response, client);
		}	
		
	}
	

	/**
	 * Handle send report to CEO.
	 * save given file in db.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleSendReportToCEO(RequestType actionRequired, Object msg, ConnectionToClient client) {
		String message =  MessageParserTextString.handleMessageExtractDataType_SingleTextString(msg);
		String[] messageArr = message.split("branch");
		String fileString = messageArr[0];
		String branchName = messageArr[1];
		byte[] arr = Base64.getDecoder().decode(fileString); //decode from string to array bytes for saving it in db
		Object response;
		try {
			openReoprtDBController.saveFileForCEOInPdf(arr,branchName);
			//response about success process
			response = MessageParserBranchManager.prepareMessageWithResultOfChangePermission(
					RequestType.SERVER_MESSAGE_TO_CLIENT_SEND_TO_CEO_SUCCESS);
		} catch (BMServerException e) {//handle error
			response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
					e.getMessage());
			sendMessageToGivenClient(response, client);
		}	
	}
	
	/**
	 * Handle approve business request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleApproveBusinessRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
        DataType messageDataType = MessageParser.parseMessage_DataType(msg);
        switch (messageDataType) {
            case HR_MANAGER:
                Business business = MessageParserHR.handleMessageFromClient_ApproveBusinessClient((ArrayList<String>) msg);
                boolean response = HRDBController.approveBusiness(business);
                if(response == false) {
                    sendMessageToGivenClient(ErrorType.COULD_NOT_UPDATE_BUSINESS_CLIENT,client);
                }
                break;
        }
    }
	
	/**
	 * Handle check approve business request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleCheckApproveBusinessRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
        int isBusinessApproved = 0;
        ArrayList<String> message;
        DataType messageDataType = MessageParser.parseMessage_DataType(msg);
        switch (messageDataType) {
            case HR_MANAGER:
                int hr_id = MessageParserHR.handleMessageFromClient_getHrId((ArrayList<String>) msg);
                isBusinessApproved = HRDBController.CheckApproveBusiness(hr_id);
                message = MessageParserHR.prepareMessageToServer_HRCheckApproveBusiness(isBusinessApproved,messageDataType,actionRequired);
                sendMessageToGivenClient(message,client);
                break;

        }
    }
	
	/**
	 * Handle search request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleSearchRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {		
		if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_SEARCH_RESTAURANT_REQUEST) {
			Object response;
    		try {
    			ArrayList<String> searchBranch = MessageParserTextString.parseMessageDataType_ArrayListString(msg);
    			ArrayList<Restaurant> result =  restaurantDBController.GetRestaurantsListFromSearchData(searchBranch);
    			//response about success process
            	response = MessegeParserRestaurants.prepareMessageWithDataType_Restaurants(result, RequestType.SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED);
				sendMessageToGivenClient(response, client);

			} catch (BMServerException e) {//handle error
				response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
						e.getMessage());
				sendMessageToGivenClient(response, client);
			}
		}
	}
	
	/**
	 * Handle file incoming request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleFileIncomingRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		String fileName = MessageParserTextString.handleMessageExtractDataType_SingleTextString(msg);
		flagFileIncoming = true;
		fileSenderClient = client;
		System.out.println(fileName +"  "+ flagFileIncoming +"  "+ client.toString());
	}
	
	/**
	 * Handle get restaurants by category request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleGetRestaurantsByCategoryRequest(RequestType actionRequired, Object msg,
			ConnectionToClient client) { 
		ArrayList<String> category = MessageParserTextString.parseMessageDataType_ArrayListString(msg); //parser the message to ArrayList<String>

		if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_CATEGORY_RESTAURANT_REQUEST) {
			Object response;
    		try {
    			ArrayList<Restaurant> result =  restaurantDBController.GetRestaurantsListFromCategoriesData(category);
    			//response about success process
            	response = MessegeParserRestaurants.prepareMessageWithDataType_Restaurants(result, RequestType.SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED);
            	sendMessageToGivenClient(response,client);
            }catch(BMServerException e) {//handle error
                response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(), e.getMessage());
                sendMessageToGivenClient(response,client);
            }
		}

	}

	/**
	 * Handle get menu request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleGetMenuRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		String ResID = MessageParserTextString.handleMessageExtractDataType_SingleTextString(msg);

		if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_MENU_REQUEST) {
			Object response;
    		try {
    			ArrayList<Dish> result =  dishesDBController.GetDishesListFromResIDData(ResID);
    			//response about success process
            	response = MessegeParserDishes.prepareMessageWithDataType_Dishes(result, RequestType.SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED);
            	sendMessageToGivenClient(response,client);
            }catch(BMServerException e) {//handle error
                response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(), e.getMessage());
                sendMessageToGivenClient(response,client);
            }
		}

	}
	
	/**
	 * Handle get business client data.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleGetBusinessClientData(RequestType actionRequired, Object msg, ConnectionToClient client) {
		if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_BUSINESS_CLIENT_DATA) {
			String businessCode = MessageParserTextString.handleMessageExtractDataType_SingleTextString(msg);
			Object response;
    		try {
    			BusinessClientData result = budDBController.getBUD(businessCode);
    			//response about success process
            	response = MessageParserBusinessClientData.prepareMessageWithDataType_BusinessClientData(result, RequestType.SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED);
            	sendMessageToGivenClient(response,client);
            }catch(BMServerException e) {//handle error
            	e.printStackTrace();
            	System.out.println(e.getMessage());
                response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(), e.getMessage());
                sendMessageToGivenClient(response,client);
            }
		}else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_BUSINESS_CLIENT_BUDGED_UPDATE) {
			ArrayList<String> updateRequest = MessageParserTextString.parseMessageDataType_ArrayListString(msg);
			budDBController.updateBudgetForClient(updateRequest);
		}
	}
	
	/**
	 * Handle CRF request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleCRFRequest(RequestType actionRequired, Object msg, ConnectionToClient client){
		if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_GET_CLIENT_REFUNDS_DATA) {
			ArrayList<String> resIDs = MessageParserTextString.parseMessageDataType_ArrayListString(msg);
			Object response;
			ClientRefundsData refundAmount =  orderDBController.getCRFDataForRestaurantsGiven(resIDs);
			//TODO: delete the prints
        	response = MessageParserClientRefundData.prepareMessageWithDataType_ClientRefundData(refundAmount, RequestType.SERVER_MESSAGE_TO_CLIENT_REFUND_AMOUNT);
        	sendMessageToGivenClient(response,client);
    	}
		else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_UPDATE_REFUND_AMOUNT_AFTER_REFUNDS_USED) {
			ClientRefundsData crf = MessageParserClientRefundData.handleMessageExtractDataType_ClientRefundData(msg);
			orderDBController.updateCRFDataForRestaurantsGiven(crf);
		}
	}
	
	/**
	 * Handle orders status.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleOrdersStatus(RequestType actionRequired, Object msg, ConnectionToClient client) {
        ArrayList<String> array = (ArrayList<String>) msg;
        Boolean response = orderDBController.moveOrder(array.get(2), array.get(3));
        if(response == false) {
            sendMessageToGivenClient(ErrorType.COULD_NOT_UPDATE_ORDER,client);
        }
    }
	
	/**
	 * Handle cancel order.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleCancelOrder(RequestType actionRequired, Object msg, ConnectionToClient client) {
        ArrayList<String> array = (ArrayList<String>) msg;
        Boolean response = orderDBController.cancelOrder(array.get(2));
        if(response == false) {
            sendMessageToGivenClient(ErrorType.COULD_NOT_CANCEL_ORDER,client);
        }
    }
	
	/**
	 * Handle get orders from supplier request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleGetOrdersFromSupplierRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
        ArrayList<String> data = (ArrayList<String>)msg;

        if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST) {
            Object response;
            try {
                ArrayList<Order> orders = orderDBController.getOrdersByResId(data.get(2), data.get(3));
              //response about success process
                response = MessageParserOrder.prepareMessageWithDataType_Orders(orders, 
                        RequestType.SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED);
                sendMessageToGivenClient(response,client);
            }catch(BMServerException e) {
                response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(), e.getMessage());
                sendMessageToGivenClient(response,client);
            }
        }

    }
	
	/**
	 * Handle delete dish from menu request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleDeleteDishFromMenuRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
        Dish dish = MessegeParserDishes.handleMessageExtractDataType_SingleDish(msg);

        if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_DELETE_DISH_FROM_MENU_REQUEST) {
            Object response;
            try {
                dishesDBController.DeleteDishFromMenu(dish);
              //response about success process
                response = MessageParserTextString.prepareMessageWithDataType_SingleTextString("your dish deleted", 
                        RequestType.SERVER_MESSAGE_TO_CLIENT_UPDATED_DISH_SUCCESS);
                sendMessageToGivenClient(response,client);
            }catch(BMServerException e) {
                response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(), e.getMessage());
                sendMessageToGivenClient(response,client);
            }
        }

    }
	
	/**
	 * Handle update dish in menu request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleUpdateDishInMenuRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
        Dish dish = MessegeParserDishes.handleMessageExtractDataType_SingleDish(msg);

        if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_UPDATE_DISH_IN_MENU_REQUEST) {
            Object response;
            try {
                dishesDBController.UpdateDishInMenu(dish);
              //response about success process
                response = MessageParserTextString.prepareMessageWithDataType_SingleTextString("your dish updated", 
                        RequestType.SERVER_MESSAGE_TO_CLIENT_UPDATED_DISH_SUCCESS);
                sendMessageToGivenClient(response,client);
            }catch(BMServerException e) {
                response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(), e.getMessage());
                sendMessageToGivenClient(response,client);
            }
        }

    }
	
	/**
	 * Handle add dish to menu request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleAddDishToMenuRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
        Dish dish = MessegeParserDishes.handleMessageExtractDataType_SingleDish(msg);

        if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_ADD_DISH_TO_MENU_REQUEST) {
            Object response;
            try {
                Dish result = dishesDBController.AddDishToMenu(dish);
              //response about success process
                response = MessegeParserDishes.prepareMessageWithDataType_SingleDish(result,RequestType.SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED);
                sendMessageToGivenClient(response,client);
            }catch(BMServerException e) {
                response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(), e.getMessage());
                sendMessageToGivenClient(response,client);
            }
        }

    }
	
	/**
	 * Handle get res by supplier request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleGetResBySupplierRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
        User supplier = MessageParserUser.handleMessageExtractDataType_User(msg);

        if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_GET_RESTAURANT_BY_SUPPLIER_REQUEST) {
            Object response;
            try {
                Restaurant result =  restaurantDBController.GetRestaurantFromUserSupplierData(supplier);
                //response about success process
                response = MessegeParserRestaurants.prepareMessageWithDataType_singleRestaurant(result, RequestType.SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED);
                sendMessageToGivenClient(response,client);
            }catch(BMServerException e) {
                response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(), e.getMessage());
                sendMessageToGivenClient(response,client);
            }
        }

    }
	


	/**
	 * Handle get report from CEO.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	private void handleGetReportFromCEO(RequestType actionRequired, Object msg, ConnectionToClient client) {
		ArrayList<String> result = new 	ArrayList<String>();
		Object response;
		try {
			result = openReoprtDBController.getBranchReportsFroCEO();
			response = MessageParserTextString.prepareMessageToServerDataType_ArrayListStringAllString(result,RequestType.SERVER_MESSAGE_TO_CLIENT_CEO_GET_BRANCHES_REPORTS);
			sendMessageToGivenClient(response,client);
		} catch (BMServerException e) {
			 response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(), e.getMessage());
             sendMessageToGivenClient(response,client);
		}
		
	}


    

	/**
	 * Handle debug request.
	 *
	 * @param actionRequired the action required
	 * @param msg the msg
	 * @param client the client
	 */
	// -------------------------DEBUG FUNCTIONS
	private void handleDebugRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		DataType messageDataType = MessageParser.parseMessage_DataType(msg);
		if (messageDataType == DataType.SINGLE_TEXT_STRING) {
			ArrayList<String> message = (ArrayList<String>) msg;
			System.out.println(actionRequired.toString() + " " + messageDataType.toString() + " " + message.get(2));
		}
	}

	/**
	 * Sets the gui controller.
	 *
	 * @param guiController the new gui controller
	 */
	// -----------------------UI FUNCTIONS
	public void setGuiController(ServerMainWindowController guiController) {
		this.guiController = guiController;
	}

	/**
	 * Server print to gui log.
	 *
	 * @param message the message
	 * @param addPrintToTerminal the add print to terminal
	 */
	private void serverPrintToGuiLog(String message, boolean addPrintToTerminal) {
		guiController.updateDataLog(message);
		if (addPrintToTerminal)
			System.out.println(message);
	}

	/**
	 * Server print to gui number of clients.
	 *
	 * @param numberOfClients the number of clients
	 */
	private void serverPrintToGuiNumberOfClients(int numberOfClients) {
		guiController.updateNumberOfClientsConnected(numberOfClients);
	}
	
	/**
	 * Gets the DB controller.
	 *
	 * @return the DB controller
	 */
	///------------------------LAST MINUTE PANIC FUNCTIONS
	public DBController getDBController() {
		return dbController;
	}

}
