package client_logic;

import ocsf.client.*;
import utility.DataType;
import utility.RequestType;

import java.io.*;

import entity.Order;
import entity.User;

public class BMClientLogic extends AbstractClient {
	
	private Object lastDataRecieved;
	private DataType typeOfLastDataRecieved;
	

	public BMClientLogic(String host, int port) throws IOException 
	  {
	    super(host, port); //Call the superclass constructor
	    openConnection();
	    System.out.println("connection opened");
	  }

	//handles messages received from the server, saves last data in a variable
	public void handleMessageFromServer(Object messageFromServerToClient) {
		
		RequestType messageRequestType = MessageParser.parseMessageFromServer_RequestType(messageFromServerToClient);
		DataType messageDataType = MessageParser.parseMessageFromServer_DataType(messageFromServerToClient);
		
		switch(messageRequestType) {
		case SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED:
			serverToClientDataProvidedRequestType(messageFromServerToClient);
			break;
		case SERVER_MESSAGE_TO_CLIENT_ERROR:
			serverToClientErrorMessage(messageFromServerToClient);
			break;
		}
		
//		if(messageRequestType == RequestType.SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED && messageDataType == DataType.ORDER) {
//			typeOfLastDataRecieved = messageDataType;
//			lastDataRecieved = ClientOrderController.handleMessageFromServer_OrderDataSent(messageFromServerToClient);
//			
//		} else if(messageRequestType == RequestType.SERVER_MESSAGE_TO_CLIENT_ERROR && messageDataType == DataType.ERROR_MESSAGE){
//			typeOfLastDataRecieved = messageDataType;
//			lastDataRecieved = ClientOrderController.handleMessageFromServer_ErrorDataSent(messageFromServerToClient);
//		}else {
//			//TODO: ADD ERROR HANDLING
//			System.out.println("CLIENT RECIEVED UNKNOW MESSAGE");
		//}
		
	}
	//handleMessageFromServer uses it to update data as lastData
	private void serverToClientDataProvidedRequestType(Object messageFromServerToClient) {
		
		DataType messageDataType = MessageParser.parseMessageFromServer_DataType(messageFromServerToClient);
		switch(messageDataType) {
		case ORDER:
			typeOfLastDataRecieved = messageDataType;
			lastDataRecieved = ClientOrderController.handleMessageFromServer_OrderDataSent(messageFromServerToClient);
		case USER:
			typeOfLastDataRecieved = messageDataType;
			lastDataRecieved = ClientLoginController.handleMessageFromServer_UserDataSent(messageFromServerToClient);
			
		}
	}
	//handleMessageFromServer uses it to update error in lastData
	private void serverToClientErrorMessage(Object messageFromServerToClient) {
		
		DataType messageDataType = MessageParser.parseMessageFromServer_DataType(messageFromServerToClient);
		if(messageDataType == DataType.ERROR_MESSAGE) {
			typeOfLastDataRecieved = messageDataType;
			lastDataRecieved = ClientOrderController.handleMessageFromServer_ErrorDataSent(messageFromServerToClient);
		}
		else {
			//TODO: ADD ERROR HANDLING
			System.out.println("CLIENT RECIEVED UNKNOW MESSAGE");
		}
			
	}
	
	public void sendMessageToServer(Object dataToSendToServer, DataType dataType, RequestType requestType) {
		Object message;
		switch(dataType) {
			case ORDER:
				message = MessageParserOrder.prepareMessageToServer_DataTypeOrder((Order)dataToSendToServer, requestType);
				break;
			case USER:
				message = MessageParserUser.prepareMessageToServer_DataTypeUser((User)dataToSendToServer, requestType);
				break;
//			case SINGLE_TEXT_STRING:
//				message = MessageParserOrder.prepareMessageToServer_DataTypeSingleTextString((String)dataToSendToServer, requestType);
//				break;
			default:
				System.out.println("Error: sendMessageToServer unknown dataType");
				//TODO: ERROR HANDLING
				return;
		}
		handleMessageFromClientUI(message);
	}

	public void handleMessageFromClientUI(Object messageToServerFromClient) {
		try {
			sendToServer(messageToServerFromClient);
		} catch (IOException e) {
//			clientUI.display("Could not send message to server.  Terminating client.");
			System.out.println("Could not send message to server.  Terminating client.");
			quit();
		}
	}
	
	
	//closes connection, while notifying the server about it beforehand
	public void closeConnectionWithMessageToServer() throws IOException{
		sendMessageToServer("disconnected", DataType.SINGLE_TEXT_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_CONNECTION_STATUS);
		closeConnection();
	}
	
	
	public Object getLastDataRecieved() {
		return lastDataRecieved;
	}
	
	public DataType getLastDataTypeRecieved() {
		return typeOfLastDataRecieved;
	}
	
	public void setLastDataRecieved(Object data, DataType dataType) {
		this.lastDataRecieved = data;
		this.typeOfLastDataRecieved = dataType;
	}

	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
