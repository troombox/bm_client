package utility.entity;

public class Restaurant {
	
	private String dish_ID, resName, category, branch;

	public Restaurant(String dish_ID, String resName, String category, String branch) {
		this.dish_ID = dish_ID;
		this.resName = resName;
		this.category = category;
		this.branch = branch;
	}

	public String getDish_ID() {
		return dish_ID;
	}

	public String getResName() {
		return resName;
	}

	public String getCategory() {
		return category;
	}

	public String getBranch() {
		return branch;
	}
	
	

}
