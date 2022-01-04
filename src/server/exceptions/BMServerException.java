package server.exceptions;

import utility.enums.ErrorType;

/**
 * The Class BMServerException - extends Exception class 
 * and gives us a bit more flexibility in handling exceptions
 * contains a variable holding ErrorType Enum to give detailed description of a problem.
 */
public class BMServerException extends Exception{
	
	/** The error type held in the exception */
	ErrorType errorType;
	
	/**
	 * Instantiates a new BM server exception.
	 *
	 * @param errorType the error type to be saved
	 * @param message the message to be saved
	 */
	public BMServerException(ErrorType errorType, String message) {
		super(message);
		this.errorType = errorType;
	}
	
	/**
	 * Instantiates a new BM server exception.
	 *
	 * @param message the message to be saved
	 */
	public BMServerException(String message) {
		super(message);
	}
	
	/**
	 * Gets the error type.
	 *
	 * @return the error type
	 */
	public ErrorType getErrorType() {
		return errorType;
	}
}
