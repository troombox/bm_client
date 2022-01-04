package utility.enums;

/**
 * The Enum ErrorType.
 * represent error types in system.
 */
public enum ErrorType {

	/** invalid credentials wrong password. */
	INVALID_CREDENTIALS_WRONG_PASSWORD("INVALID_CREDENTIALS_WRONG_PASSWORD"),
	
	/** invalid credentials user not found. */
	INVALID_CREDENTIALS_USER_NOT_FOUND("INVALID_CREDENTIALS_USER_NOT_FOUND"),
	
	/** invalid credentials user already logged in. */
	INVALID_CREDENTIALS_USER_ALREADY_LOGGED_IN("INVALID_CREDENTIALS_USER_ALREADY_LOGGED_IN"),
	
	/** invalid w4 value not found. */
	INVALID_W4_VALUE_NOT_FOUND("INVALID_W4_VALUE_NOT_FOUND"),
	
	/** invalid credentials restaurant not found. */
	INVALID_CREDENTIALS_RESTAURANT_NOT_FOUND("INVALID_CREDENTIALS_RESTAURANT_NOT_FOUND"),
	
	/** client already exist. */
	CLIENT_ALREADY_EXIST("CLIENT_ALREADY_EXIST"),
	
	/** business doesnt exist. */
	BUSINESS_DOESNT_EXIST("BUSINESS_DOESNT_EXIST"),
	
	/** business not approve. */
	BUSINESS_NOT_APPROVE("BUSINESS_NOT_APPROVE"),
	
	/** worker dosent exist. */
	WORKER_DOSENT_EXIST("WORKER_DOSENT_EXIST"),
	
	/** no clients in this branch. */
	NO_CLIENTS_IN_THIS_BRANCH("NO_CLIENTS_IN_THIS_BRANCH"),
	
	/**worker dosent restaurant owner. */
	WORKER_DOSENT_RESTAURANT_OWNER("WORKER_DOSENT_RESTAURANT_OWNER"),
	
	/** dishes not found. */
	DISHES_NOT_FOUND("DISHES_NOT_FOUND"),
	
	/** business client data not found. */
	BUSINESS_CLIENT_DATA_NOT_FOUND("BUSINESS_CLIENT_DATA_NOT_FOUND"),
	
	/** supplier already exist. */
	SUPPLIER_ALREADY_EXIST("SUPPLIER_ALREADY_EXIST"),
	
	/** report not exist. */
	REPORT_NOT_EXIST("REPORT_NOT_EXIST"),
	
	/** error creating report. */
	ERROR_CREATING_REPORT("ERROR_CREATING_REPORT"),
	
	/** file already open. */
	FILE_ALREADY_OPEN("FILE_ALREADY_OPEN"),
	
	/** could not update business client. */
	COULD_NOT_UPDATE_BUSINESS_CLIENT("COULD_NOT_UPDATE_BUSINESS_CLIENT"),
	
	/** could not update order. */
	COULD_NOT_UPDATE_ORDER("COULD_NOT_UPDATE_ORDER"),
	
	/** could not cancel order. */
	COULD_NOT_CANCEL_ORDER("COULD_NOT_CANCEL_ORDER"),
	
	/** user account is frozen. */
	USER_ACCOUNT_IS_FROZEN("USER_ACCOUNT_IS_FROZEN"),
	
	/** user is unregistered. */
	USER_IS_UNREGISTERED("USER_IS_UNREGISTERED"),
	
	/** unknown. */
	UNKNOWN("UNKNOWN_ERROR_TYPE");																
	
	/** The error type. used internally, should not be sent as a type */
	private String errorType;
	
	/**
	 * Instantiates a new error type.
	 *
	 * @param errorType the error type
	 */
	private ErrorType(String errorType) {
		this.errorType = errorType;
	}
	
	/**
	 * To string.
	 *
	 * @return the errorType string
	 */
	@Override
	public String toString() {
		return errorType;
	}
	
	/**
	 * From string.
	 *
	 * @param errorTypeString the error type string
	 * @return the error type
	 */
	public static ErrorType fromString(String errorTypeString) {
		switch(errorTypeString) {
		case "INVALID_CREDENTIALS_WRONG_PASSWORD":
			return ErrorType.INVALID_CREDENTIALS_WRONG_PASSWORD;
		case "INVALID_CREDENTIALS_USER_NOT_FOUND":
			return ErrorType.INVALID_CREDENTIALS_USER_NOT_FOUND;
		case "INVALID_CREDENTIALS_USER_ALREADY_LOGGED_IN":
			return ErrorType.INVALID_CREDENTIALS_USER_ALREADY_LOGGED_IN;
		case "INVALID_W4_VALUE_NOT_FOUND":
			return ErrorType.INVALID_W4_VALUE_NOT_FOUND;
		case "CLIENT_ALREADY_EXIST":
			return ErrorType.CLIENT_ALREADY_EXIST;
		case "BUSINESS_NOT_APPROVE":
			return BUSINESS_NOT_APPROVE;
		case "BUSINESS_DOESNT_EXIST":
			return BUSINESS_DOESNT_EXIST;
		case "NO_CLIENTS_IN_THIS_BRANCH":
			return ErrorType.NO_CLIENTS_IN_THIS_BRANCH;
		case "WORKER_DOSENT_EXIST":
			return WORKER_DOSENT_EXIST;
		case "WORKER_DOSENT_RESTAURANT_OWNER":
			return WORKER_DOSENT_RESTAURANT_OWNER;
		case "INVALID_CREDENTIALS_RESTAURANT_NOT_FOUND":
			return ErrorType.INVALID_CREDENTIALS_RESTAURANT_NOT_FOUND;
		case "DISHES_NOT_FOUND":
			return ErrorType.DISHES_NOT_FOUND;
		case "BUSINESS_CLIENT_DATA_NOT_FOUND":
			return ErrorType.BUSINESS_CLIENT_DATA_NOT_FOUND;
		case "SUPPLIER_ALREADY_EXIST":
			return ErrorType.SUPPLIER_ALREADY_EXIST;
		case "REPORT_NOT_EXIST":
			return ErrorType.REPORT_NOT_EXIST;
		case "ERROR_CREATING_REPORT":
			return ErrorType.ERROR_CREATING_REPORT;
		case "FILE_ALREADY_OPEN":
			return ErrorType.FILE_ALREADY_OPEN;
		case "COULD_NOT_UPDATE_BUSINESS_CLIENT":
			return ErrorType.COULD_NOT_UPDATE_BUSINESS_CLIENT;
		case "COULD_NOT_UPDATE_ORDER":
			return ErrorType.COULD_NOT_UPDATE_ORDER;
		case "COULD_NOT_CANCEL_ORDER":
			return ErrorType.COULD_NOT_CANCEL_ORDER;
		case "USER_ACCOUNT_IS_FROZEN":
			return ErrorType.USER_ACCOUNT_IS_FROZEN;
		case "USER_IS_UNREGISTERED":
			return ErrorType.USER_IS_UNREGISTERED;
		default:
			return ErrorType.UNKNOWN;
		}
	}
}
