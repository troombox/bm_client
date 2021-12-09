package utility.enums;

public enum ErrorType {

	INVALID_CREDENTIALS_WRONG_PASSWORD("INVALID_CREDENTIALS_WRONG_PASSWORD"),
	INVALID_CREDENTIALS_USER_NOT_FOUND("INVALID_CREDENTIALS_USER_NOT_FOUND"),
	UNKNOWN("UNKNOWN");																//used internally, should not be sent as a type
	
	private String errorType;
	
	private ErrorType(String errorType) {
		this.errorType = errorType;
	}
	
	@Override
	public String toString() {
		return errorType;
	}
	
	public static ErrorType fromString(String errorTypeString) {
		switch(errorTypeString) {
		case "INVALID_CREDENTIALS_WRONG_PASSWORD":
			return ErrorType.INVALID_CREDENTIALS_WRONG_PASSWORD;
		case "INVALID_CREDENTIALS_USER_NOT_FOUND":
			return ErrorType.INVALID_CREDENTIALS_USER_NOT_FOUND;
		default:
			return ErrorType.UNKNOWN;
		}
	}
}
