package client.logic;

import java.io.IOException;
import java.util.ArrayList;

import client.utility.wrappers.MultiOrder;
import ocsf.client.AbstractClient;
import utility.entity.Business;
import utility.entity.BusinessClient;
import utility.entity.Client;
import utility.entity.Dish;
import utility.entity.Order;
import utility.entity.ClientChangePermission;
import utility.entity.ClientRefundsData;
import utility.entity.Supplier;
import utility.entity.User;
import utility.enums.DataType;
import utility.enums.RequestType;
import utility.enums.UserType;
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
 * The Class BMClientLogic subclass of abstractClient.
 * The class uses for parse the data type of the request
 * in order to send the right data to server side.
 */
public class BMClientLogic extends AbstractClient{
	
	/** The last data received. */
	private Object lastDataRecieved;
	
	/** The type of last data received. */
	private DataType typeOfLastDataRecieved;
	
	/** The type of last request received. */
	private RequestType typeOfLastRequestRecieved;
	
	/** The logged in user. */
	private User loggedInUser;
	
	/** The current order. */
	private MultiOrder currentOrder;
	
	/**
	 * Constructor initialize the connection parameters
	 * the host and the port number.
	 * Open the connection with server. 
	 *
	 * @param host the host
	 * @param port the port
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public BMClientLogic(String host, int port) throws IOException {
	    super(host, port); //Call the superclass constructor
	    openConnection();
	}

	/**
	 * Handle message from server.
	 * Check which data type got from server 
	 * and save the parser data in the variable lastDataRecieved.
	 * Parsing the data using the right parser method according to 
	 * the data type and request type.
	 *
	 * @param messageFromServerToClient the message from server to client
	 */
	@Override
	protected void handleMessageFromServer(Object messageFromServerToClient) {
		RequestType messageRequestType = MessageParser.parseMessage_RequestType(messageFromServerToClient);
		DataType messageDataType = MessageParser.parseMessage_DataType(messageFromServerToClient);
		typeOfLastDataRecieved = messageDataType;
		typeOfLastRequestRecieved = messageRequestType;
		switch(messageDataType) {
			case USER:
				lastDataRecieved = MessageParserUser.handleMessageExtractDataType_User(messageFromServerToClient);
				break;
			case CLIENT:
				lastDataRecieved = MessageParserBranchManager.handleMessageExtractDataTypeResultRegistretion_Client(messageFromServerToClient);
				break;
			case SUPPLIER:
				lastDataRecieved = MessageParserBranchManager.handleMessageExtractDataTypeResultRegistretion_Supplier(messageFromServerToClient);
				break;
			case RESTAURANTS_LIST:
				lastDataRecieved = MessegeParserRestaurants.handleMessageExtractDataType_Restaurants(messageFromServerToClient);
				break;
			case HR_MANAGER:
				if(messageRequestType == RequestType.CLIENT_REQUEST_TO_SERVER_CHECK_APRROVE_BUSINESS)
                    lastDataRecieved = MessageParserHR.handleMessageFromClient_getHrId((ArrayList<String>)messageFromServerToClient);
                else {
                    lastDataRecieved = MessageParserHR.handleMessageExtractDataType_HRGetData(messageFromServerToClient);
                }
				break;
			case ERROR_MESSAGE:
				lastDataRecieved = MessageParserError.handleMessageExtractDataType__ErrorType(messageFromServerToClient);
				break;
			case DISHES_LIST:
				lastDataRecieved = MessegeParserDishes.handleMessageExtractDataType_Dishes(messageFromServerToClient);
				break;
			case BUSINESS_CLIENT_DATA:
				lastDataRecieved = MessageParserBusinessClientData.handleMessageExtractDataType_BusinessClientData(messageFromServerToClient);
				break;
			case SINGLE_TEXT_STRING:
				lastDataRecieved = MessageParserTextString.handleMessageExtractDataType_SingleTextString(messageFromServerToClient);
				break;
			case GET_DATA_OF_BUSINESS:
				lastDataRecieved = messageFromServerToClient;
				break;
			case APPROVE_BUSINESS:
				lastDataRecieved = MessageParserBranchManager.handleMessageExtractDataTypeResultOfApproveBusiness(messageFromServerToClient);
				break;
			case GET_DATA_OF_CLIENT:
				lastDataRecieved = MessageParserBranchManager.handleMessageExtractDataType_GetDataOfClient(messageFromServerToClient);
				break;
			case CHANGE_PERMISSION:
				lastDataRecieved = MessageParserBranchManager.handleMessageExtractFromArrayList(messageFromServerToClient);
				break;
			case REPORT:
				lastDataRecieved = MessageParserBranchManager.handleMessageExtractFromArrayList(messageFromServerToClient);
				break;
			case RESTAURANT:
                lastDataRecieved = MessegeParserRestaurants.handleMessageExtractDataType_singleRestaurant(messageFromServerToClient);
                break;
			case DISH:
                lastDataRecieved = MessegeParserDishes.handleMessageExtractDataType_SingleDish(messageFromServerToClient);
                break;
			case ORDERS_LIST:
                lastDataRecieved = MessageParserOrder.handleMessageExtractDataType_orders(messageFromServerToClient);
                break;
			case ARRAYLIST_STRING:
				 lastDataRecieved = MessageParserTextString.handleMessageExtractDataType_ArrayListString(messageFromServerToClient);
				 break;
			case CLIENT_REFUNDS_DATA:
				lastDataRecieved = MessageParserClientRefundData.handleMessageExtractDataType_ClientRefundData(messageFromServerToClient);
				break;
			default:
				System.out.println("ERROR, UNKNOWN DATA TYPE");
		}	
	}
	
	/**
	 * Send message to server data.
	 * According to the data type we using the right parser
	 * to parse the data to something we except in the server side.
	 *
	 * @param dataToSendToServer the data to send to server
	 * @param dataType the data type
	 * @param requestType the request type
	 */
	public void sendMessageToServer(Object dataToSendToServer, DataType dataType, RequestType requestType) {
		Object message=null;
		switch(dataType) {
		case USER:
			message = MessageParserUser.prepareMessageWithDataType_User((User)dataToSendToServer, requestType);
			break;
		case CLIENT:
			message = MessageParserBranchManager.prepareMessageWithDataType_Client((Client)dataToSendToServer, requestType);
			break;
		case SUPPLIER:
			message = MessageParserBranchManager.prepareMessageWithDataType_Supplier((Supplier)dataToSendToServer, requestType);
			break;
		case ORDER:
			message = MessageParserOrder.prepareMessageWithDataType_Order((Order)dataToSendToServer, requestType);
			break;
		case SINGLE_TEXT_STRING:
			message = MessageParserTextString.prepareMessageWithDataType_SingleTextString((String)dataToSendToServer, requestType);
			break;	
		case HR_MANAGER:
			if(requestType == RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA) {
                message = MessageParserHR.prepareMessageToServer_HRDataRequest((int)dataToSendToServer,dataType, requestType);
            } else if (requestType == RequestType.CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB){
                message = MessageParserHR.prepareMessageToServer_HRUpdateDB((BusinessClient) dataToSendToServer, dataType, requestType);
            }
            else if(requestType == RequestType.CLIENT_REQUEST_TO_SERVER_APRROVE_BUSINESS || 
                    requestType == RequestType.CLIENT_REQUEST_TO_SERVER_CHECK_APRROVE_BUSINESS) {
                message = MessageParserHR.prepareMessageToServer_HRApproveBusiness((int) dataToSendToServer, dataType, requestType);
            }
            else if(requestType == RequestType.CLIENT_REQUEST_TO_SERVER_GET_APPROVED_BUSINESS_CLIENTS) {
                message = MessageParserHR.prepareMessageToServer_HRDataRequest((int)dataToSendToServer,dataType, requestType);
            }else if(requestType == RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_NEW_BUSINESS) {
                message = MessageParserHR.prepareMessageToServer_HRRegisterNewBusiness((Business)dataToSendToServer,dataType, requestType);
            }
			break;
		case GET_DATA_OF_BUSINESS:
			message = MessageParserBranchManager.prepareMessageWithDataType_GET_DATA_OF_BUSINESS((String)dataToSendToServer, requestType);
			break;
		case APPROVE_BUSINESS:
			message = MessageParserBranchManager.prepareMessageWithDataType_Business((Business)dataToSendToServer,requestType);
			break;
		case GET_DATA_OF_CLIENT:
			message = MessageParserBranchManager.prepareMessageWithDataType_getDataOfClient((String)dataToSendToServer,requestType);
			break;
		case CHANGE_PERMISSION:
			message = MessageParserBranchManager.prepareMessageWithDataType_changePermission((ClientChangePermission)dataToSendToServer, requestType);
			break;
		case REPORT:
			System.out.println("before sending to server");
			if(requestType == RequestType.CLIENT_REQUEST_TO_SERVER_SEND_REPORT_TO_CEO) {
				message = MessageParserTextString.prepareMessageWithDataType_SingleTextString((String)dataToSendToServer, requestType);
				
			}else {
			message = dataToSendToServer;
			}
			break;
		case DISH:
            message = MessegeParserDishes.prepareMessageWithDataType_SingleDish(dataToSendToServer, requestType);
            break;
		case ARRAYLIST_STRING:
			ArrayList<String> arraylist = (ArrayList<String>)dataToSendToServer;
			message = MessageParserTextString.prepareMessageToServerDataType_ArrayListString(arraylist, requestType);
			break;
		case CLIENT_REFUNDS_DATA:
			message = MessageParserClientRefundData.prepareMessageWithDataType_ClientRefundData((ClientRefundsData)dataToSendToServer, requestType);
			break;
		default:
			System.out.println("sendMessageToServer: unknown dataType");
			return;
		}
		handleMessageToServer(message);
	}
	
	/**
	 * Handle message to server.
	 * Get parsed data and send it 
	 * to server using sendToServer method.
	 *
	 * @param msg the msg
	 */
	private void handleMessageToServer(Object msg) {
		try {
			sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not send message to server. Terminating client.");
			System.exit(1);
		}
	}
	
	/**
	 * Close connection with message to server.
	 */
	public void closeConnectionWithMessageToServer() {
		sendMessageToServer("disconnected", DataType.SINGLE_TEXT_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_CONNECTION_STATUS);
		try {
			closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	//----------------LOGGED-IN USER METHODS
	
	/**
	 * Login user.
	 * Saving the logged in user in variable.
	 *
	 * @param user the user
	 */
	public void loginUser(User user) {
		this.loggedInUser = user;
	}
	
	/**
	 * Log out user.
	 * Method for log out request.
	 * Send to server log out request.
	 */
	public void logOutUser() {
		if(loggedInUser == null) {
			return;
		}
		sendMessageToServer(loggedInUser, DataType.USER, RequestType.CLIENT_REQUEST_TO_SERVER_LOGOUT_REQUEST);
		loggedInUser = new User(-1, "", "", "", "", "", UserType.USER, "", "", false, "");
		currentOrder = null;
	}
	
	/**
	 * Gets the logged user.
	 * Getter for getting the variable that
	 * save the logged in user
	 *
	 * @return the logged user
	 */
	public User getLoggedUser() {
		if(loggedInUser == null)
			return new User(-1, "", "", "", "", "", UserType.USER, "", "", false, "");
		else {
			return loggedInUser;
		}
	}
	
	//--------------CURRENT ORDER METHODS
	
	/**
	 * Creates the order.
	 * create new order.
	 *
	 * @param restaurantID the restaurant ID
	 */
	public void createOrder(int restaurantID) {
		if(currentOrder == null) {
			currentOrder = new MultiOrder(loggedInUser, restaurantID);
		}
		return;
	}
	
	/**
	 * Add dish to order.
	 *
	 * @param dishToAdd the dish to add
	 */
	public void addToOrder(Dish dishToAdd) {
		createOrder(Integer.parseInt(dishToAdd.getRes_ID()));
		currentOrder.addDish(dishToAdd);
	}
	
	/**
	 * Removes dish from order.
	 *
	 * @param dishToRemove the dish to remove
	 */
	public void removeFromOrder(Dish dishToRemove) {
		if(currentOrder == null) {
			return;
		}
		if(currentOrder.checkIfDishInOrderByDish(dishToRemove)) {
			currentOrder.removeDish(dishToRemove);
		}
	}
	
	/**
	 * Checks if is order list empty.
	 *
	 * @return true, if is order list empty
	 */
	public boolean isOrderListEmpty() {
		if(currentOrder == null) {
			return true;
		}
		if(currentOrder.getAmountOfDishes() != 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Gets the order dishes.
	 *
	 * @return the order dishes
	 */
	public ArrayList<Dish> getOrderDishes(){
		if(currentOrder == null) {
			return null;
		}
		return currentOrder.getDishesInOrder();
	}
	
	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
	public MultiOrder getOrder(){
		if(currentOrder == null) {
			return null;
		}
		return currentOrder;
	}
	
	/**
	 * Clear current order.
	 */
	public void clearCurrentOrder() {
		currentOrder = null;
	}
	

	
	/**
	 * Gets the last data received from server.
	 *
	 * @return the last data received
	 */
	//----------------TO BE CHANGED WHEN MESSAGE HISTORY ADDED (POSSIBLY)
	public Object getLastDataRecieved() {
		return lastDataRecieved;
	}

	/**
	 * Gets the type of last data received from server.
	 *
	 * @return the type of last data received
	 */
	public DataType getTypeOfLastDataRecieved() {
		return typeOfLastDataRecieved;
	}

	/**
	 * Gets the type of last request received.
	 *
	 * @return the type of last request received
	 */
	public RequestType getTypeOfLastRequestRecieved() {
		return typeOfLastRequestRecieved;
	}
	
}
