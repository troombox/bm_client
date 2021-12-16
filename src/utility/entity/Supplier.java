package utility.entity;

public class Supplier {
	
	private String restaurantName;
	private String imagePath;
	private String categories;
	private String personalBranch;
	public Supplier(String restaurantName, String imagePath, String categories,String personalBranch) {
		this.restaurantName = restaurantName;
		this.imagePath = imagePath;
		this.categories = categories;
		this.personalBranch=personalBranch;
	}
	
	public Supplier(String restaurantName,String personalBranch) {
		this(restaurantName,"","",personalBranch);
	}
	
	public String getRestaurantName() {
		return restaurantName;
	}
	public String getPersonalBranch() {
		return personalBranch;
	}
	public String getImagePath() {
		return imagePath;
	}
	public String getCategories() {
		return this.categories;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public void setPersonalBranch(String branch) {
		personalBranch = branch;
	}
	

}
