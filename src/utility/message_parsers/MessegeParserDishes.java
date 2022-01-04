package utility.message_parsers;

import java.util.ArrayList;

import utility.entity.Dish;
import utility.enums.DataType;
import utility.enums.RequestType;

/**
 * The Class MessegeParserDishes - holds all the functions needed to 
 * send messages regarding Dishes data in the system
 */
public class MessegeParserDishes {
	

	/**
	 * Prepare message with data type dishes.
	 *
	 * @param dishesList the ArrayList of dishes
	 * @param requestType the request type
	 * @return the message object
	 */
	public static Object prepareMessageWithDataType_Dishes(ArrayList<Dish> dishesList,
			RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.DISHES_LIST.toString());
		
		for(Dish dish: dishesList) {
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


	/**
	 * Handle message extract data as an ArrayList of dishes.
	 *
	 * @param message the message to extract data from
	 * @return the ArrayList of Dishes
	 */
	public static ArrayList<Dish> handleMessageExtractDataType_Dishes(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		if (!msg.get(1).equals("DISHES_LIST")) {
			// TODO:ADD ERROR HANDLING
			return null;
		}
		int i, j=2;
		ArrayList<Dish> result = new ArrayList<>();
		for(i=0; i<(msg.size()-2)/8; i++) {
			result.add(new Dish(msg.get(j),msg.get(j+1),msg.get(j+2),msg.get(j+3), msg.get(j+4),
					msg.get(j+5),msg.get(j+6), msg.get(j+7)));
			j+=8;
		}
		return result;
	}


	/**
	 * Prepare message with data of a single Dish.
	 *
	 * @param dataToSendToServer the data to send to server
	 * @param requestType the request type
	 * @return the message object
	 */
	public static Object prepareMessageWithDataType_SingleDish(Object dataToSendToServer, RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.DISH.toString());
		
		Dish dish = (Dish)dataToSendToServer;
		messageToPrepare.add(String.valueOf(dish.getDish_ID()));
		messageToPrepare.add(dish.getRes_ID());
		messageToPrepare.add(dish.getType());
		messageToPrepare.add(dish.getName());
		messageToPrepare.add(dish.getDescription());
		messageToPrepare.add(dish.getSize());
		messageToPrepare.add(dish.getCooking_level());
		messageToPrepare.add(dish.getPrice());			
		

		return messageToPrepare;
	}


	/**
	 * Handle message extract data of a single dish.
	 *
	 * @param message the message to extract data from 
	 * @return the dish class object
	 */
	public static Dish handleMessageExtractDataType_SingleDish(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		if (!msg.get(1).equals("DISH")) {
			// TODO:ADD ERROR HANDLING
			return null;
		}
		Dish result = new Dish(msg.get(2),msg.get(3),msg.get(4),msg.get(5), msg.get(6),
				msg.get(7),msg.get(8), msg.get(9));
		return result;
	}

}
