package client.logic.logic;

import java.io.IOException;
import java.util.ArrayList;

import client.logic.message_parsers.MessageParser;
import client.logic.message_parsers.MessageParserError;
import client.logic.message_parsers.MessageParserUser;
import ocsf.client.AbstractClient;
import utility.entity.*;
import utility.enums.*;

public class BMClientLogic extends AbstractClient{
	
	private Object lastDataRecieved;
	private DataType typeOfLastDataRecieved;
	private RequestType typeOfLastRequestRecieved;
	
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
				lastDataRecieved = MessageParserUser.handleMessageFromServer_UserDataSent(messageFromServerToClient);
				break;
			case ERROR_MESSAGE:
				lastDataRecieved = MessageParserError.handleMessageFromServer_ErrorTypeSent(messageFromServerToClient);
				break;
			default:
				System.out.println("ERROR, UNKNOWN DATA TYPE");
		}	
	}
	
	public void sendMessageToServer(Object dataToSendToServer, DataType dataType, RequestType requestType) {
		Object message;
		switch(dataType) {
		case USER:
			message = MessageParserUser.prepareMessageToServerDataType_User((User)dataToSendToServer, requestType);
			break;
		default:
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
