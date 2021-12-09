package utility.entity;

import utility.enums.UserType;

public class User {

	int user_ID;
	String firstName;
	String lastName;
	String personalBranch;
	String email;
	String phone;
	String status;
	String w4c;
	String password;
	boolean isSignedIn;	
	UserType userType;
	
	public User(int user_ID, String firstName, String lastName, String personalBranch, String email, String phone, UserType userType,
			String status, String w4c, boolean isSignedIn, String password) {
		this.user_ID = user_ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.personalBranch = personalBranch;
		this.email = email;
		this.phone = phone;
		this.status = status;
		this.w4c = w4c;
		this.password = password;
		this.isSignedIn = isSignedIn;
		this.userType = userType;
	}
	
	
	public User(String email, String password){
		this(0, "", "", "", email, "", UserType.USER, "", "", false, password);
	}
	
	public int getUser_ID() {
		return user_ID;
	}


	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public String getPersonalBranch() {
		return personalBranch;
	}


	public String getEmail() {
		return email;
	}


	public String getPhone() {
		return phone;
	}


	public String getStatus() {
		return status;
	}


	public String getW4c() {
		return w4c;
	}


	public String getPassword() {
		return password;
	}


	public boolean isSignedIn() {
		return isSignedIn;
	}

	public UserType getUserType() {
		return userType;
	}
	
	@Override
	public String toString() {
		return "User [user_ID=" + user_ID + ", firstName=" + firstName + ", lastName=" + lastName + ", personalBranch="
				+ personalBranch + ", email=" + email + ", phone=" + phone + ", status=" + status + ", w4c=" + w4c
				+ ", password=" + password + ", isSignedIn=" + isSignedIn + ", userType=" + userType.toString() + "]";
	}


}
