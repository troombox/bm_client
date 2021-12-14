package utility.message_parsers;

import java.util.ArrayList;

import utility.entity.Client;
import utility.entity.User;
import utility.enums.DataType;
import utility.enums.RequestType;
import utility.enums.UserType;

public class MessageParserBranchManager {

	public static Client handleMessageExtractDataType_Client(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		if (!msg.get(1).equals("CLIENT")) {
			// TODO:ADD ERROR HANDLING
			return null;
		}
		return new Client(Integer.parseInt(msg.get(2)), msg.get(3), msg.get(4), msg.get(5), msg.get(6), msg.get(7),
				UserType.fromString(msg.get(8)), msg.get(9), msg.get(10), Boolean.parseBoolean(msg.get(11)),
				msg.get(12), Integer.parseInt(msg.get(13)), Integer.parseInt(msg.get(14)),Integer.parseInt(msg.get(15)),Integer.parseInt(msg.get(16)),
				Integer.parseInt(msg.get(17)),Integer.parseInt(msg.get(18)),Integer.parseInt(msg.get(19)));
	}

	public static Object prepareMessageWithDataType_Client(Client client, RequestType requestType) {
		// this method is preparing user messages, data string for user are:
		// String orderNumber,String restaurantName,String orderTime,String
		// PhoneNumber,String TypeOfOrder,String orderAddress
		ArrayList<String> messageToPrepare = new ArrayList<String>();

		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.CLIENT.toString());
		messageToPrepare.add(String.valueOf(client.getUser_ID()));
		messageToPrepare.add(client.getFirstName());
		messageToPrepare.add(client.getLastName());
		messageToPrepare.add(client.getPersonalBranch());
		messageToPrepare.add(client.getEmail());
		messageToPrepare.add(client.getPhone());
		messageToPrepare.add(client.getUserType().toString());
		messageToPrepare.add(client.getStatus());
		messageToPrepare.add(client.getW4c());
		messageToPrepare.add(String.valueOf(client.isSignedIn()));
		messageToPrepare.add(client.getPassword());
		messageToPrepare.add(String.valueOf(client.getPersonalCode()));
		messageToPrepare.add(String.valueOf(client.getBuisnessCode()));
		messageToPrepare.add(String.valueOf(client.getBalanceInApp()));
		messageToPrepare.add(String.valueOf(client.getBudget()));
		messageToPrepare.add(String.valueOf(client.getIsApproved()));
		messageToPrepare.add(String.valueOf(client.getBusinessId()));
		messageToPrepare.add(String.valueOf(client.getPersonalCreditNumber()));
		
		return messageToPrepare;
	}
	
	public static Object prepareMessageWithResultOfRegisterion_Client(RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.CLIENT.toString());
		
		return messageToPrepare;
	}
	
	public static String handleMessageExtractDataTypeResultRegistretion_Client(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		return msg.get(0);
	}
}
