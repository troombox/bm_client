package client.logic.message_parsers;

import java.util.ArrayList;

import utility.entity.User;
import utility.enums.DataType;
import utility.enums.RequestType;
import utility.enums.UserType;

public class MessageParserUser {

	public static User handleMessageFromServer_UserDataSent(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		if (!msg.get(1).equals("USER")) {
			// TODO:ADD ERROR HANDLING
			return null;
		}
		return new User(Integer.parseInt(msg.get(2)), msg.get(3), msg.get(4), msg.get(5), msg.get(6), msg.get(7),
				UserType.fromString(msg.get(8)), msg.get(9), msg.get(10), Boolean.parseBoolean(msg.get(11)),
				msg.get(12));
	}

	public static Object prepareMessageToServerDataType_User(User user, RequestType requestType) {
		// this method is preparing user messages, data string for user are:
		// String orderNumber,String restaurantName,String orderTime,String
		// PhoneNumber,String TypeOfOrder,String orderAddress
		ArrayList<String> messageToServer = new ArrayList<String>();

		messageToServer.add(requestType.toString());
		messageToServer.add(DataType.USER.toString());
		messageToServer.add(String.valueOf(user.getUser_ID()));
		messageToServer.add(user.getFirstName());
		messageToServer.add(user.getLastName());
		messageToServer.add(user.getPersonalBranch());
		messageToServer.add(user.getEmail());
		messageToServer.add(user.getPhone());
		messageToServer.add(user.getUserType().toString());
		messageToServer.add(user.getStatus());
		messageToServer.add(user.getW4c());
		messageToServer.add(String.valueOf(user.isSignedIn()));
		messageToServer.add(user.getPassword());

		return messageToServer;
	}

}
