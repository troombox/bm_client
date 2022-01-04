package utility.entity;

<<<<<<< HEAD

public class BusinessClient {
	private final String workerID;
	private final String workerName;
	private final String workerPhone;
	private final String workerEmail;
	
	private boolean isApproved = false;

	public BusinessClient(String userId, String firstName, String phoneNumber, String email) {
		this.workerID = userId;
		this.workerName = firstName;
		this.workerPhone = phoneNumber;
		this.workerEmail = email;
	}
	
	public void setIsApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	public boolean getIsApproved() {
		return isApproved;
	}

	public String getWorkerID() {
		return workerID;
	}

	public String getWorkerName() {
		return workerName;
	}

	public String getWorkerPhone() {
		return workerPhone;
	}

	public String getWorkerEmail() {
=======
import javafx.beans.property.SimpleStringProperty;

public class BusinessClient {
	private final SimpleStringProperty workerID;
	private final SimpleStringProperty workerName;
	private final SimpleStringProperty workerPhone;
	private final SimpleStringProperty workerEmail;

	public BusinessClient(String userId, String firstName, String phoneNumber, String email) {
		this.workerID = new SimpleStringProperty(userId);
		this.workerName = new SimpleStringProperty(firstName);
		this.workerPhone = new SimpleStringProperty(phoneNumber);
		this.workerEmail = new SimpleStringProperty(email);
	}

	public SimpleStringProperty getWorkerID() {
		return workerID;
	}

	public SimpleStringProperty getWorkerName() {
		return workerName;
	}

	public SimpleStringProperty getWorkerPhone() {
		return workerPhone;
	}

	public SimpleStringProperty getWorkerEmail() {
>>>>>>> main
		return workerEmail;
	}

	@Override
	public String toString() {
		return "BusinessClient [workerID=" + workerID + ", workerName=" + workerName + ", workerPhone=" + workerPhone
				+ ", workerEmail=" + workerEmail + "]";
	}


}
