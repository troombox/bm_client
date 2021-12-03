package server_logic;

import java.util.ArrayList;

import entity.Order;
import utility.DataType;
import utility.RequestType;

public class MessageParser {
	
	/**
	 * Data Message Layout:
	 * [ RequestType (enum) | DataType (enum) |  Data strings ]
	 *  RequestTypes defined in utility.RequestType
	 */
	
	//to minimize later changes we took the message parser to be a class of itself;
	public static RequestType parseMessageFromClient_RequestType(Object message) {
		//current iteration assumes message as an ArrayList<String>
		ArrayList<String> msg = (ArrayList<String>)message;
		switch(msg.get(0)) {
		case "CLIENT_REQUEST_TO_SERVER_GET_DATA":
			return RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA;
		case "CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB":
			return RequestType.CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB;
		case "CLIENT_REQUEST_TO_SERVER_WRITE_NEW_TO_DB":
			return RequestType.CLIENT_REQUEST_TO_SERVER_WRITE_NEW_TO_DB;
		case "CLIENT_REQUEST_TO_SERVER_CONNECTION_STATUS":
			return RequestType.CLIENT_REQUEST_TO_SERVER_CONNECTION_STATUS;
		case "CLIENT_REQUEST_TO_SERVER_DEBUG_MESSAGE":
			return RequestType.CLIENT_REQUEST_TO_SERVER_DEBUG_MESSAGE;
		default:
			//TODO: ADD error handling for this case;
			return RequestType.CLIENT_REQUEST_TO_SERVER_UNKNOWN_REQUEST;
		}
	}
	
	public static DataType parseMessageFromClient_DataType(Object message) {
		//current iteration assumes message as an ArrayList<String>
		ArrayList<String> msg = (ArrayList<String>)message;
		switch(msg.get(1)) {
		case "ORDER":
			return DataType.ORDER;
		case "SINGLE_TEXT_STRING":
			return DataType.SINGLE_TEXT_STRING;
		default:
			//TODO: ADD error handling for this case;
			return DataType.UNKNOWN;
		}
	}
	
	//---------------------------------------------------Entity parsing functions
	
	public static Order parseMessageDataType_Order(Object message) {
		ArrayList<String> msg = (ArrayList<String>)message;
		if(!msg.get(1).equals("ORDER")) {
			//TODO: ADD error handling for this case;
			System.out.println("Error parseMessageDataType_Order not order");
			return null;
		}
		Order order = new Order(msg.get(2), msg.get(3), msg.get(4), msg.get(5), msg.get(6), msg.get(7));
		return order;
	}
	
	
	/**
	 * Get Request Layout:
	 * [ RequestType | Data Type |  DataToGet (id?) ]
	 * RequestTypes defined in utility.RequestType
	 */
	
	public static String parseMessageDataType_Order_GetRequestOrderID(Object message) {
		ArrayList<String> msg = (ArrayList<String>)message;
		if(!msg.get(1).equals("ORDER")) {
			//TODO: ADD error handling for this case;
			System.out.println("Error parseMessageDataType_Order_GetRequestOrderID not order");
			return null;
		}
		String id = msg.get(2);
		return id;
	}
	
	
	public static String parseMessageDataType_SingleTextString(Object message) {
		ArrayList<String> msg = (ArrayList<String>)message;
		if(!msg.get(1).equals("SINGLE_TEXT_STRING")) {
			//TODO: ADD error handling for this case;
			System.out.println("Error parseMessageDataType_SingleTextString not string");
			return null;
		}
		String string = msg.get(2);
		return string;
	}
	
	
	//-------------------------------------------------Entity wrapping functions
	
	public static ArrayList<String> createMessageToClientDataType_Order(Order order) {
		if (order == null) {
			// TODO: Error handling
			System.out.println("createMessageToClientDataType_Order Failed");
			return null;
		}
		ArrayList<String> result = new ArrayList<String>();
		result.add(RequestType.SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED.toString());
		result.add(DataType.ORDER.toString());
		result.add(order.getOrderNumber());
		result.add(order.getRestaurantName());
		result.add(order.getOrderTime());
		result.add(order.getPhoneNumber());
		result.add(order.getTypeOfOrder());
		result.add(order.getOrderAddress());
		return result;
	}

	public static ArrayList<String> createMessageToClientDataType_Error(String error) {
		if (error == null) {
			// TODO: Error handling
			System.out.println("createMessageToClientDataType_Error Failed");
			return null;
		}
		ArrayList<String> result = new ArrayList<String>();
		result.add(RequestType.SERVER_MESSAGE_TO_CLIENT_ERROR.toString());
		result.add(DataType.ERROR_MESSAGE.toString());
		result.add(error);
		return result;
	}
	
	
}
