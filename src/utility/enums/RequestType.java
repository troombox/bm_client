package utility.enums;

// TODO: Auto-generated Javadoc
/**
 * represent all actions related to request type in system.
 * RequestTypes with name starting with "SERVER_..." are sent by server to client
 * RequestTypes with name starting with "CLIENT_..." are sent by client to server
 */
public enum RequestType {
	
	/** The client request to server get data. */
	CLIENT_REQUEST_TO_SERVER_GET_DATA("CLIENT_REQUEST_TO_SERVER_GET_DATA"),						
	/** The client request to server write new to db. used to ask server to send some data to client*/
						
	CLIENT_REQUEST_TO_SERVER_WRITE_NEW_TO_DB("CLIENT_REQUEST_TO_SERVER_WRITE_NEW_TO_DB"), 		
	/** The client request to server write update to db. used to ask server to write new data in server DB */
		 
	CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB("CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB"), 
	/** The client request to server connection status. used to ask server to update existing data in DB*/

	CLIENT_REQUEST_TO_SERVER_CONNECTION_STATUS("CLIENT_REQUEST_TO_SERVER_CONNECTION_STATUS"),	
	/** The client request to server debug message. used by client to update server on client's connection status*/

	CLIENT_REQUEST_TO_SERVER_DEBUG_MESSAGE("CLIENT_REQUEST_TO_SERVER_DEBUG_MESSAGE"), 			
	/** The client request to server unknown request. used to send a debug message from client to server, should not be used*/

	CLIENT_REQUEST_TO_SERVER_UNKNOWN_REQUEST("CLIENT_REQUEST_TO_SERVER_UNKNOWN_REQUEST"), 		
	/** The client request to server login request. used on server side for error handling, should not be sent from client*/
		 
	CLIENT_REQUEST_TO_SERVER_LOGIN_REQUEST("CLIENT_REQUEST_TO_SERVER_LOGIN_REQUEST"),
	
	/** The client request to server logout request. */
	CLIENT_REQUEST_TO_SERVER_LOGOUT_REQUEST("CLIENT_REQUEST_TO_SERVER_LOGOUT_REQUEST"),
	
	/** The client request to server w4c request. */
	CLIENT_REQUEST_TO_SERVER_W4C_REQUEST("CLIENT_REQUEST_TO_SERVER_W4C_REQUEST"),
	
	/** The client request to server register client. */
	CLIENT_REQUEST_TO_SERVER_REGISTER_CLIENT("CLIENT_REQUEST_TO_SERVER_REGISTER_CLIENT"),		
		/** The client request to server register supplier. used for send data about new "client" for registration from client to server*/
 
	CLIENT_REQUEST_TO_SERVER_REGISTER_SUPPLIER("CLIENT_REQUEST_TO_SERVER_REGISTER_SUPPLIER"),	
	/** The client request to server search restaurant request. used for send data about new supplier for registration from client to server*/

	CLIENT_REQUEST_TO_SERVER_SEARCH_RESTAURANT_REQUEST("CLIENT_REQUEST_TO_SERVER_SEARCH_RESTAURANT_REQUEST"),	
	
	/** The client request to server approve business. */
	CLIENT_REQUEST_TO_SERVER_APPROVE_BUSINESS("CLIENT_REQUEST_TO_SERVER_APPROVE_BUSINESS"),
	
	/** The client request to server decline business. */
	CLIENT_REQUEST_TO_SERVER_DECLINE_BUSINESS("CLIENT_REQUEST_TO_SERVER_DECLINE_BUSINESS"),
	
	/** The client request to server get data businesses names. */
	CLIENT_REQUEST_TO_SERVER_GET_DATA_BUSINESSES_NAMES("CLIENT_REQUEST_TO_SERVER_GET_DATA_BUSINESSES_NAMES"),
	
	/** The client request to server get data client info. */
	CLIENT_REQUEST_TO_SERVER_GET_DATA_CLIENT_INFO("CLIENT_REQUEST_TO_SERVER_GET_DATA_CLIENT_INFO"),
	
	/** The client request to server change permission. */
	CLIENT_REQUEST_TO_SERVER_CHANGE_PERMISSION("CLIENT_REQUEST_TO_SERVER_CHANGE_PERMISSION"),
	
	/** The client request to server open report. */
	CLIENT_REQUEST_TO_SERVER_OPEN_REPORT("CLIENT_REQUEST_TO_SERVER_OPEN_REPORT"),
	
	/** The client request to server open supplier income report. */
	CLIENT_REQUEST_TO_SERVER_OPEN_SUPPLIER_INCOME_REPORT("CLIENT_REQUEST_TO_SERVER_OPEN_SUPPLIER_INCOME_REPORT"),
	
	/** The client request to server check aprrove business. */
	CLIENT_REQUEST_TO_SERVER_CHECK_APRROVE_BUSINESS("CLIENT_REQUEST_TO_SERVER_CHECK_APRROVE_BUSINESS"),
	
	/** The client request to server aprrove business. */
	CLIENT_REQUEST_TO_SERVER_APRROVE_BUSINESS("CLIENT_REQUEST_TO_SERVER_APRROVE_BUSINESS"),
	
	/** The client request to server get approved business clients. */
	CLIENT_REQUEST_TO_SERVER_GET_APPROVED_BUSINESS_CLIENTS("CLIENT_REQUEST_TO_SERVER_GET_APPROVED_BUSINESS_CLIENTS"),
	
	/** The client request to server supplier update order. */
	CLIENT_REQUEST_TO_SERVER_SUPPLIER_UPDATE_ORDER("CLIENT_REQUEST_TO_SERVER_SUPPLIER_UPDATE_ORDER"),
	
	/** The client request to server send report to ceo. */
	CLIENT_REQUEST_TO_SERVER_SEND_REPORT_TO_CEO("CLIENT_REQUEST_TO_SERVER_SEND_REPORT_TO_CEO"),
	
	/** The client request to server get orders by restaurant id request. */
	CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST("CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST"),
	
	/** The client request to server get restaurant by supplier request. */
	CLIENT_REQUEST_TO_SERVER_GET_RESTAURANT_BY_SUPPLIER_REQUEST("CLIENT_REQUEST_TO_SERVER_GET_RESTAURANT_BY_SUPPLIER_REQUEST"),
	
	/** The client request to server add dish to menu request. */
	CLIENT_REQUEST_TO_SERVER_ADD_DISH_TO_MENU_REQUEST("CLIENT_REQUEST_TO_SERVER_ADD_DISH_TO_MENU_REQUEST"),
	
	/** The client request to server update dish in menu request. */
	CLIENT_REQUEST_TO_SERVER_UPDATE_DISH_IN_MENU_REQUEST("CLIENT_REQUEST_TO_SERVER_UPDATE_DISH_IN_MENU_REQUEST"),
	
	/** The client request to server supplier cancel order. */
	CLIENT_REQUEST_TO_SERVER_SUPPLIER_CANCEL_ORDER("CLIENT_REQUEST_TO_SERVER_SUPPLIER_CANCEL_ORDER"),
	
	/** The client request to server ceo quarterly report. */
	CLIENT_REQUEST_TO_SERVER_CEO_QUARTERLY_REPORT("CLIENT_REQUEST_TO_SERVER_CEO_QUARTERLY_REPORT"),
	
	/** The client request to server ceo get branches reports. */
	CLIENT_REQUEST_TO_SERVER_CEO_GET_BRANCHES_REPORTS("CLIENT_REQUEST_TO_SERVER_CEO_GET_BRANCHES_REPORTS"),
	
	/** The client request to server delete dish from menu request. */
	CLIENT_REQUEST_TO_SERVER_DELETE_DISH_FROM_MENU_REQUEST("CLIENT_REQUEST_TO_SERVER_DELETE_DISH_FROM_MENU_REQUEST"),
	
	/** The client request to server open report from branch. */
	CLIENT_REQUEST_TO_SERVER_OPEN_REPORT_FROM_BRANCH("CLIENT_REQUEST_TO_SERVER_OPEN_REPORT_FROM_BRANCH"),
	
	/** The server message to client updated dish success. */
	SERVER_MESSAGE_TO_CLIENT_UPDATED_DISH_SUCCESS("SERVER_MESSAGE_TO_CLIENT_UPDATED_DISH_SUCCESS"),
	
	/** The server message to client login success. */
	SERVER_MESSAGE_TO_CLIENT_LOGIN_SUCCESS("SERVER_MESSAGE_TO_CLIENT_LOGIN_SUCCESS"),
	
	/** The server message to client decline business success. */
	SERVER_MESSAGE_TO_CLIENT_DECLINE_BUSINESS_SUCCESS("SERVER_MESSAGE_TO_CLIENT_DECLINE_BUSINESS_SUCCESS"),
	
	/** The server message to client error. */
	SERVER_MESSAGE_TO_CLIENT_ERROR("SERVER_MESSAGE_TO_CLIENT_ERROR"),							
	/** The server message to client data provided.	used to send error from server to client */

	SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED("SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED"),			
	/** The server message to client register success. used to send data from server to client*/
		
	SERVER_MESSAGE_TO_CLIENT_REGISTER_SUCCESS("SERVER_MESSAGE_TO_CLIENT_REGISTER_SUCCESS"),
	
	/** The server message to client supplier register success. */
	SERVER_MESSAGE_TO_CLIENT_SUPPLIER_REGISTER_SUCCESS("SERVER_MESSAGE_TO_CLIENT_SUPPLIER_REGISTER_SUCCESS"),
	
	/** The server message to client refund amount. */
	SERVER_MESSAGE_TO_CLIENT_REFUND_AMOUNT("SERVER_MESSAGE_TO_CLIENT_CLIENT_REFUND_AMOUNT"),
	
	/** The server message to client approve business success. */
	SERVER_MESSAGE_TO_CLIENT_APPROVE_BUSINESS_SUCCESS("SERVER_MESSAGE_TO_CLIENT_APPROVE_BUSINESS_SUCCESS"),
	
	/** The server message to client change permission success. */
	SERVER_MESSAGE_TO_CLIENT_CHANGE_PERMISSION_SUCCESS("SERVER_MESSAGE_TO_CLIENT_CHANGE_PERMISSION_SUCCESS"),
	
	/** The server message to client open report success. */
	SERVER_MESSAGE_TO_CLIENT_OPEN_REPORT_SUCCESS("SERVER_MESSAGE_TO_CLIENT_OPEN_REPORT_SUCCESS"),
	
	/** The server message to client send to ceo success. */
	SERVER_MESSAGE_TO_CLIENT_SEND_TO_CEO_SUCCESS("SERVER_MESSAGE_TO_CLIENT_SEND_TO_CEO_SUCCESS"),
	
	/** The server message to client ceo get branches reports. */
	SERVER_MESSAGE_TO_CLIENT_CEO_GET_BRANCHES_REPORTS("SERVER_MESSAGE_TO_CLIENT_CEO_GET_BRANCHES_REPORTS"),
	
	/** The client request to server incoming file. */
	CLIENT_REQUEST_TO_SERVER_INCOMING_FILE("CLIENT_REQUEST_TO_SERVER_INCOMING_FILE"),
	
	/** The client request to server category restaurant request. */
	CLIENT_REQUEST_TO_SERVER_CATEGORY_RESTAURANT_REQUEST("CLIENT_REQUEST_TO_SERVER_CATEGORY_RESTAURANT_REQUEST"), 
	
	/** The client request to server update refund amount after refunds used. */
	CLIENT_REQUEST_TO_SERVER_UPDATE_REFUND_AMOUNT_AFTER_REFUNDS_USED("CLIENT_REQUEST_TO_SERVER_UPDATE_REFUND_AMOUNT_AFTER_REFUNDS_USED"),
	
	/** The client request to server get client refunds data. */
	CLIENT_REQUEST_TO_SERVER_GET_CLIENT_REFUNDS_DATA("CLIENT_REQUEST_TO_SERVER_GET_CLIENT_REFUNDS_DATA"),
	
	/** The client request to server business client data. */
	CLIENT_REQUEST_TO_SERVER_BUSINESS_CLIENT_DATA("CLIENT_REQUEST_TO_SERVER_BUSINESS_CLIENT_DATA"), 
	
	/** The client request to server business client budged update. */
	CLIENT_REQUEST_TO_SERVER_BUSINESS_CLIENT_BUDGED_UPDATE("CLIENT_REQUEST_TO_SERVER_BUSINESS_CLIENT_BUDGED_UPDATE"),
	
	/** The client request to server menu request. */
	CLIENT_REQUEST_TO_SERVER_MENU_REQUEST("CLIENT_REQUEST_TO_SERVER_MENU_REQUEST");			
	
	/** The request type. */
	private String requestType;
	
	/**
	 * Instantiates a new request type.
	 *
	 * @param request the request
	 */
	private RequestType(String request) {
		this.requestType = request;
	}
	
	/**
	 * To string.
	 *
	 * @return the requestType string
	 */
	@Override
	public String toString() {
		return requestType;
	}
}
