package server.logic;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import server.db_logic.*;
import server.exceptions.BMServerException;
import server.gui.ServerMainWindowController;
import utility.entity.*;
import utility.enums.*;
import utility.message_parsers.*;


public class BMServerLogic extends AbstractServer {

	ServerMainWindowController guiController;

	DBController dbController;
	OrderDBController orderDBController;
	UserDBController userDBController;

	ClientDBController clientDBController;
	SupplierDBController supplierDBController;
	RestaurantDBController restaurantDBController;
	HRDBController hrDBController;
	
	boolean flagFileIncoming = false;
	ConnectionToClient fileSenderClient = null; 

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
		this.restaurantDBController = new RestaurantDBController(dbController);
		this.hrDBController = new HRDBController(dbController);
	}

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
		}
		serverPrintToGuiLog("Message From Client Handled, action: " + actionRequired.toString(), true);
	}

	public void sendMessageToGivenClient(Object msg, ConnectionToClient client) {
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			// TODO: HANDLE ERROR
			e.printStackTrace();
		}
	}

	protected void serverStarted() {
		serverPrintToGuiLog("Server listening for connections on port " + getPort(), true);
	}

	protected void serverStopped() {
		serverPrintToGuiLog("Server has stopped listening for connections.", true);
	}

	protected void clientConnected(ConnectionToClient client) {
		serverPrintToGuiNumberOfClients(getNumberOfClients());
		serverPrintToGuiLog("Client Connected: " + client.toString(), true);
	}

//	synchronized protected void clientDisconnected(ConnectionToClient client) {
////		System.out.println("Client Disconnected:" + client.toString());
//		serverPrintToGuiLog("Client Disconnected:" + client.toString(), true);
//		int numOfClients = getNumberOfClients() - 1;
//		serverPrintToGuiNumberOfClients(numOfClients);
//	}

	protected void clientDisconnectedNonAbstract(ConnectionToClient client) {
		serverPrintToGuiLog("Client Disconnected: " + client.toString(), true);
		int numOfClients = getNumberOfClients() - 1;
		serverPrintToGuiNumberOfClients(numOfClients);
	}

	// ---------------------- HELPER FUNCTIONS --------------------------

	private void handleWriteRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		DataType messageDataType = MessageParser.parseMessage_DataType(msg);
		switch (messageDataType) {
		case ORDER:
//				Order orderData = MessageParser.parseMessageDataType_Order(msg);
//				if(!orderDBController.handleWriteRequestMessage(actionRequired, orderData)) {
//					String error = "Error: Failed to update given OrderNumber in DB";
//					Object response = MessageParser.createMessageToClientDataType_Error(error);
//					sendMessageToGivenClient(response,client);
//				}
			break;
		case UNKNOWN:
			// TODO: HANDLE ERROR - UNKNOWN DATA TYPE?
		default:
			// TODO: HANDE ERROR - HOW DID WE GET HERE?
		}
	}

	private void handleGetRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		DataType messageDataType = MessageParser.parseMessage_DataType(msg);
		switch (messageDataType) {
		case ORDER:
//				String requestedOrder = MessageParser.parseMessageDataType_Order_GetRequestOrderID(msg);
//				Order order = orderDBController.getOrderDataFromDB(requestedOrder);
//				Object response;
//				if(order == null) {
//					//TOOD:Add error handling
//					String error = "Error: Failed to find requestedOrder in DB";
//					response = MessageParser.createMessageToClientDataType_Error(error);
//				} else {
//					response = MessageParser.createMessageToClientDataType_Order(order);
//				}
//				sendMessageToGivenClient(response,client);
			break;
		case HR_MANAGER:
			ArrayList<String> gotFromClient = MessageParserHR.handleMessageFromClient_HRGetData(msg);
			Object response = HRDBController.getUsersToApproveFromDB(Integer.parseInt(gotFromClient.get(2)));
			sendMessageToGivenClient(response,client);
			break;
		case UNKNOWN:
			// TODO: HANDLE ERROR - UNKNOWN DATA TYPE?
		default:
			// TODO: HANDE ERROR - HOW DID WE GET HERE?
		}
	}

	private void handleConnectionRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		DataType messageDataType = MessageParser.parseMessage_DataType(msg);
		switch (messageDataType) {
		case SINGLE_TEXT_STRING:
			String message = MessageParser.parseMessageDataType_SingleTextString(msg);
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
			// TODO: HANDE ERROR - HOW DID WE GET HERE?
		}
	}

	private void handleLoginRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		User user = MessageParserUser.handleMessageExtractDataType_User(msg);
		if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_LOGIN_REQUEST) {
			Object response;
			try {
				User result = userDBController.authenticateAndGetFullUserData(user);
				userDBController.setUserToLoggedIn(result);
				response = MessageParserUser.prepareMessageWithDataType_User(result,
						RequestType.SERVER_MESSAGE_TO_CLIENT_LOGIN_SUCCESS);
				sendMessageToGivenClient(response, client);
			} catch (BMServerException e) {
				response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
						e.getMessage());
				sendMessageToGivenClient(response, client);
			}
		} else if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_LOGOUT_REQUEST) {
			userDBController.setUserToLoggedOut(user);
		}
	}

	private void handleW4CRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		User user = MessageParserUser.handleMessageExtractDataType_User(msg);
		if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_W4C_REQUEST) {
			Object response;
			try {
				User result = userDBController.getCodesAccordingToW4C(user);
				response = MessageParserUser.prepareMessageWithDataType_User(result,
						RequestType.SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED);
				sendMessageToGivenClient(response, client);
			} catch (BMServerException e) {
				response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
						e.getMessage());
				sendMessageToGivenClient(response, client);
			}
		}
	}

	private void handleRegisterClientRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		Client newClient = MessageParserBranchManager.handleMessageExtractDataType_Client(msg);
		if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_CLIENT) {
			Object response;
			try {
				// newClient
				clientDBController.setNewClient(newClient);
				response = MessageParserBranchManager.prepareMessageWithResultOfRegisterion_Client(
						RequestType.SERVER_MESSAGE_TO_CLIENT_REGISTER_SUCCESS);
				sendMessageToGivenClient(response, client);
			} catch (BMServerException e) {
				response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
						e.getMessage());
				sendMessageToGivenClient(response, client);
			}
		}
	}

	private void handleRegisterSupplierRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		Supplier newSupplier = MessageParserBranchManager.handleMessageExtractDataType_Supplier(msg);
		if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_SUPPLIER) {
			Object response;
			try {
				// newClient
				supplierDBController.setNewSupplier(newSupplier);
				response = MessageParserBranchManager.prepareMessageWithResultOfRegisterion_Supplier(
						RequestType.SERVER_MESSAGE_TO_CLIENT_SUPPLIER_REGISTER_SUCCESS);
				sendMessageToGivenClient(response, client);
			} catch (BMServerException e) {
				response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
						e.getMessage());
				sendMessageToGivenClient(response, client);
			}
		}
	}

	private void handleSearchRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		String searchText = MessageParser.parseMessageDataType_SingleTextString(msg);

		if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_SEARCH_RESTAURANT_REQUEST) {
			Object response;
			try {
				ArrayList<Restaurant> result = restaurantDBController.GetRestaurantsListFromSearchData(searchText);
				response = MessegeParserRestaurants.prepareMessageWithDataType_Restaurants(result,
						RequestType.SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED);
				sendMessageToGivenClient(response, client);

			} catch (BMServerException e) {
				response = MessageParserError.prepareMessageToClientWithDataType_Error(e.getErrorType(),
						e.getMessage());
				sendMessageToGivenClient(response, client);
			}
		}
	}
	
	private void handleFileIncomingRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		String fileName = MessageParser.parseMessageDataType_SingleTextString(msg);
		flagFileIncoming = true;
		fileSenderClient = client;
		System.out.println(fileName +"  "+ flagFileIncoming +"  "+ client.toString());
	}

	// -------------------------DEBUG FUNCTIONS
	private void handleDebugRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		DataType messageDataType = MessageParser.parseMessage_DataType(msg);
		if (messageDataType == DataType.SINGLE_TEXT_STRING) {
			ArrayList<String> message = (ArrayList<String>) msg;
			System.out.println(actionRequired.toString() + " " + messageDataType.toString() + " " + message.get(2));
		}
	}

	// -----------------------UI FUNCTIONS
	public void setGuiController(ServerMainWindowController guiController) {
		this.guiController = guiController;
	}

	private void serverPrintToGuiLog(String message, boolean addPrintToTerminal) {
		guiController.updateDataLog(message);
		if (addPrintToTerminal)
			System.out.println(message);
	}

	private void serverPrintToGuiNumberOfClients(int numberOfClients) {
		guiController.updateNumberOfClientsConnected(numberOfClients);
	}

}
