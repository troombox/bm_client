package utility.entity;

/**
 * The Class Restaurant.
 * represent the entity of restaurant in system
 */
public class Restaurant {
	

	/** The branch,name,category and res_ID of restaurant */
	private String res_ID, resName, category, branch;

	/**
	 * Instantiates a new restaurant.
	 *
	 * @param res_ID the res ID
	 * @param resName the res name
	 * @param category the category
	 * @param branch the branch
	 */
	public Restaurant(String res_ID, String resName, String category, String branch) {
		this.res_ID = res_ID;
		this.resName = resName;
		this.category = category;
		this.branch = branch;
	}


	/**
	 * Gets the res ID.
	 *
	 * @return the res ID
	 */
	public String getRes_ID() {
		return res_ID;
	}

	/**
	 * Gets the res name.
	 *
	 * @return the res name
	 */
	public String getResName() {
		return resName;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Gets the branch.
	 *
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}
	
	

}
