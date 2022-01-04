
package utility.entity;

/**
 * The Class Supplier.
 * represent the entity of restaurant supplier in system
 */
public class Supplier {
	
	/** The restaurant name. */
	private String restaurantName;
	
	/** The image path. */
	private String imagePath;
	
	/** The categories. */
	private String categories;
	
	/** The personal branch. */
	private String personalBranch;
	
	/** The worker ID. */
	private String workerID;
	
	/**
	 * Instantiates a new supplier.
	 *
	 * @param restaurantName the restaurant name
	 * @param imagePath the image path
	 * @param categories the categories
	 * @param personalBranch the personal branch
	 * @param workerID the worker ID
	 */
	public Supplier(String restaurantName, String imagePath, String categories,String personalBranch,String workerID) {
		this.restaurantName = restaurantName;
		this.imagePath = imagePath;
		this.categories = categories;
		this.personalBranch=personalBranch;
		this.workerID = workerID;
	}
	
	/**
	 * Gets the worker ID.
	 *
	 * @return the worker ID
	 */
	public String getWorkerID() {
		return workerID;
	}

	/**
	 * Instantiates a new supplier.
	 *
	 * @param restaurantName the restaurant name
	 * @param categories the categories
	 * @param personalBranch the personal branch
	 * @param workerID the worker ID
	 */
	public Supplier(String restaurantName,String categories,String personalBranch,String workerID) {
		this(restaurantName,"",categories,personalBranch,workerID);
	}
	
	/**
	 * Gets the restaurant name.
	 *
	 * @return the restaurant name
	 */
	public String getRestaurantName() {
		return restaurantName;
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
	 * Gets the image path.
	 *
	 * @return the image path
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * Gets the categories of restaurant.
	 *
	 * @return the categories
	 */
	public String getCategories() {
		return this.categories;
	}
	
	/**
	 * Sets the restaurant name.
	 *
	 * @param restaurantName the new restaurant name
	 */
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	
	/**
	 * Sets the image path.
	 *
	 * @param imagePath the new image path
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	/**
	 * Sets the categories.
	 *
	 * @param categories the new categories
	 */
	public void setCategories(String categories) {
		this.categories = categories;
	}
	
	/**
	 * Sets the personal branch.
	 *
	 * @param branch the new personal branch
	 */
	public void setPersonalBranch(String branch) {
		personalBranch = branch;
	}
	

}
