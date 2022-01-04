package utility.enums;

/**
 * The Enum DataType.
 * represent all data type in system. used in message parsers
 */
public enum DataType {
	
	/** The single text string.used for messages that contain a single string of text: Connection/Debug */
	SINGLE_TEXT_STRING("SINGLE_TEXT_STRING"),	
	/** The order. */
	ORDER("ORDER"),								
	/** The user. used for Order Messages */
								
	USER("USER"),								
	/** The client. used for User Messages*/						
	CLIENT("CLIENT"),							
	/** The supplier. used for restaurants type messages*/					
	SUPPLIER("SUPPLIER"),
	/** The hr manager. */
	HR_MANAGER("HR_MANAGER"),
	/** The approve business. */
	APPROVE_BUSINESS("APPROVE_BUSINESS"),
	
	/** The decline business. */
	DECLINE_BUSINESS("DECLINE_BUSINESS"),
	
	/** The get data of business. */
	GET_DATA_OF_BUSINESS("GET_DATA_OF_BUSINESS"),
	
	/** The get data of client. */
	GET_DATA_OF_CLIENT("GET_DATA_OF_CLIENT"),
	
	/** The change permission. */
	CHANGE_PERMISSION("CHANGE_PERMISSION"),
	
	/** The restaurants list. */
	RESTAURANTS_LIST("RESTAURANTS_LIST"),
	
	/** The business client data. */
	BUSINESS_CLIENT_DATA("BUSINESS_CLIENT_DATA"),
	
	/** The error message. */
	ERROR_MESSAGE("ERROR_MESSAGE"),				
	/** The arraylist string. used for Error Messages*/
				
	ARRAYLIST_STRING("ARRAYLIST_STRING"),
	
	/** The dishes list. */
	DISHES_LIST("DISHES_LIST"),
	
	/** The client refunds data. */
	CLIENT_REFUNDS_DATA("CLIENT_REFUNDS_DATA"),
	
	/** The report. */
	REPORT("REPORT"),
	
	/** The orders list. */
	ORDERS_LIST("ORDERS_LIST"),
	
	/** The restaurant. */
	RESTAURANT("RESTAURANT"),
	
	/** The ceo. */
	CEO("ECO"),
	
	/** The dish. */
	DISH("DISH"),
	
	/** The unknown. used internally, should not be sent as a type*/
	UNKNOWN("UNKNOWN");							
	
	/** The data type. */
	private String dataType;
	
	/**
	 * Instantiates a new data type.
	 *
	 * @param dataType the data type
	 */
	private DataType(String dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * To string.
	 *
	 * @return the dataType as string 
	 */
	@Override
	public String toString() {
		return dataType;
	}
}
