package utility.entity;

public class Dish {

	private String dish_ID, description,size,cooking_level;
	private String res_ID, price, name;
	
	
	public Dish(String dish_ID, String description, String size, String cooking_level, String res_ID, String price,String name) {

		this.dish_ID = dish_ID;
		this.description = description;
		this.size = size;
		this.cooking_level = cooking_level;
		this.res_ID = res_ID;
		this.price = price;
		this.name = name;
	}
	
	public String getDish_ID() {
		return dish_ID;
	}
	public String getDescription() {
		return description;
	}
	public String getSize() {
		return size;
	}
	public String getCooking_level() {
		return cooking_level;
	}
	public String getRes_ID() {
		return res_ID;
	}
	public String getPrice() {
		return price;
	}
	public String getName() {
		return name;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setCooking_level(String cooking_level) {
		this.cooking_level = cooking_level;
	}
}
