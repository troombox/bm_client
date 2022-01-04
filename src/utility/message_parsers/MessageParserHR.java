package utility.message_parsers;

import java.util.ArrayList;

import utility.entity.Business;
import utility.entity.BusinessClient;
import utility.enums.DataType;
import utility.enums.RequestType;


/**
 * The Class MessageParserHR - holds all the functions needed to 
 * send messages regarding HR functions in the system
 */
public class MessageParserHR {
	
	/**
	 * Handle message extract ArrayList of BusinessClient data.
	 *
	 * @param message the message to extract data from
	 * @return ArrayList of BusinessClient data
	 */
	public static ArrayList<BusinessClient>  handleMessageExtractDataType_HRGetData(Object message) {
		ArrayList<BusinessClient> parsedData = new ArrayList<BusinessClient>();
		ArrayList<String> DataFromServer = (ArrayList<String>)message;
		int count = 0;
		if (!DataFromServer.get(1).equals("HR_MANAGER")) {
			return null;
		}
		for(int i = 2 ; i+3 < DataFromServer.size() ; i+=4) {
			BusinessClient businessClient = new BusinessClient(DataFromServer.get(i), DataFromServer.get(i+1),DataFromServer.get(i+2), DataFromServer.get(i+3));
			parsedData.add(businessClient);
		}
		return parsedData;
	}
	
	/**
	 * Prepare message to server containing a request to get HR data of HR manager
	 *
	 * @param userID the user ID of HR manager, who's data is requested
	 * @param dataType the data type (unused, instead DataType.HR_MANAGER is sent)
	 * @param requestType the request type
	 * @return the message object
	 */
	public static Object prepareMessageToServer_HRDataRequest(int userID, DataType dataType,RequestType requestType) {	
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.HR_MANAGER.toString());
		messageToPrepare.add(String.valueOf(userID));
		
		return messageToPrepare;

	}
	
	/**
	 * Prepare message to server from HR to update DB with business client data
	 *
	 * @param businessClient the BusinessClient object with data to be written to db
	 * @param dataType the data type, unused - instead DataType.HR_MANAGER
	 * @param requestType the request type
	 * @return the message object
	 */
	public static Object prepareMessageToServer_HRUpdateDB(BusinessClient businessClient,  DataType dataType,RequestType requestType) {	
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.HR_MANAGER.toString());
		messageToPrepare.add(businessClient.getWorkerID());
		messageToPrepare.add(String.valueOf(businessClient.getIsApproved()));
				
		return messageToPrepare;

	}
	
	/**
	 * Handle message from client with HR data
	 *
	 * @param msg the message to parse
	 * @return ArrayList of Strings that represent HR data
	 */
	public static ArrayList<String> handleMessageFromClient_HRGetData(Object msg){
		return (ArrayList<String>)msg;
	}
	
	/**
	 * Prepare message to server from HR to approve business.
	 *
	 * @param hr_id the id for HR manager
	 * @param dataType the data type
	 * @param requestType the request type
	 * @return the message object
	 */
	public static Object prepareMessageToServer_HRApproveBusiness(int hr_id,  DataType dataType,RequestType requestType) {	
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.HR_MANAGER.toString());
		messageToPrepare.add(String.valueOf(hr_id));

		return messageToPrepare;

	}
	
	
	/**
	 * Handle message from client approve business client.
	 *
	 * @param messageFromClient the message from client
	 * @return the Business class object
	 */
	public static Business handleMessageFromClient_ApproveBusinessClient(ArrayList<String> messageFromClient) {
        Business business = new Business(Integer.parseInt(messageFromClient.get(2)), messageFromClient.get(3), 1, Integer.parseInt(messageFromClient.get(4)), messageFromClient.get(5));
        return business;
    }
	
	
	/**
	 * Prepare message to server HR check approve business.
	 *
	 * @param isApproved the isapproved value
	 * @param dataType the data type enum
	 * @param requestType the request type
	 * @return the array list of strings holding approved businesses
	 */
	public static ArrayList<String> prepareMessageToServer_HRCheckApproveBusiness(int isApproved,  DataType dataType,RequestType requestType) {	
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.HR_MANAGER.toString());
		messageToPrepare.add(String.valueOf(isApproved));
	
		return messageToPrepare;

	}
	
	/**
	 * Prepare message to server HR register new business.
	 *
	 * @param business the business
	 * @param dataType the data type
	 * @param requestType the request type
	 * @return the array list
	 */
	public static ArrayList<String> prepareMessageToServer_HRRegisterNewBusiness(Business business,  DataType dataType,RequestType requestType) {
        ArrayList<String> messageToPrepare = new ArrayList<String>();

        messageToPrepare.add(requestType.toString());
        messageToPrepare.add(DataType.HR_MANAGER.toString());
        messageToPrepare.add(String.valueOf(business.getBusinessId()));
        messageToPrepare.add(business.getBusinessName());
        messageToPrepare.add(String.valueOf(business.getHr_id()));
        messageToPrepare.add(business.getBranch());

        return messageToPrepare;

    }
	
	/**
	 * Handle message from client get hr id value.
	 *
	 * @param messageFromClient the message from client
	 * @return the int value
	 */
	public static int handleMessageFromClient_getHrId(ArrayList<String> messageFromClient) {
        return Integer.parseInt(messageFromClient.get(2));
    }
}


