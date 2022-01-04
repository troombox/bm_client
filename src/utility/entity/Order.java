
package utility.entity;

import java.util.ArrayList;

import utility.enums.OrderType;

/**
 * The Class Order.
 * represent the entity of order of client from restaurant
 */
public class Order {

	/** The ordering user id in system. */
	private int orderingUserID;
	
	/** The order ID. */
	private int orderID;
	
	/** The user first name. */
	private String userFirstName;
	
	/** The user last name. */
	private String userLastName;
	
	/** The full name. */
	private String fullName;
	
	/** The user phone. */
	private String userPhone;
	
	/** The status. */
	private String status;
	
	/** The user email. */
	private String userEmail;
	
	/** The delivery address. */
	private String deliveryAddress;
	
	/** The restaurant ID. */
	private int restaurantID;
	
	/** The type of order. */
	private String typeOfOrder;
	
	/** The total price. */
	private int totalPrice; 	//dish price + discount
	
	/** The time of order. */
	private String timeOfOrder;
	
	/** The time of arrival. */
	private String timeOfArrival;

	/** The dishes in order. */
	private ArrayList<Dish> dishesInOrder;
	
	/** The is private order. */
	private boolean isPrivateOrder = true;
	
	/** The order price. price actually paid without delivery*/
	private int orderPrice; 	
	
	/** The delivery price. price for order delivery paid */
	private int deliveryPrice; 	
	
	/**
	 * Instantiates a new order.
	 *
	 * @param user the user
	 * @param restaurantID the restaurant ID
	 */
	public Order(User user,  int restaurantID) {
		this(user.getFirstName(), user.getLastName(), user.getPhone(), "", restaurantID, "");
	}
	
	/**
	 * Instantiates a new order.
	 *
	 * @param user the user
	 * @param deliveryAddress the delivery address
	 * @param restaurantID the restaurant ID
	 * @param typeOfOrder the type of order
	 */
	public Order(User user, String deliveryAddress,  int restaurantID, String typeOfOrder) {
		this(user.getFirstName(), user.getLastName(), user.getPhone(), deliveryAddress, restaurantID, typeOfOrder);
	}
	
	/**
	 * Instantiates a new order.
	 *
	 * @param userFirstName the user first name
	 * @param userLastName the user last name
	 * @param userPhone the user phone
	 * @param deliveryAddress the delivery address
	 * @param restaurantID the restaurant ID
	 * @param typeOfOrder the type of order
	 */
	public Order( String userFirstName, String userLastName, String userPhone, String deliveryAddress, int restaurantID, String typeOfOrder) {
		dishesInOrder = new ArrayList<Dish>();
		this.deliveryAddress = deliveryAddress;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPhone = userPhone;
		this.restaurantID = restaurantID;
		this.typeOfOrder = typeOfOrder;
	}
	

	
	/**
	 * Instantiates a new order.
	 *
	 * @param orderID the order ID
	 * @param typeOfOrder the type of order
	 * @param deliveryAddress the delivery address
	 * @param status the status
	 * @param totalPrice the total price
	 * @param timeOfOrder the time of order
	 * @param fullName the full name
	 * @param userPhone the user phone
	 */
	public Order( int orderID, String typeOfOrder, String deliveryAddress, String status, int totalPrice,
			String timeOfOrder, String fullName, String userPhone) {
		
		dishesInOrder = new ArrayList<Dish>();
		this.orderID = orderID;
		this.typeOfOrder = typeOfOrder;
		this.deliveryAddress = deliveryAddress;
		this.status = status;
		this.totalPrice = totalPrice;
		this.timeOfOrder = timeOfOrder;		
		this.fullName = fullName;
		this.userPhone = userPhone;
		
	}
	
	/**
	 * Instantiates a new order.
	 *
	 * @param userFirstName the user first name
	 * @param userLastName the user last name
	 * @param userPhone the user phone
	 * @param deliveryAddress the delivery address
	 * @param restaurantID the restaurant ID
	 * @param typeOfOrder the type of order
	 */
	//Added by Semion
	public Order(String userFirstName, String userLastName, String userPhone, String deliveryAddress, int restaurantID, OrderType typeOfOrder) {
		this(userFirstName, userLastName, userPhone, deliveryAddress, restaurantID, typeOfOrder.toString());
	}

	/**
	 * Instantiates a new order.
	 *
	 * @param userFirstName the user first name
	 * @param userLastName the user last name
	 * @param userPhone the user phone
	 * @param deliveryAddress the delivery address
	 * @param restaurantID the restaurant ID
	 * @param typeOfOrder the type of order
	 * @param orderPrice the order price
	 * @param deliveryPrice the delivery price
	 */
	public Order( String userFirstName, String userLastName, String userPhone, String deliveryAddress, int restaurantID, OrderType typeOfOrder, int orderPrice, int deliveryPrice) {
		this(userFirstName, userLastName, userPhone, deliveryAddress, restaurantID, typeOfOrder.toString());
		this.deliveryPrice = deliveryPrice;
		this.orderPrice = orderPrice;
		
	}
	//End

	/**
	 * Gets the ordering user ID.
	 *
	 * @return the ordering user ID
	 */
	public int getOrderingUserID() {
		return orderingUserID;
	}

	/**
	 * Sets the ordering user ID.
	 *
	 * @param orderingUserID the new ordering user ID
	 */
	public void setOrderingUserID(int orderingUserID) {
		this.orderingUserID = orderingUserID;
	}

	/**
	 * Gets the order ID.
	 *
	 * @return the order ID
	 */
	public int getOrderID() {
		return orderID;
	}

	/**
	 * Sets the order ID.
	 *
	 * @param orderID the new order ID
	 */
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	/**
	 * Gets the user first name.
	 *
	 * @return the user first name
	 */
	public String getUserFirstName() {
		return userFirstName;
	}

	/**
	 * Sets the user first name.
	 *
	 * @param userFirstName the new user first name
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	/**
	 * Gets the user last name.
	 *
	 * @return the user last name
	 */
	public String getUserLastName() {
		return userLastName;
	}

	/**
	 * Sets the user last name.
	 *
	 * @param userLastName the new user last name
	 */
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	/**
	 * Gets the full name.
	 *
	 * @return the full name
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the full name.
	 *
	 * @param fullName the new full name
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Gets the user phone.
	 *
	 * @return the user phone
	 */
	public String getUserPhone() {
		return userPhone;
	}

	/**
	 * Sets the user phone.
	 *
	 * @param userPhone the new user phone
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
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
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the delivery address.
	 *
	 * @return the delivery address
	 */
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	/**
	 * Sets the delivery address.
	 *
	 * @param deliveryAddress the new delivery address
	 */
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	/**
	 * Gets the restaurant ID.
	 *
	 * @return the restaurant ID
	 */
	public int getRestaurantID() {
		return restaurantID;
	}

	/**
	 * Sets the restaurant ID.
	 *
	 * @param restaurantID the new restaurant ID
	 */
	public void setRestaurantID(int restaurantID) {
		this.restaurantID = restaurantID;
	}

	/**
	 * Gets the type of order.
	 *
	 * @return the type of order
	 */
	public String getTypeOfOrder() {
		return typeOfOrder;
	}

	/**
	 * Sets the type of order.
	 *
	 * @param typeOfOrder the new type of order
	 */
	public void setTypeOfOrder(String typeOfOrder) {
		this.typeOfOrder = typeOfOrder;
	}

	/**
	 * Gets the total price.
	 *
	 * @return the total price
	 */
	public int getTotalPrice() {
		return totalPrice;
	}

	/**
	 * Sets the total price.
	 *
	 * @param totalPrice the new total price
	 */
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * Gets the time of order.
	 *
	 * @return the time of order
	 */
	public String getTimeOfOrder() {
		return timeOfOrder;
	}

	/**
	 * Sets the time of order.
	 *
	 * @param timeOfOrder the new time of order
	 */
	public void setTimeOfOrder(String timeOfOrder) {
		this.timeOfOrder = timeOfOrder;
	}

	/**
	 * Gets the time of arrival.
	 *
	 * @return the time of arrival
	 */
	public String getTimeOfArrival() {
		return timeOfArrival;
	}

	/**
	 * Sets the time of arrival.
	 *
	 * @param timeOfArrival the new time of arrival
	 */
	public void setTimeOfArrival(String timeOfArrival) {
		this.timeOfArrival = timeOfArrival;
	}

	/**
	 * Gets the dishes in order.
	 *
	 * @return the dishes in order
	 */
	public ArrayList<Dish> getDishesInOrder() {
		return dishesInOrder;
	}

	/**
	 * Sets the dishes in order.
	 *
	 * @param dishesInOrder the new dishes in order
	 */
	public void setDishesInOrder(ArrayList<Dish> dishesInOrder) {
		this.dishesInOrder = dishesInOrder;
	}
	

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Order [orderingUserID=" + orderingUserID + ", orderID=" + orderID + ", userFirstName=" + userFirstName
				+ ", userLastName=" + userLastName + ", fullName=" + fullName + ", userPhone=" + userPhone + ", status="
				+ status + ", userEmail=" + userEmail + ", deliveryAddress=" + deliveryAddress + ", restaurantID="
				+ restaurantID + ", typeOfOrder=" + typeOfOrder + ", totalPrice=" + totalPrice + ", timeOfOrder="
				+ timeOfOrder + ", timeOfArrival=" + timeOfArrival
				+ ", isPrivateOrder=" + isPrivateOrder + ", orderPrice=" + orderPrice + ", deliveryPrice="
				+ deliveryPrice + ", dishesInOrder=" + dishesInOrder.toString()+ "]";
	}


	/**
	 * Gets the user email.
	 *
	 * @return the user email
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * Sets the user email.
	 *
	 * @param userEmail the new user email
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
  
  /**
   * Checks if is private order.
   *
   * @return true, if is private order
   */
  public boolean isPrivateOrder() {
		return isPrivateOrder;
	}

	/**
	 * Sets the private order.
	 *
	 * @param isPrivateOrder the new private order
	 */
	public void setPrivateOrder(boolean isPrivateOrder) {
		this.isPrivateOrder = isPrivateOrder;
	}
	
	/**
	 * Adds the dish.
	 *
	 * @param dish the dish
	 */

	public void addDish(Dish dish) {
		dishesInOrder.add(dish);
	}
	
	/**
	 * Removes the dish.
	 *
	 * @param dish the dish
	 */
	public void removeDish(Dish dish) {
		dishesInOrder.remove(dish);
	}
	
	/**
	 * Gets the dish by dish ID.
	 *
	 * @param dishID the dish ID
	 * @return the dish by dish ID
	 */
	public Dish getDishByDishID(String dishID) {
		for (Dish d : dishesInOrder) {
			if(d.getDish_ID().equals(dishID))
				return d;
		}
		return null;
	}
	
	/**
	 * Check if dish in order by dish.
	 *
	 * @param dish the dish
	 * @return true, if successful
	 */
	public boolean checkIfDishInOrderByDish(Dish dish) {
		return checkIfDishInOrderByDishID(dish.getDish_ID());
	}
	
	/**
	 * Check if dish in order by dish ID.
	 *
	 * @param dishID the dish ID
	 * @return true, if successful
	 */
	public boolean checkIfDishInOrderByDishID(String dishID) {
		for (Dish d : dishesInOrder) {
			if(d.getDish_ID().equals(dishID))
				return true;
		}
		return false;
	}
	
	/**
	 * Gets the amount of dishes.
	 *
	 * @return the amount of dishes
	 */
	public int getAmountOfDishes() {
		return dishesInOrder.size();
	}

	/**
	 * Gets the order price.
	 *
	 * @return the order price
	 */
	public int getOrderPrice() {
		return orderPrice;
	}

	/**
	 * Gets the delivery price.
	 *
	 * @return the delivery price
	 */
	public int getDeliveryPrice() {
		return deliveryPrice;
	}

	/**
	 * Sets the order price.
	 *
	 * @param orderPrice the new order price
	 */
	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	/**
	 * Sets the delivery price.
	 *
	 * @param deliveryPrice the new delivery price
	 */
	public void setDeliveryPrice(int deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

}

