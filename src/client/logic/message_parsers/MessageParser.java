package client.logic.message_parsers;

import java.util.ArrayList;

import utility.enums.DataType;
import utility.enums.RequestType;

public class MessageParser {
		
	//-------Parsing messages from Server to Client:
	//currently working with message made as ArrayList<String>:
	//message layout: [ RequestType (enum) | DataType (enum) |  Data strings ]
	
	public static RequestType parseMessageFromServer_RequestType(Object message) {
		//current iteration assumes message as an ArrayList<String>
		ArrayList<String> msg = (ArrayList<String>)message;
		switch(msg.get(0)) {
		case "SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED":
			return RequestType.SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED;
		case "SERVER_MESSAGE_TO_CLIENT_LOGIN_SUCCESS":
			return RequestType.SERVER_MESSAGE_TO_CLIENT_LOGIN_SUCCESS;
		case "SERVER_MESSAGE_TO_CLIENT_ERROR":
			return RequestType.SERVER_MESSAGE_TO_CLIENT_ERROR;
		case "SERVER_MESSAGE_TO_CLIENT_REGISTER_SUCCESS":
			return RequestType.SERVER_MESSAGE_TO_CLIENT_REGISTER_SUCCESS;

		default:
			//TODO: ADD error handling for this case;
			return RequestType.CLIENT_REQUEST_TO_SERVER_UNKNOWN_REQUEST;
		}
	}
	
	public static DataType parseMessageFromServer_DataType(Object message) {
		//current iteration assumes message as an ArrayList<String>
		ArrayList<String> msg = (ArrayList<String>)message;
		switch(msg.get(1)) {
		case "ORDER":
			return DataType.ORDER;
		case "USER":
			return DataType.USER;
		case "CLIENT":
			return DataType.CLIENT;
		case "SINGLE_TEXT_STRING":
			return DataType.SINGLE_TEXT_STRING;
		case "ERROR_MESSAGE":
			return DataType.ERROR_MESSAGE;

		case "RESTAURANTS_LIST":
			return DataType.RESTAURANTS_LIST;

		default:
			//TODO: ADD error handling for this case;
			return DataType.UNKNOWN;
		}
	}
	
	//--------------------------------- DEBUG METHODS
	
	public static Object prepareMessageToServerDataType_SingleTextString(String textString, RequestType requestType) {
		//DEBUG MESSAGE:
		// [CLIENT_REQUEST_TO_SERVER_DEBUG_MESSAGE | SINGLE_TEXT_STRING | _TEXT_STRING_ ]
		ArrayList<String> messageToServer = new ArrayList<String>();	
		messageToServer.add(requestType.toString());
		messageToServer.add(DataType.SINGLE_TEXT_STRING.toString());
		messageToServer.add(textString);
		
		return messageToServer;
	}

}
