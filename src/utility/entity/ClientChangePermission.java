package utility.entity;

/**
 * The Class ClientChangePermission. 
 * represent the entity of client if branch manager wants to change permission
 */
public class ClientChangePermission {
	
	/** The first name. */
	private final String firstName;
	
	/** The last name. */
	private final String lastName;
	
	/** The status. */
	private  String status;
	
	/** The branch. */
	private final String branch;
	
	/** The id in system. */
	private final String id;
	

	/**
	 * Instantiates a new client change permission.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param branch the branch
	 * @param status the status
	 * @param id the id in system
	 */
	public ClientChangePermission(String firstName,String lastName, String branch, String status,String id) {
		this.branch=branch;
		this.firstName = firstName;
		this.status = status;
		this.lastName=lastName;
		this.id=id;
	}
	
	/**
	 * Instantiates a new client change permission.
	 *
	 * @param branch the branch
	 * @param status the status
	 */
	public ClientChangePermission(String branch, String status) {
		this("","",branch,"","");
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id in system
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the branch.
	 *
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
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
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param newS the new status
	 */
	public void setStatus(String newS) {
		status = newS;
	}


}
