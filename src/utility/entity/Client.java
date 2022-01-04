package utility.entity;

import utility.enums.UserType;

/**
 * The Class Client.
 * represents an abstract client in system. can be business or personal
 */
public class Client extends User{
	
	/** The balance in app. */
	private int balanceInApp;
	
	/** The budget. */
	private int budget;
	
	/** The is approved. */
	private int isApproved;
	
	/** The business id. */
	private int businessId;
	
	/** The employer code. */
	private int employerCode;
	
	/** The personal credit number. */
	private int personalCreditNumber;
	
	/** The email. */
	private String email;
	
	/** The user type. */
	private UserType userType;
	
	
	/**
	 * Instantiates a new client.
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
	 * @param businessCode the business code
	 * @param balanceInApp the balance in app
	 * @param budget the budget
	 * @param isApproved the is approved
	 * @param businessId the business id
	 * @param personalCreditNumber the personal credit number
	 */
	public Client(int user_ID, String firstName, String lastName, String personalBranch, String email, String phone,
			UserType userType, String status, String w4c, boolean isSignedIn, String password, int personalCode, int businessCode,
			int balanceInApp, int budget, int isApproved,int businessId, int personalCreditNumber) {
		super(user_ID, firstName, lastName, personalBranch, email, phone, userType, status, w4c, isSignedIn, password,
				personalCode, businessCode);
		this.balanceInApp = balanceInApp;
		this.budget = budget;
		this.isApproved = isApproved;
		this.businessId = businessId;
		this.employerCode = businessId;
		this.personalCreditNumber = personalCreditNumber;
		this.email=email;
		this.userType=userType;
	}
	
	/**
	 * Instantiates a new client.
	 *
	 * @param ID the id
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param personalBranch the personal branch
	 * @param email the email
	 * @param phone the phone
	 * @param personalCreditNumber the personal credit number
	 * @param employerCode the employer code
	 * @param mothlyBalance the monthly balance
	 * @param type the type
	 */
	public Client(int ID,String firstName, String lastName,String personalBranch,String email, String phone,int personalCreditNumber, 
				  int employerCode, int mothlyBalance, UserType type) {
		this(ID,firstName,lastName, personalBranch, email, phone,type,"","",false,"",0,0,0,mothlyBalance,0,employerCode,personalCreditNumber);
	}


	/**
	 * Gets the budget.
	 *
	 * @return the budget
	 */
	public int getBudget() {
		return budget;
	}


	/**
	 * Gets the checks if is approved.
	 *
	 * @return the checks if is approved
	 */
	public int getIsApproved() {
		return isApproved;
	}


	/**
	 * Gets the business id.
	 *
	 * @return the business id
	 */
	public int getBusinessId() {
		return businessId;
	}


	/**
	 * Gets the personal credit number.
	 *
	 * @return the personal credit number
	 */
	public int getPersonalCreditNumber() {
		return personalCreditNumber;
	}
	
	public int getEmployerCode() {
		return employerCode;
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
	 * Gets the balance in the app for user entity.
	 *
	 * @return the user type
	 */
	public int getBalanceInApp() {
		return balanceInApp;
	}

	/**
	 * Sets the balance in the app for user entity.
	 *
	 * @return the user type
	 */
	public void setBalanceInApp(int balanceInApp) {
		this.balanceInApp = balanceInApp;
	}

}
