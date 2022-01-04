package client.utility.wrappers;

import java.util.ArrayList;

import utility.entity.Dish;
import utility.entity.Order;
import utility.entity.User;
import utility.enums.OrderType;

/**
 * The Class MultiOrder - used to keep data about a collection of dishes that 
 * not necessary belong to same restaurant ID 
 */
public class MultiOrder extends Order {
	
	/** The array keeping restaurant ID's of dishes saved in the order. */
	ArrayList<String> resIDs;

	/**
	 * Instantiates a new multi order.
	 *
	 * @param userFirstName - the user first name
	 * @param userLastName -  the user last name
	 * @param userPhone - the user phone
	 * @param deliveryAddress - the delivery address
	 * @param restaurantID - the restaurant ID of a first dish added
	 * @param typeOfOrder - the type of order
	 */
	public MultiOrder(String userFirstName, String userLastName, String userPhone, String deliveryAddress,
			int restaurantID, OrderType typeOfOrder) {
		super(userFirstName, userLastName, userPhone, deliveryAddress, restaurantID, typeOfOrder);
		this.resIDs = new ArrayList<String>();
	}
	
	/**
	 * Instantiates a new multi order.
	 *
	 * @param user the user
	 * @param restaurantID the restaurant ID
	 */
	public MultiOrder(User user,  int restaurantID) {
		this(user.getFirstName(), user.getLastName(), user.getPhone(), "", restaurantID, OrderType.UNKNOWN);
	}
	
	/**
	 * Adds the dish.
	 *
	 * @param dish - the dish to add
	 */
	@Override
	public void addDish(Dish dish) {
		super.addDish(dish);
		if(!resIDs.contains(dish.getRes_ID())) {
			resIDs.add(dish.getRes_ID());
		}
	}
	
	/**
	 * Removes the dish.
	 *
	 * @param dish - the dish to remove
	 */
	@Override
	public void removeDish(Dish dish) {
		super.removeDish(dish);
		String resID = dish.getRes_ID();
		for(Dish d : super.getDishesInOrder()) {
			if(d.getRes_ID().equals(resID)) {
				return;
			}
		}
		resIDs.remove(dish.getRes_ID());
	}
	
	/**
	 * Number of restaurants in order.
	 *
	 * @return the int value of number of restaurants in order.
	 */
	public int numberOfRestaurantsInOrder() {
		return resIDs.size();
	}
	
	/**
	 * ArrayList of restaurant ID's saved in the multiorder
	 *
	 * @return the array list
	 */
	public ArrayList<String> restaurantsInOrderList() {
		return resIDs;
	}
	
}
