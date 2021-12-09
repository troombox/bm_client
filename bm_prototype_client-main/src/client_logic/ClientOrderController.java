package client_logic;

import java.util.ArrayList;

import entity.Order;

public class ClientOrderController {
	
//	public static ArrayList<String> requestOrderInfoFromDB(String orderNumber){
//		ArrayList<String> result = new ArrayList<String>(); 
//		result.add(0, RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA.toString());
//		result.add(1, orderNumber);
//		return result;
//	}
//	
//	public static ArrayList<String> requestOrderUpdateInDB(ArrayList<String> orderDetails){
//		ArrayList<String> result = new ArrayList<String>();
//		result.add(0, RequestType.CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB.toString());
//		result.addAll(orderDetails);
//		return result;
//	}
	
	
	public static Order handleMessageFromServer_OrderDataSent(Object message) {
		ArrayList<String> msg = (ArrayList<String>)message;
		if(!msg.get(1).equals("ORDER")) {
			//TODO:ADD ERROR HANDLING
			return null; 
		}
		return new Order(msg.get(2),msg.get(3),msg.get(4),msg.get(5),msg.get(6),msg.get(7));
	
	}

	public static String handleMessageFromServer_ErrorDataSent(Object message) {
		ArrayList<String> msg = (ArrayList<String>)message;
		if(!msg.get(1).equals("ERROR_MESSAGE")) {
			//TODO:ADD ERROR HANDLING
			return null; 
		}
		return msg.get(2);
	}
	
}
