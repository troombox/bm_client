package utility.entity;

import java.util.ArrayList;

public class Order {
	private int orderingUserID;
	private int orderID;
	
	private String userFirstName;
	private String userLastName;
	private String fullName;
	private String userPhone;
	private String status;
	
	private String deliveryAddress;
	
	private int restaurantID;
	private String typeOfOrder;
	
	private int totalPrice;
	private String timeOfOrder;
	private String timeOfArrival;

	private ArrayList<Dish> dishesInOrder;
	
	public Order(User user,  int restaurantID) {
		this(user.getFirstName(), user.getLastName(), user.getPhone(), "", restaurantID, "");
	}
	
	public Order(User user, String deliveryAddress,  int restaurantID, String typeOfOrder) {
		this(user.getFirstName(), user.getLastName(), user.getPhone(), deliveryAddress, restaurantID, typeOfOrder);
	}
	
	public Order( String userFirstName, String userLastName, String userPhone, String deliveryAddress, int restaurantID, String typeOfOrder) {
		dishesInOrder = new ArrayList<Dish>();
		this.deliveryAddress = deliveryAddress;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPhone = userPhone;
		this.restaurantID = restaurantID;
		this.typeOfOrder = typeOfOrder;
	}
	
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
	
	
	public void addDish(Dish dish) {
		dishesInOrder.add(dish);
	}
	
	public void removeDish(Dish dish) {
		dishesInOrder.remove(dish);
	}
	
	public Dish getDishByDishID(String dishID) {
		for (Dish d : dishesInOrder) {
			if(d.getDish_ID().equals(dishID))
				return d;
		}
		return null;
	}
	
	public boolean checkIfDishInOrderByDish(Dish dish) {
		return checkIfDishInOrderByDishID(dish.getDish_ID());
	}
	
	public boolean checkIfDishInOrderByDishID(String dishID) {
		for (Dish d : dishesInOrder) {
			if(d.getDish_ID().equals(dishID))
				return true;
		}
		return false;
	}

	public int getOrderingUserID() {
		return orderingUserID;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public int getRestaurantID() {
		return restaurantID;
	}

	public String getTypeOfOrder() {
		return typeOfOrder;
	}

	public ArrayList<Dish> getDishesInOrder() {
		return dishesInOrder;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public void setTypeOfOrder(String typeOfOrder) {
		this.typeOfOrder = typeOfOrder;
	}

	public int getAmountOfDishes() {
		return dishesInOrder.size();
	}

	public int getOrderID() {
		return orderID;
	}

	public String getFullName() {
		return fullName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getTimeOfOrder() {
		return timeOfOrder;
	}

	public String getTimeOfArrival() {
		return timeOfArrival;
	}

	public void setTimeOfArrival(String timeOfArrival) {
		this.timeOfArrival = timeOfArrival;
	}
}
