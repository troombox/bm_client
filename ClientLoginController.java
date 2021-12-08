package client_logic;

import java.util.ArrayList;

import entity.Order;
import entity.User;
import utility.UserType;

public class ClientLoginController {
	

	public static User handleMessageFromServer_UserDataSent(Object message) {
		ArrayList<String> msg = (ArrayList<String>)message;
		if(!msg.get(1).equals("USER")) {
			//TODO:ADD ERROR HANDLING
			return null; 
		}
		return new User(Integer.valueOf(msg.get(2)), msg.get(3), msg.get(4),msg.get(5),msg.get(6),msg.get(7),msg.get(8),
				msg.get(9),msg.get(10), msg.get(11).equals("true"), (UserType)msg.get(12));
	
	}

}
