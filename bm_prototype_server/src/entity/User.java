package entity;

import utility.UserType;

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
	
	
	public User(int user_ID, String firstName, String lastName, String personalBranch, String email, String phone,
			String status, String w4c, String password, boolean isSignedIn, UserType userType) {
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
	
	
	User(int user_ID){
		this(user_ID, "","","","","","", "", "", false, UserType.USER);
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
	

}
