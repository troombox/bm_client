package utility.entity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Class Dish.
 * represent the entity of dish in restaurant
 */
public class Dish {

	/** The cooking level. */
	private String dish_ID, description,size,cooking_level;
	
	/** The exceptions. */
	private String res_ID, price, name, type, exceptions;
	
	
	/**
	 * Instantiates a new dish.
	 *
	 * @param dish_ID the dish ID
	 * @param res_ID the res ID
	 * @param type the type
	 * @param name the name
	 * @param description the description
	 * @param size the size
	 * @param cooking_level the cooking level
	 * @param price the price
	 */
	public Dish(String dish_ID,String res_ID, String type, String name, String description, String size,
			String cooking_level, String price) {
			

		this.dish_ID = dish_ID;
		this.description = description;
		this.size = size;
		this.cooking_level = cooking_level;
		this.res_ID = res_ID;
		this.price = price;
		this.name = name;
		this.type = type;
	}
	
	/**
	 * Gets the dish ID.
	 *
	 * @return the dish ID
	 */
	public String getDish_ID() {
		return dish_ID;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public String getSize() {
		return size;
	}
	
	/**
	 * Gets the cooking level.
	 *
	 * @return the cooking level
	 */
	public String getCooking_level() {
		return cooking_level;
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
	 * Gets the price.
	 *
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * Sets the cooking level of dish.
	 *
	 * @param cooking_level the new cooking level
	 */
	public void setCooking_level(String cooking_level) {
		this.cooking_level = cooking_level;
	}
	
	/**
	 * Gets the price of dish by size.
	 *
	 * @param size the size
	 * @return the price by size
	 */
	public String getPriceBySize(String size){
		//fix for a null, no idea where the bug comes from
		if(this.size == null)
			this.size = "";
		if(this.price == null) {
			this.price = "";
		}
		String[] sizes = this.size.split(",");
		ArrayList<String> sizesArray = new ArrayList<String>(Arrays.asList(sizes));
		String[] prices = this.price.split(",");
		ArrayList<String> pricesArray = new ArrayList<String>(Arrays.asList(prices));
		int index = sizesArray.indexOf(size);
		return pricesArray.get(index);
	}

	/**
	 * Sets the price of dish.
	 *
	 * @param price the new price
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	
	/**
	 * Copy of dish.
	 *
	 * @param dish the dish
	 * @return the dish
	 */
	public static Dish copyOfDish(Dish dish) {
		return new Dish(dish.dish_ID, dish.res_ID, dish.type, dish.name, dish.description, dish.size, dish.cooking_level, dish.price);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Dish [dish_ID=" + dish_ID + ", description=" + description + ", size=" + size + ", cooking_level="
				+ cooking_level + ", res_ID=" + res_ID + ", price=" + price + ", name=" + name + ", type=" + type + "]";
	}

	/**
	 * Gets the exceptions.
	 *
	 * @return the exceptions
	 */
	public String getExceptions() {
		return exceptions;
	}
	
	/**
	 * Sets the exceptions.
	 *
	 * @param exceptions the new exceptions
	 */
	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
	}
	
}
