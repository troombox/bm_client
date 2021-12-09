package client.logic.entity_controllers;

import java.util.ArrayList;

import utility.entity.User;
import utility.enums.UserType;


public class ClientLoginController {
	
		public static User handleMessageFromServer_UserDataSent(Object message) {
		ArrayList<String> msg = (ArrayList<String>)message;
		if(!msg.get(1).equals("ORDER")) {
			//TODO:ADD ERROR HANDLING
			return null; 
		}

		return new User(Integer.parseInt(msg.get(2)),msg.get(3),msg.get(4),msg.get(5),msg.get(6),msg.get(7), UserType.valueOf(msg.get(8)), msg.get(9), msg.get(10),Boolean.parseBoolean(msg.get(11)), msg.get(12));
	
	}
		

}
