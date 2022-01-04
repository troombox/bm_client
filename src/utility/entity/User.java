package utility.entity;

import utility.enums.UserType;

/**
 * The Class User.
 */
public class User {

	/** The user id in system. */
	private int user_ID;
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The personal branch. */
	private String personalBranch;
	
	/** The email. */
	private String email;
	
	/** The phone. */
	private String phone;
	
	/** The status. */
	private String status;
	
	/** The w 4 c. */
	private String w4c;
	
	/** The password. */
	private String password;
	
	/** The is signed in. */
	private boolean isSignedIn;	
	
	/** The user type. */
	private UserType userType;
	
	/** The personal code. */
	private int personalCode;
	
	/** The buisness code. */
	private int buisnessCode;
	
	
	/**
	 * Instantiates a new user.
	 *
	 * @param user_ID the user ID
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param personalBranch the personal branch
	 * @param email the email
	 * @param phone the phone
	 * @param userType the user type
	 * @param status the status
	 * @param w4c the w 4 c
	 * @param isSignedIn the is signed in
	 * @param password the password
	 * @param personalCode the personal code
	 * @param buisnessCode the buisness code
	 */
	public User(int user_ID, String firstName, String lastName, String personalBranch, String email, String phone, UserType userType,
			String status, String w4c, boolean isSignedIn, String password, int personalCode, int buisnessCode) {
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
		this.personalCode = personalCode;
		this.buisnessCode = buisnessCode;
	}
	
	/**
	 * Instantiates a new user.
	 *
	 * @param user_ID the user ID
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param personalBranch the personal branch
	 * @param email the email
	 * @param phone the phone
	 * @param userType the user type
	 * @param status the status
	 * @param w4c the w 4 c
	 * @param isSignedIn the is signed in
	 * @param password the password
	 */
	public User(int user_ID, String firstName, String lastName, String personalBranch, String email, String phone, UserType userType,
			String status, String w4c, boolean isSignedIn, String password) {

		this(user_ID, firstName, lastName, personalBranch, email, phone, userType, status, w4c, isSignedIn, password, -1, -1);
	}
	
	/**
	 * Instantiates a new user.
	 *
	 * @param email the email
	 * @param password the password
	 */
	public User(String email, String password){
		this(-1, "", "", "", email, "", UserType.USER, "", "", false, password, -1, -1);

	}
	
	
	/**
	 * Gets the user id in system.
	 *
	 * @return the user id in system
	 */
	public int getUser_ID() {
		return user_ID;
	}


	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * Gets the personal branch.
	 *
	 * @return the personal branch
	 */
	public String getPersonalBranch() {
		return personalBranch;
	}


	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}


	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * Gets the w 4 c.
	 *
	 * @return the w 4 c
	 */
	public String getW4c() {
		return w4c;
	}


	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * Checks if is signed in.
	 *
	 * @return true, if is signed in
	 */
	public boolean isSignedIn() {
		return isSignedIn;
	}

	/**
	 * Gets the user type.
	 *
	 * @return the user type
	 */
	public UserType getUserType() {
		return userType;
	}
	

	/**
	 * Gets the personal code.
	 *
	 * @return the personal code
	 */
	public int getPersonalCode() {
		return personalCode;
	}



	/**
	 * Gets the buisness code.
	 *
	 * @return the buisness code
	 */
	public int getBuisnessCode() {
		return buisnessCode;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "User [user_ID=" + user_ID + ", firstName=" + firstName + ", lastName=" + lastName + ", personalBranch="
				+ personalBranch + ", email=" + email + ", phone=" + phone + ", status=" + status + ", w4c=" + w4c
				+ ", password=" + password + ", isSignedIn=" + isSignedIn + ", userType=" + userType + ", personalCode="
				+ personalCode + ", buisnessCode=" + buisnessCode + "]";
	}


	/**
	 * Sets the personal code.
	 *
	 * @param personalCode the new personal code
	 */
	public void setPersonalCode(int personalCode) {
		this.personalCode = personalCode;
	}

	/**
	 * Sets the business code.
	 *
	 * @param buisnessCode the new business code
	 */
	public void setBuisnessCode(int buisnessCode) {
		this.buisnessCode = buisnessCode;
	}


}
