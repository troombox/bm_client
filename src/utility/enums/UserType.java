package utility.enums;

public enum UserType {
	
	USER("USER"),
	CLIENT_PERSONAL("CLIENT_PERSONAL"),
	CLIENT_BUSINESS("CLIENT_BUSINESS"),
	HR_MANAGER("HR_MANAGER"),
	BM_BRANCH_MANAGER("BM_BRANCH_MANAGER"), 
	BM_CEO("BM_CEO"), 
	RESTAURANT_OWNER("RESTAURANT_OWNER"), 
	RESTAURANT_TRUSTED_WORKER("RESTAURANT_TRUSTED_WORKER");

	private String userType;

	private UserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return userType;
	}
}
