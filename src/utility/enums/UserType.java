package utility.enums;

/**
 * The Enum UserType.
 *  represent all tpye of users in system.
 */
public enum UserType {
	
	/** The user. */
	USER("USER"),
	
	/** The client personal. */
	CLIENT_PERSONAL("CLIENT_PERSONAL"),
	
	/** The client business. */
	CLIENT_BUSINESS("CLIENT_BUSINESS"),
	
	/** The hr manager. */
	HR_MANAGER("HR_MANAGER"),
	
	/** The bm branch manager. */
	BM_BRANCH_MANAGER("BM_BRANCH_MANAGER"), 
	
	/** The bm ceo. */
	BM_CEO("BM_CEO"), 
	
	/** The restaurant owner. */
	RESTAURANT_OWNER("RESTAURANT_OWNER"), 
	
	/** The restaurant trusted worker. */
	RESTAURANT_TRUSTED_WORKER("RESTAURANT_TRUSTED_WORKER");

	/** The user type. */
	private String userType;

	/**
	 * Instantiates a new user type.
	 *
	 * @param userType the user type
	 */
	private UserType(String userType) {
		this.userType = userType;
	}

	/**
	 * To string.
	 *
	 * @return the userType string
	 */
	@Override
	public String toString() {
		return userType;
	}
	
	/**
	 * From string.
	 *
	 * @param userTypeString the user type string
	 * @return the user type 
	 */
	public static UserType fromString(String userTypeString) {
		switch(userTypeString) {
		case "CLIENT_PERSONAL":
			return UserType.CLIENT_PERSONAL;
		case "CLIENT_BUSINESS":
			return UserType.CLIENT_BUSINESS;
		case "HR_MANAGER":
			return UserType.HR_MANAGER;
		case "BM_BRANCH_MANAGER":
			return UserType.BM_BRANCH_MANAGER;
		case "BM_CEO":
			return UserType.BM_CEO;
		case "RESTAURANT_OWNER":
			return UserType.RESTAURANT_OWNER;
		default:
			return UserType.USER;
		}
	}
}
