package utility.entity;

/**
 * The Class BusinessClient represents data kept by system regarding a business client, 
 * that works in a business that got a contract with "BiteMe".
 */
public class BusinessClient {
	
	/** The business client ID. */
	private final String workerID;
	
	/** The business client name. */
	private final String workerName;
	
	/** The business client phone. */
	private final String workerPhone;
	
	/** The business client email. */
	private final String workerEmail;
	
	/** shows if approved in the system. */
	private boolean isApproved = false;

	/**
	 * Instantiates a new business client.
	 *
	 * @param userId the user id
	 * @param firstName the first name
	 * @param phoneNumber the phone number
	 * @param email the email
	 */
	public BusinessClient(String userId, String firstName, String phoneNumber, String email) {
		this.workerID = userId;
		this.workerName = firstName;
		this.workerPhone = phoneNumber;
		this.workerEmail = email;
	}
	
	/**
	 * Sets flag isApproved to show the client being approved
	 *
	 * @param isApproved - new value to set the flag to
	 */
	public void setIsApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	/**
	 * Gets the value of ifApproved flag.
	 *
	 * @return true if isApproved
	 */
	public boolean getIsApproved() {
		return isApproved;
	}

	/**
	 * Gets business client ID.
	 *
	 * @return the business client ID
	 */
	public String getWorkerID() {
		return workerID;
	}

	/**
	 * Gets business client name.
	 *
	 * @return the business client name
	 */
	public String getWorkerName() {
		return workerName;
	}

	/**
	 * Gets business client's phone.
	 *
	 * @return the business client phone
	 */
	public String getWorkerPhone() {
		return workerPhone;
	}

	/**
	 * Gets business client's email.
	 *
	 * @return the business client email
	 */
	public String getWorkerEmail() {
		return workerEmail;
	}

	/**
	 * To String method.
	 *
	 * @return the Business Client Data as a string
	 */
	@Override
	public String toString() {
		return "BusinessClient [workerID=" + workerID + ", workerName=" + workerName + ", workerPhone=" + workerPhone
				+ ", workerEmail=" + workerEmail + "]";
	}


}
