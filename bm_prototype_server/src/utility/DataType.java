package utility;

public enum DataType {
	
	SINGLE_TEXT_STRING("SINGLE_TEXT_STRING"),	//used for messages that contain a single string of text: Connection/Debug
	ORDER("ORDER"),								//used for Order Messages
	USER("USER"),
	ERROR_MESSAGE("ERROR_MESSAGE"),				//used for Error Messages
	UNKNOWN("UNKNOWN");							//used internally, should not be sent as a type
	
	private String dataType;
	
	private DataType(String dataType) {
		this.dataType = dataType;
	}
	
	@Override
	public String toString() {
		return dataType;
	}
}
