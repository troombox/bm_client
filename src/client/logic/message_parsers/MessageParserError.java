package client.logic.message_parsers;

import java.util.ArrayList;

import utility.enums.ErrorType;

public class MessageParserError {
	public static String handleMessageFromServer_ErrorMessageSent(Object message) {
		ArrayList<String> msg = (ArrayList<String>)message;
		if(!msg.get(1).equals("ERROR_MESSAGE")) {
			//TODO:ADD ERROR HANDLING
			return null; 
		}
		return msg.get(3);
	}
	
	public static ErrorType handleMessageFromServer_ErrorTypeSent(Object message) {
		ArrayList<String> msg = (ArrayList<String>)message;
		if(!msg.get(1).equals("ERROR_MESSAGE")) {
			//TODO:ADD ERROR HANDLING
			return null; 
		}
		return ErrorType.fromString(msg.get(2));
	}
	
}
