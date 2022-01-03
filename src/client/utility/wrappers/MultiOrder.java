package client.utility.wrappers;

import java.util.ArrayList;

import utility.entity.Dish;
import utility.entity.Order;
import utility.entity.User;
import utility.enums.OrderType;

public class MultiOrder extends Order {
	
	ArrayList<String> resIDs;

	public MultiOrder(String userFirstName, String userLastName, String userPhone, String deliveryAddress,
			int restaurantID, OrderType typeOfOrder) {
		super(userFirstName, userLastName, userPhone, deliveryAddress, restaurantID, typeOfOrder);
		this.resIDs = new ArrayList<String>();
	}
	
	public MultiOrder(User user,  int restaurantID) {
		this(user.getFirstName(), user.getLastName(), user.getPhone(), "", restaurantID, OrderType.UNKNOWN);
	}
	
	@Override
	public void addDish(Dish dish) {
		super.addDish(dish);
		if(!resIDs.contains(dish.getRes_ID())) {
			resIDs.add(dish.getRes_ID());
		}
	}
	
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
	
	public int numberOfRestaurantsInOrder() {
		return resIDs.size();
	}
	
	public ArrayList<String> restaurantsInOrderList() {
		return resIDs;
	}
	
}
