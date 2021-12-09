package client.logic.logic;

import java.io.IOException;

import client.logic.message_parsers.MessageParserUser;
import ocsf.client.AbstractClient;
import utility.entity.*;
import utility.enums.*;

public class BMClientLogic extends AbstractClient{
	
	public BMClientLogic(String host, int port) throws IOException {
	    super(host, port); //Call the superclass constructor
	    openConnection();
	    System.out.println("connection opened");
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		
	}
	
	public void sendMessageToServer(Object dataToSendToServer, DataType dataType, RequestType requestType) {
		Object message;
		switch(dataType) {
		case USER:
			System.out.println("case user");
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
	
}
