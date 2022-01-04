package utility.entity;
/**
 * The Class Menu.
 * represent the entity of menu in restaurant
 */
public class Menu {
	
	/** The restaurant ID. */
	private String restaurant_ID;
	
	/** The dishes list. */
	private Dish[] dishesList;
	
	
	/**
	 * Instantiates a new menu.
	 *
	 * @param restaurant_ID the restaurant ID
	 * @param dishesList the dishes list
	 */
	public Menu(String restaurant_ID,Dish[] dishesList) {
		this.restaurant_ID = restaurant_ID;
		this.dishesList = dishesList;
	}


	/**
	 * Sets the dishes list.
	 *
	 * @param dishesList the new dishes list
	 */
	public void setDishesList(Dish[] dishesList) {
		this.dishesList = dishesList;
	}


	/**
	 * Gets the restaurant ID.
	 *
	 * @return the restaurant ID
	 */
	public String getRestaurant_ID() {
		return restaurant_ID;
	}


	/**
	 * Gets the dishes list.
	 *
	 * @return the dishes list
	 */
	public Dish[] getDishesList() {
		return dishesList;
	}

}
