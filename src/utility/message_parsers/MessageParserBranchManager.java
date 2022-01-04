package utility.message_parsers;

import java.util.ArrayList;

import utility.entity.Business;
import utility.entity.Client;
import utility.entity.ClientChangePermission;
import utility.entity.Supplier;
import utility.enums.DataType;
import utility.enums.RequestType;
import utility.enums.UserType;

/**
 * The Class MessageParserBranchManager. holds parsers that take care of BranchManager data sent from server to client
 * and data sent from client to server
 */
public class MessageParserBranchManager {

	/**
	 * Handle message extract data type client - used to extract Client data from messages that contain it
	 *
	 * @param message the message
	 * @return the Client data
	 */
	//----------------------------------------------------------------------------->client
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

	/**
	 * Prepare message with data type client - this method is used for 
	 * preparing user messages with Class Client Data
	 *
	 * @param client the Client class that holds the data 
	 * @param requestType the request type
	 * @return the message as a Class Object
	 */
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
	
	/**
	 * Prepare message with result of Client registration data.
	 *
	 * @param requestType the request type
	 * @return the message as a Class Object
	 */
	public static Object prepareMessageWithResultOfRegisterion_Client(RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.CLIENT.toString());
		
		return messageToPrepare;
	}
	
	/**
	 * Handle message extract data type result registration client.
	 *
	 * @param message the message
	 * @return the string
	 */
	public static String handleMessageExtractDataTypeResultRegistretion_Client(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		return msg.get(0);
	}
	
	/**
	 * Handle message extract data type supplier.
	 *
	 * @param message the message 
	 * @return the supplier
	 */
	//------------------------------------------------------------------------------------->supplier
	public static Supplier handleMessageExtractDataType_Supplier(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		if (!msg.get(1).equals("SUPPLIER")) {
			// TODO:ADD ERROR HANDLING
			return null;
		}
		return new Supplier(msg.get(2), msg.get(3), msg.get(4), msg.get(5), msg.get(6));
	}
	
	/**
	 * Prepare message with data type supplier.
	 *
	 * @param supplier the supplier
	 * @param requestType the request type
	 * @return the message as a Class Object
	 */
	public static Object prepareMessageWithDataType_Supplier(Supplier supplier, RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.SUPPLIER.toString());
		messageToPrepare.add(supplier.getRestaurantName());
		messageToPrepare.add(supplier.getImagePath());
		messageToPrepare.add(supplier.getCategories());
		messageToPrepare.add(supplier.getPersonalBranch());
		messageToPrepare.add(supplier.getWorkerID());
		
		return messageToPrepare;
	}
	
	/**
	 * Prepare message with result of supplier registration.
	 *
	 * @param requestType the request type
	 * @return the message as a Class Object
	 */
	public static Object prepareMessageWithResultOfRegisterion_Supplier(RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.SUPPLIER.toString());
		
		return messageToPrepare;
	}
	
	/**
	 * Handle message extract data type result of supplier registration.
	 *
	 * @param message the message
	 * @return the string that holds the result message
	 */
	public static String handleMessageExtractDataTypeResultRegistretion_Supplier(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		return msg.get(0);
	}
	//------------------------------------------------>approve_business
	
	/**
	 * Prepare message that specifically requests GET_DATA_OF_BUSINESS request.
	 *
	 * @param branchName the branch name to request the data of
	 * @param requestType the request type, disregarded in this method (for some reason)
	 * @return the message as a Class Object
	 */
	public static Object prepareMessageWithDataType_GET_DATA_OF_BUSINESS(String branchName ,RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.GET_DATA_OF_BUSINESS.toString());
		messageToPrepare.add(branchName);
		return messageToPrepare;
	}
	
	/**
	 * Prepare message with result of getting data of a business.
	 *
	 * @param requestType the request type
	 * @param message the message
	 * @return the message as a Class Object
	 */
	public static Object prepareMessageWithResultOfGettingData_Business(RequestType requestType,Object message) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		ArrayList<String> msg = (ArrayList<String>)message;
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.GET_DATA_OF_BUSINESS.toString());
		messageToPrepare.addAll(msg);
		
		return messageToPrepare;
	}
	
	/**
	 * Handle message to extract data type Class Business.
	 *
	 * @param message the message
	 * @return the business data as a class Business Object
	 */
	public static Business handleMessageExtractDataType_Business(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		if (!msg.get(1).equals("APPROVE_BUSINESS")) {
			// TODO:ADD ERROR HANDLING
			return null;
		}
		return new Business(Integer.parseInt(msg.get(2)),msg.get(3),Integer.parseInt(msg.get(4)),Integer.parseInt(msg.get(5)),msg.get(6));
	}

	/**
	 * Prepare message with data type business.
	 *
	 * @param business the business
	 * @param requestType the request type
	 * @return the message as a Class Object
	 */
	public static Object prepareMessageWithDataType_Business(Business business, RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();

		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.APPROVE_BUSINESS.toString());
		messageToPrepare.add(String.valueOf(business.getBusinessId()));
		messageToPrepare.add(business.getBusinessName());
		messageToPrepare.add(String.valueOf(business.getIsApproved()));
		messageToPrepare.add(String.valueOf(business.getHr_id()));
		messageToPrepare.add(business.getBranch());
		
		
		return messageToPrepare;
	}
	
	/**
	 * Prepare message with result of APPROVE_BUSINESS request.
	 *
	 * @param requestType the request type
	 * @return the message as a Class Object
	 */
	public static Object prepareMessageWithResultOfApproveBusiness(RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.APPROVE_BUSINESS.toString());
		
		return messageToPrepare;
	}
	
	/**
	 * Handle message and extract result of APPROVE_BUSINESS request.
	 *
	 * @param message the message
	 * @return the string that holds the message
	 */
	public static String handleMessageExtractDataTypeResultOfApproveBusiness(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		return msg.get(0);
	}

	
	/**
	 * Prepare message with a GET_DATA_OF_CLIENT request
	 *
	 * @param branchName the branch name
	 * @param requestType the request type (unused)
	 * @return the message as a Class Object
	 */
	//-------------------------------------------------------->user
	public static Object prepareMessageWithDataType_getDataOfClient(String branchName,RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();

		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.GET_DATA_OF_CLIENT.toString());
		messageToPrepare.add(branchName);
		return messageToPrepare;
	}	
	
	/**
	 * Prepare message with GET_DATA_OF_CLIENT request
	 *
	 * @param clientList the ArrayList clientList of data
	 * @param requestType the request type (unused)
	 * @return the message as a Class Object
	 */
	public static Object prepareMessageWithDataType_GetDataOfClient(ArrayList<String> clientList, RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();

		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.GET_DATA_OF_CLIENT.toString());
		messageToPrepare.addAll(clientList);
		return messageToPrepare;
	}
	
	/**
	 * Handle message extract the result of a GET_DATA_OF_CLIENT request
	 *
	 * @param message the message
	 * @return the array list of class ClientChangePermission data
	 */
	public static ArrayList<ClientChangePermission> handleMessageExtractDataType_GetDataOfClient(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		ArrayList<ClientChangePermission> parsedData = new ArrayList<ClientChangePermission>();
		if (!msg.get(1).equals("GET_DATA_OF_CLIENT")) {
			// TODO:ADD ERROR HANDLING
			return null;
		}
		for(int i = 2 ; i+4 < msg.size() ; i+=5) {
			ClientChangePermission client = new ClientChangePermission(msg.get(i), msg.get(i+1),msg.get(i+2), msg.get(i+3),msg.get(i+4));
			parsedData.add(client);
		}
		return parsedData;
	}
	
	/**
	 * Prepare message with CHANGE_PERMISSION request
	 *
	 * @param client the class ClientChangePermission object, holding the relevant data
	 * @param requestType the request type
	 * @return the message as a Class Object
	 */
	public static Object prepareMessageWithDataType_changePermission(ClientChangePermission client,RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();

		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.CHANGE_PERMISSION.toString());
		messageToPrepare.add(client.getFirstName());
		messageToPrepare.add(client.getLastName());
		messageToPrepare.add(client.getBranch());
		messageToPrepare.add(client.getStatus());
		messageToPrepare.add(client.getId());
		return messageToPrepare;
	}
	
	/**
	 * Prepare message with result of CHANGE_PERMISSION request .
	 *
	 * @param requestType the request type
	 * @return the message as a Class Object
	 */
	public static Object prepareMessageWithResultOfChangePermission(RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		messageToPrepare.add(requestType.toString());
		if(requestType == RequestType.SERVER_MESSAGE_TO_CLIENT_SEND_TO_CEO_SUCCESS)
			messageToPrepare.add(DataType.REPORT.toString());
		else
			messageToPrepare.add(DataType.CHANGE_PERMISSION.toString());
		
		return messageToPrepare;
	}
	
	/**
	 * Handle message extract from array list - extract message from arraylist to 
	 * string ,for change permission and report
	 *
	 * @param message the message
	 * @return the string that holds the message
	 */
	public static String handleMessageExtractFromArrayList(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		return msg.get(0);
	}

}
