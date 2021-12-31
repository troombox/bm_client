package utility.entity;

import java.util.ArrayList;

import utility.enums.OrderType;

public class Order {
	private int orderingUserID;
	
	private String userFirstName;
	private String userLastName;
	private String userPhone;
	
	private String deliveryAddress;
	
	private int restaurantID;
	private OrderType typeOfOrder;
	
	private int orderPrice;
	private int deliveryPrice;
	
	private String timeOfOrder = "";
	
	private boolean isPrivateOrder = true;

	private ArrayList<Dish> dishesInOrder;
	
	public Order(User user,  int restaurantID) {
		this(user.getFirstName(), user.getLastName(), user.getPhone(), "", restaurantID, OrderType.UNKNOWN, 0, 0);
	}
	
	public Order(User user, String deliveryAddress,  int restaurantID, OrderType typeOfOrder) {
		this(user.getFirstName(), user.getLastName(), user.getPhone(), deliveryAddress, restaurantID, typeOfOrder,0,0);
	}
	
	public Order( String userFirstName, String userLastName, String userPhone, String deliveryAddress, int restaurantID, OrderType typeOfOrder) {
		this(userFirstName, userLastName, userPhone, deliveryAddress, restaurantID, typeOfOrder, 0, 0);
	}
	
	public Order( String userFirstName, String userLastName, String userPhone, String deliveryAddress, int restaurantID, OrderType typeOfOrder, int orderPrice, int deliveryPrice) {
		dishesInOrder = new ArrayList<Dish>();
		this.deliveryAddress = deliveryAddress;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPhone = userPhone;
		this.restaurantID = restaurantID;
		this.typeOfOrder = typeOfOrder;
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

	public OrderType getTypeOfOrder() {
		return typeOfOrder;
	}

	public ArrayList<Dish> getDishesInOrder() {
		return dishesInOrder;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public void setTypeOfOrder(OrderType typeOfOrder) {
		this.typeOfOrder = typeOfOrder;
	}

	public int getAmountOfDishes() {
		return dishesInOrder.size();
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public int getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public void setDeliveryPrice(int deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	public boolean isPrivateOrder() {
		return isPrivateOrder;
	}

	public void setPrivateOrder(boolean isPrivateOrder) {
		this.isPrivateOrder = isPrivateOrder;
	}

	public String getTimeOfOrder() {
		return timeOfOrder;
	}

	public void setTimeOfOrder(String timeOfOrder) {
		this.timeOfOrder = timeOfOrder;
	}

	@Override
	public String toString() {
		return "Order [orderingUserID=" + orderingUserID + ", userFirstName=" + userFirstName + ", userLastName="
				+ userLastName + ", userPhone=" + userPhone + ", deliveryAddress=" + deliveryAddress + ", restaurantID="
				+ restaurantID + ", typeOfOrder=" + typeOfOrder + ", orderPrice=" + orderPrice + ", deliveryPrice="
				+ deliveryPrice + ", timeOfOrder=" + timeOfOrder + ", isPrivateOrder=" + isPrivateOrder
				+ ", dishesInOrder=" + dishesInOrder + "]";
	}

	public void setOrderingUserID(int orderingUserID) {
		this.orderingUserID = orderingUserID;
	}
}
