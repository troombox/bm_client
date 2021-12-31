package utility.message_parsers;

import java.util.ArrayList;

import utility.entity.Dish;
import utility.entity.Order;
import utility.enums.DataType;
import utility.enums.OrderType;
import utility.enums.RequestType;

public class MessageParserOrder {

	public static Object prepareMessageWithDataType_Order(Order order, RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.ORDER.toString());
		messageToPrepare.add(order.getUserFirstName());
		messageToPrepare.add(order.getUserLastName());
		messageToPrepare.add(order.getUserPhone());
		messageToPrepare.add(order.getDeliveryAddress());
		messageToPrepare.add(String.valueOf(order.getRestaurantID()));
		messageToPrepare.add(order.getTypeOfOrder().toString());
		messageToPrepare.add(String.valueOf(order.getOrderPrice()));
		messageToPrepare.add(String.valueOf(order.getDeliveryPrice()));
		messageToPrepare.add(String.valueOf(order.isPrivateOrder()));
		messageToPrepare.add(order.getTimeOfOrder());
		for(Dish dish: order.getDishesInOrder()) {
			messageToPrepare.add(String.valueOf(dish.getDish_ID()));
			messageToPrepare.add(dish.getRes_ID());
			messageToPrepare.add(dish.getType());
			messageToPrepare.add(dish.getName());
			messageToPrepare.add(dish.getDescription());
			messageToPrepare.add(dish.getSize());
			messageToPrepare.add(dish.getCooking_level());
			messageToPrepare.add(dish.getPrice());				
		}
		return messageToPrepare;
	}
	
	public static Order handleMessageExtractDataType_Order(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		if (!msg.get(1).equals("ORDER")) {
			// TODO:ADD ERROR HANDLING
			return null;
		}
		Order order = new Order(msg.get(2), msg.get(3), msg.get(4), msg.get(5), Integer.parseInt(msg.get(6)), OrderType.fromString(msg.get(7)),Integer.parseInt(msg.get(8)), Integer.parseInt(msg.get(9)));
		order.setPrivateOrder(Boolean.parseBoolean(msg.get(10)));
		order.setTimeOfOrder(msg.get(11));
		int i, j=12;
		for(i=0; i<(msg.size()-11)/8; i++) {
			order.addDish(new Dish(msg.get(j),msg.get(j+1),msg.get(j+2),msg.get(j+3), msg.get(j+4),
					msg.get(j+5),msg.get(j+6), msg.get(j+7)));
			j+=8;
		}
		System.out.println("handleMessageExtractDataType_Order: " + order.toString());
		return order;
	}

}
