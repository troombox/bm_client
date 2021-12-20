package utility.message_parsers;

import java.util.ArrayList;

import utility.enums.DataType;
import utility.enums.RequestType;

public class MessageParser {

	/**
	 * Data Message Layout: [ RequestType (enum) | DataType (enum) | Data strings ]
	 * RequestTypes defined in utility.RequestType
	 */

	// to minimize later changes we took the message parser to be a class of itself;
	public static RequestType parseMessage_RequestType(Object message) {
		// current iteration assumes message as an ArrayList<String>
		ArrayList<String> msg = (ArrayList<String>) message;
		switch (msg.get(0)) {
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
		case "CLIENT_REQUEST_TO_SERVER_LOGIN_REQUEST":
			return RequestType.CLIENT_REQUEST_TO_SERVER_LOGIN_REQUEST;
		case "CLIENT_REQUEST_TO_SERVER_LOGOUT_REQUEST":
			return RequestType.CLIENT_REQUEST_TO_SERVER_LOGOUT_REQUEST;
		case "CLIENT_REQUEST_TO_SERVER_W4C_REQUEST":
			return RequestType.CLIENT_REQUEST_TO_SERVER_W4C_REQUEST;
		case "CLIENT_REQUEST_TO_SERVER_REGISTER_CLIENT":
			return RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_CLIENT;
		case "CLIENT_REQUEST_TO_SERVER_SEARCH_RESTAURANT_REQUEST":
			return RequestType.CLIENT_REQUEST_TO_SERVER_SEARCH_RESTAURANT_REQUEST;
		case "CLIENT_REQUEST_TO_SERVER_INCOMING_FILE":
			return RequestType.CLIENT_REQUEST_TO_SERVER_INCOMING_FILE;
		case "CLIENT_REQUEST_TO_SERVER_REGISTER_SUPPLIER":
			return RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_SUPPLIER;
		case "CLIENT_REQUEST_TO_SERVER_CATEGORY_RESTAURANT_REQUEST":
			return RequestType.CLIENT_REQUEST_TO_SERVER_CATEGORY_RESTAURANT_REQUEST;
		case "CLIENT_REQUEST_TO_SERVER_MENU_REQUEST":
			return RequestType.CLIENT_REQUEST_TO_SERVER_MENU_REQUEST;
		default:
			// TODO: ADD error handling for this case;
			return RequestType.CLIENT_REQUEST_TO_SERVER_UNKNOWN_REQUEST;
		}
	}

	public static DataType parseMessage_DataType(Object message) {
		// current iteration assumes message as an ArrayList<String>
		ArrayList<String> msg = (ArrayList<String>) message;
		switch (msg.get(1)) {
		case "ORDER":
			return DataType.ORDER;
		case "USER":
			return DataType.USER;
		case "CLIENT":
			return DataType.CLIENT;
		case "SINGLE_TEXT_STRING":
			return DataType.SINGLE_TEXT_STRING;
		case "RESTAURANTS_LIST":
			return DataType.RESTAURANTS_LIST;
		case "HR_MANAGER":
			return DataType.HR_MANAGER;
		case "DISHES_LIST":
			return DataType.DISHES_LIST;
		case "ARRAYLIST_STRING":
			return DataType.ARRAYLIST_STRING;
		case "ERROR_MESSAGE":
			return DataType.ERROR_MESSAGE;
		default:
			// TODO: ADD error handling for this case;
			return DataType.UNKNOWN;
		}
	}

	// ---------------------------------------------------Entity parsing functions


	/**
	 * Get Request Layout: [ RequestType | Data Type | DataToGet (id?) ]
	 * RequestTypes defined in utility.RequestType
	 */

}
