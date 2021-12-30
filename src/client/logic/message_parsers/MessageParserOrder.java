package client.logic.message_parsers;

import java.util.ArrayList;

import utility.entity.Order;
import utility.enums.DataType;
import utility.enums.RequestType;


public class MessageParserOrder extends MessageParser{
	
	
	public static Object prepareMessageToServer_DataTypeOrder(Order order, RequestType requestType) {
		//this method is preparing Order messages, data string for order are:
		//String orderNumber,String restaurantName,String orderTime,String PhoneNumber,String TypeOfOrder,String orderAddress
		ArrayList<String> messageToServer = new ArrayList<String>();
		
		messageToServer.add(requestType.toString());
		messageToServer.add(DataType.ORDER.toString());
		messageToServer.add(String.valueOf(order.getOrderID()));
		if(requestType == RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA) {
			//if we are requesting Order data from server we need only the orderNumber
			return messageToServer;
		}
		messageToServer.add(String.valueOf(order.getRestaurantID()));
		messageToServer.add(order.getTimeOfOrder());
		messageToServer.add(order.getUserPhone());
		messageToServer.add(order.getTypeOfOrder());
		messageToServer.add(order.getDeliveryAddress());
		
		return messageToServer;
	}
	
	public static Order handleMessageFromServer_OrderDataSent(Object message) {
		ArrayList<String> msg = (ArrayList<String>)message;
		if(!msg.get(1).equals("ORDER")) {
			//TODO:ADD ERROR HANDLING
			return null; 
		}
		return new Order(msg.get(2),msg.get(3),msg.get(4),msg.get(5),Integer.parseInt(msg.get(6)),msg.get(7));
	}

}
