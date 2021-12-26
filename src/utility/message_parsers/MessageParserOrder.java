package utility.message_parsers;

import java.util.ArrayList;

import utility.entity.Dish;
import utility.entity.Order;
import utility.enums.DataType;
import utility.enums.RequestType;

public class MessageParserOrder {
	
	public static ArrayList<Order> handleMessageExtractDataType_orders(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		if (!msg.get(1).equals("ORDERS_LIST")) {
			// TODO:ADD ERROR HANDLING
			return null;
		}
		int i, j=2, k;
		ArrayList<Order> result = new ArrayList<>();
		int cntOrders = Integer.valueOf(msg.get(j));
				
		for(i=0; i<cntOrders; i++) {
			Order order = new Order(Integer.valueOf(msg.get(j+1)),msg.get(j+2),msg.get(j+3),msg.get(j+4), 
					Integer.valueOf(msg.get(j+5)),msg.get(j+6), msg.get(j+7), msg.get(j+8));
			int cntDishes = Integer.valueOf(msg.get(j+9));
			j+=9;
			for(k=0; k < cntDishes; k++) {
			order.getDishesInOrder().add(new Dish(msg.get(j),msg.get(j+1),msg.get(j+2),msg.get(j+3), 
					msg.get(j+4),msg.get(j+5), msg.get(j+6), msg.get(j+7)));
			result.add(order);
			j+=8;
		}}
		return result;
	}
	
	public static Object prepareMessageWithDataType_Orders(ArrayList<Order> ordersList, RequestType requestType) {
	
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.ORDERS_LIST.toString());
		messageToPrepare.add(String.valueOf(ordersList.size())); //count of orders to a restaurant
		
		for(Order order: ordersList) {
			messageToPrepare.add(String.valueOf(order.getOrderID()));
			messageToPrepare.add(order.getTypeOfOrder());
			messageToPrepare.add(order.getDeliveryAddress());
			messageToPrepare.add(order.getStatus());
			messageToPrepare.add(String.valueOf(order.getTotalPrice()));
			messageToPrepare.add(order.getTimeOfOrder());
			messageToPrepare.add(order.getFullName());
			messageToPrepare.add(order.getUserPhone());
			messageToPrepare.add(String.valueOf(order.getDishesInOrder().size()));
			
			for(Dish dish: order.getDishesInOrder()) {
				messageToPrepare.add(String.valueOf(dish.getDish_ID()));
				messageToPrepare.add(String.valueOf(dish.getRes_ID()));
				messageToPrepare.add(dish.getType());
				messageToPrepare.add(dish.getName());
				messageToPrepare.add(dish.getDescription());
				
				messageToPrepare.add(dish.getSize());
				messageToPrepare.add(dish.getCooking_level());
				messageToPrepare.add(String.valueOf(dish.getPrice()));
			}
		}

		return messageToPrepare;
	}
	

}
