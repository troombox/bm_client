package utility.entity;

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
		return workerEmail;
	}

	@Override
	public String toString() {
		return "BusinessClient [workerID=" + workerID + ", workerName=" + workerName + ", workerPhone=" + workerPhone
				+ ", workerEmail=" + workerEmail + "]";
	}


}
