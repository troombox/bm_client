package client_logic;

import java.util.ArrayList;

import entity.Order;
import utility.DataType;
import utility.RequestType;

public class MessageParserOrder extends MessageParser{
	
	
	public static Object prepareMessageToServer_DataTypeOrder(Order order, RequestType requestType) {
		//this method is preparing Order messages, data string for order are:
		//String orderNumber,String restaurantName,String orderTime,String PhoneNumber,String TypeOfOrder,String orderAddress
		ArrayList<String> messageToServer = new ArrayList<String>();
		
		messageToServer.add(requestType.toString());
		messageToServer.add(DataType.ORDER.toString());
		messageToServer.add(order.getOrderNumber());
		if(requestType == RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA) {
			//if we are requesting Order data from server we need only the orderNumber
			return messageToServer;
		}
		messageToServer.add(order.getRestaurantName());
		messageToServer.add(order.getOrderTime());
		messageToServer.add(order.getPhoneNumber());
		messageToServer.add(order.getTypeOfOrder());
		messageToServer.add(order.getOrderAddress());
		
		return messageToServer;
	}



}
