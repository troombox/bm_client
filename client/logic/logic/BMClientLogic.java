package client.logic.logic;

import java.io.IOException;

import client.logic.message_parsers.MessageParser;
import ocsf.client.AbstractClient;
import utility.entity.User;
import utility.enums.DataType;
import utility.enums.RequestType;
import utility.enums.UserType;
import utility.message_parsers.MessageParserError;
import utility.message_parsers.MessageParserUser;
import utility.message_parsers.MessegeParserRestaurants;

public class BMClientLogic extends AbstractClient{
	
	private Object lastDataRecieved;
	private DataType typeOfLastDataRecieved;
	private RequestType typeOfLastRequestRecieved;
	
	private User loggedInUser;
	
	public BMClientLogic(String host, int port) throws IOException {
	    super(host, port); //Call the superclass constructor
	    openConnection();
	}

	@Override
	protected void handleMessageFromServer(Object messageFromServerToClient) {
		RequestType messageRequestType = MessageParser.parseMessageFromServer_RequestType(messageFromServerToClient);
		DataType messageDataType = MessageParser.parseMessageFromServer_DataType(messageFromServerToClient);
		typeOfLastDataRecieved = messageDataType;
		typeOfLastRequestRecieved = messageRequestType;
		switch(messageDataType) {
			case USER:
				lastDataRecieved = MessageParserUser.handleMessageExtractDataType_User(messageFromServerToClient);
				break;
			case RESTAURANTS_LIST:
				lastDataRecieved = MessegeParserRestaurants.handleMessageExtractDataType_Restaurants(messageFromServerToClient);
				break;
			case ERROR_MESSAGE:
				lastDataRecieved = MessageParserError.handleMessageExtractDataType__ErrorType(messageFromServerToClient);
				break;
			default:
				System.out.println("ERROR, UNKNOWN DATA TYPE");
		}	
	}
	
	public void sendMessageToServer(Object dataToSendToServer, DataType dataType, RequestType requestType) {
		Object message;
		switch(dataType) {
		case USER:
			message = MessageParserUser.prepareMessageWithDataType_User((User)dataToSendToServer, requestType);
			break;
		case SINGLE_TEXT_STRING:
			message = MessageParser.prepareMessageToServerDataType_SingleTextString((String)dataToSendToServer, requestType);
			break;	
		default:
			System.out.println("sendMessageToServer: unknown dataType");
			return;
		}
		handleMessageToServer(message);
	}
	
	private void handleMessageToServer(Object msg) {
		try {
			sendToServer(msg);
		} catch (IOException e) {
			System.out.println("Could not send message to server. Terminating client.");
			System.exit(1);
		}
	}
	
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
	
	public void loginUser(User user) {
		this.loggedInUser = user;
	}
	
	public void logOutUser() {
		if(loggedInUser == null) {
			return;
		}
		sendMessageToServer(loggedInUser, DataType.USER, RequestType.CLIENT_REQUEST_TO_SERVER_LOGOUT_REQUEST);
		loggedInUser = new User(-1, "", "", "", "", "", UserType.USER, "", "", false, "");
	}
	
	public User getLoggedUser() {
		if(loggedInUser == null)
			return new User(-1, "", "", "", "", "", UserType.USER, "", "", false, "");
		else {
			return loggedInUser;
		}
	}

	
	//----------------TO BE CHANGED WHEN MESSAGE HISTORY ADDED (POSSIBLY)
	public Object getLastDataRecieved() {
		return lastDataRecieved;
	}

	public DataType getTypeOfLastDataRecieved() {
		return typeOfLastDataRecieved;
	}

	public RequestType getTypeOfLastRequestRecieved() {
		return typeOfLastRequestRecieved;
	}
	
}
