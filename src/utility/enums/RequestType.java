package utility.enums;

public enum RequestType {
	//RequestTypes with name starting with "SERVER_..." are sent by server to client
	//RequestTypes with name starting with "CLIENT_..." are sent by client to server	
	CLIENT_REQUEST_TO_SERVER_GET_DATA("CLIENT_REQUEST_TO_SERVER_GET_DATA"),						//used to ask server to send some data to client
	CLIENT_REQUEST_TO_SERVER_WRITE_NEW_TO_DB("CLIENT_REQUEST_TO_SERVER_WRITE_NEW_TO_DB"), 		//used to ask server to write new data in server DB 
	CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB("CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB"), //used to ask server to update existing data in DB
	CLIENT_REQUEST_TO_SERVER_CONNECTION_STATUS("CLIENT_REQUEST_TO_SERVER_CONNECTION_STATUS"),	//used by client to update server on client's connection status
	CLIENT_REQUEST_TO_SERVER_DEBUG_MESSAGE("CLIENT_REQUEST_TO_SERVER_DEBUG_MESSAGE"), 			//used to send a debug message from client to server, should not be used
	CLIENT_REQUEST_TO_SERVER_UNKNOWN_REQUEST("CLIENT_REQUEST_TO_SERVER_UNKNOWN_REQUEST"), 		//used on server side for error handling, should not be sent from client
	SERVER_MESSAGE_TO_CLIENT_ERROR("SERVER_MESSAGE_TO_CLIENT_ERROR"),							//used to send error from server to client
	SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED("SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED");			//used to send data from server to client
	
	private String requestType;
	
	private RequestType(String request) {
		this.requestType = request;
	}
	
	@Override
	public String toString() {
		return requestType;
	}
}
