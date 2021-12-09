package client_logic;

import java.util.ArrayList;

import entity.User;
import utility.DataType;
import utility.RequestType;

public class MessageParserUser extends MessageParser {
	
	public static Object prepareMessageToServer_DataTypeUser(User user, RequestType requestType) {
		//this method is preparing user messages, data string for user are:
		//String orderNumber,String restaurantName,String orderTime,String PhoneNumber,String TypeOfOrder,String orderAddress
		ArrayList<String> messageToServer = new ArrayList<String>();
		
		messageToServer.add(requestType.toString());
		messageToServer.add(String.valueOf(DataType.USER));
		
		messageToServer.add(String.valueOf(user.getUser_ID()));
		messageToServer.add(user.getFirstName());
		messageToServer.add(user.getLastName());
		messageToServer.add(user.getPersonalBranch());
		messageToServer.add(user.getEmail());
		messageToServer.add(user.getPhone());
		messageToServer.add(user.getStatus());
		messageToServer.add(user.getW4c());
		messageToServer.add(String.valueOf(user.isSignedIn()));
		messageToServer.add(user.getPassword());
		
		
		return messageToServer;
		
		
		
	}

}
