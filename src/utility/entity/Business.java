package utility.entity;

/**
 * The Class Business.
 * represent the business  of business clients 
 */
public class Business {
	
	/** The business id. */
	private int businessId;
	
	/** The business name. */
	private String businessName;
	
	/** Is the business approved. */
	private int isApproved;
	
	/** The hr id. */
	private int hr_id;
	
	/** The branch. */
	private String branch;
	
	/**
	 * Instantiates a new business.
	 *
	 * @param businessId the business id
	 * @param businessName the business name
	 * @param isApproved is the business approved
	 * @param hr_id the hr id
	 * @param branch the branch of business
	 */
	public Business(int businessId, String businessName, int isApproved, int hr_id, String branch) {
		super();
		this.businessId = businessId;
		this.businessName = businessName;
		this.isApproved = isApproved;
		this.hr_id = hr_id;
		this.branch = branch;
	}
	
	/**
	 * Instantiates a new business.
	 *
	 * @param businessName the business name
	 * @param isApproved is the business approved
	 * @param branch the branch of business
	 */
	public Business(String businessName,int isApproved, String branch) {
		this(0,businessName,isApproved,0,branch);
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
	 * Gets the business name.
	 *
	 * @return the business name
	 */
	public String getBusinessName() {
		return businessName;
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
	 * Gets the hr id.
	 *
	 * @return the hr id
	 */
	public int getHr_id() {
		return hr_id;
	}
	
	/**
	 * Gets the branch.
	 *
	 * @return the branch of business
	 */
	public String getBranch() {
		return branch;
	}

	

}
