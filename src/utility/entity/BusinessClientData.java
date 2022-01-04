package utility.entity;

/**
 * The Class BusinessClientData.
 */
public class BusinessClientData {
	
	/** The user ID. */
	private int userID;
	
	/** The personal code. */
	private int personalCode;
	
	/** The business code. */
	private int businessCode;
	
	/** The balance in app. */
	private int balanceInApp;
	
	/** The budget. */
	private int budget;
	
	/** flag isApproved. */
	private boolean approved;
	
	/** The business ID. */
	private int businessID;
	
	/** The personal credit num. */
	private int personalCreditNum;
	
	/**
	 * Instantiates a new business client data.
	 *
	 * @param businessCode the business code
	 * @param personalCode the personal code
	 * @param balanceInApp the balance in app
	 * @param userID the user ID
	 * @param budget the budget
	 * @param approved the approved
	 * @param businessID the business ID
	 * @param personalCreditNum the personal credit num
	 */
	public BusinessClientData( int businessCode, int personalCode, int balanceInApp, int userID, int budget,
			boolean approved, int businessID, int personalCreditNum) {
		this.userID = userID;
		this.personalCode = personalCode;
		this.businessCode = businessCode;
		this.balanceInApp = balanceInApp;
		this.budget = budget;
		this.approved = approved;
		this.businessID = businessID;
		this.personalCreditNum = personalCreditNum;
	}

	/**
	 * Instantiates a new business client data.
	 *
	 * @param businessCode the business code
	 */
	BusinessClientData(int businessCode){
		this(0, 0, businessCode,0, 0, false, 0, 0);
	}

	/**
	 * Gets the user ID.
	 *
	 * @return the user ID
	 */
	public int getUserID() {
		return userID;
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
	 * Gets the business code.
	 *
	 * @return the business code
	 */
	public int getBusinessCode() {
		return businessCode;
	}

	/**
	 * Gets the balance in app.
	 *
	 * @return the balance in app
	 */
	public int getBalanceInApp() {
		return balanceInApp;
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
	 * Checks if is approved.
	 *
	 * @return true, if is approved
	 */
	public boolean isApproved() {
		return approved;
	}

	/**
	 * Gets the business ID.
	 *
	 * @return the business ID
	 */
	public int getBusinessID() {
		return businessID;
	}

	/**
	 * Gets the personal credit num.
	 *
	 * @return the personal credit num
	 */
	public int getPersonalCreditNum() {
		return personalCreditNum;
	}

	/**
	 * Sets the user ID.
	 *
	 * @param userID the new user ID
	 */
	public void setUserID(int userID) {
		this.userID = userID;
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
	 * @param businessCode the new business code
	 */
	public void setBusinessCode(int businessCode) {
		this.businessCode = businessCode;
	}

	/**
	 * Sets the balance in app.
	 *
	 * @param balanceInApp the new balance in app
	 */
	public void setBalanceInApp(int balanceInApp) {
		this.balanceInApp = balanceInApp;
	}

	/**
	 * Sets the budget.
	 *
	 * @param budget the new budget
	 */
	public void setBudget(int budget) {
		this.budget = budget;
	}

	/**
	 * Sets the approved.
	 *
	 * @param approved the new approved
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	/**
	 * Sets the business ID.
	 *
	 * @param businessID the new business ID
	 */
	public void setBusinessID(int businessID) {
		this.businessID = businessID;
	}

	/**
	 * Sets the personal credit num.
	 *
	 * @param personalCreditNum the new personal credit num
	 */
	public void setPersonalCreditNum(int personalCreditNum) {
		this.personalCreditNum = personalCreditNum;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "BusinessClientData [userID=" + userID + ", personalCode=" + personalCode + ", businessCode="
				+ businessCode + ", balanceInApp=" + balanceInApp + ", budget=" + budget + ", approved=" + approved
				+ ", businessID=" + businessID + ", personalCreditNum=" + personalCreditNum + "]";
	}
}
