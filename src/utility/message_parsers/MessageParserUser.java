package utility.message_parsers;

import java.util.ArrayList;

import utility.entity.User;
import utility.enums.DataType;
import utility.enums.RequestType;
import utility.enums.UserType;

/**
 * The Class MessageParserUser -  holds all the functions needed to 
 * send messages regarding User data in the system
 */
public class MessageParserUser {

	/**
	 * Handle message extract data type user from message
	 *
	 * @param message the message to extract data from 
	 * @return the user data
	 */
	public static User handleMessageExtractDataType_User(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		if (!msg.get(1).equals("USER")) {
			// TODO:ADD ERROR HANDLING
			return null;
		}
		return new User(Integer.parseInt(msg.get(2)), msg.get(3), msg.get(4), msg.get(5), msg.get(6), msg.get(7),
				UserType.fromString(msg.get(8)), msg.get(9), msg.get(10), Boolean.parseBoolean(msg.get(11)),
				msg.get(12), Integer.parseInt(msg.get(13)), Integer.parseInt(msg.get(14)));
	}

	/**
	 * Prepare message with data type user.
	 *
	 * @param user the user data to be sent
	 * @param requestType the request type
	 * @return the message object
	 */
	public static Object prepareMessageWithDataType_User(User user, RequestType requestType) {
		// this method is preparing user messages, data string for user are:
		// String orderNumber,String restaurantName,String orderTime,String
		// PhoneNumber,String TypeOfOrder,String orderAddress
		ArrayList<String> messageToPrepare = new ArrayList<String>();

		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.USER.toString());
		messageToPrepare.add(String.valueOf(user.getUser_ID()));
		messageToPrepare.add(user.getFirstName());
		messageToPrepare.add(user.getLastName());
		messageToPrepare.add(user.getPersonalBranch());
		messageToPrepare.add(user.getEmail());
		messageToPrepare.add(user.getPhone());
		messageToPrepare.add(user.getUserType().toString());
		messageToPrepare.add(user.getStatus());
		messageToPrepare.add(user.getW4c());
		messageToPrepare.add(String.valueOf(user.isSignedIn()));
		messageToPrepare.add(user.getPassword());
		messageToPrepare.add(String.valueOf(user.getPersonalCode()));
		messageToPrepare.add(String.valueOf(user.getBuisnessCode()));

		return messageToPrepare;
	}

}
