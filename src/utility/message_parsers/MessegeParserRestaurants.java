package utility.message_parsers;

import java.util.ArrayList;

import utility.entity.Restaurant;
import utility.enums.DataType;
import utility.enums.RequestType;

/**
 * The Class MessegeParserRestaurants - holds all the functions needed to 
 * send messages regarding Order data in the system
 */
public class MessegeParserRestaurants {
	
	/**
	 * Handle message extract ArrayList holding data of restaurants.
	 *
	 * @param message the message to extract data from
	 * @return the array list of Restaurant class objects
	 */
	public static ArrayList<Restaurant> handleMessageExtractDataType_Restaurants(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		if (!msg.get(1).equals("RESTAURANTS_LIST")) {
			// TODO:ADD ERROR HANDLING
			return null;
		}
		int i, j=2;
		ArrayList<Restaurant> result = new ArrayList<>();
		for(i=0; i<(msg.size()-2)/4; i++) {
			result.add(new Restaurant(msg.get(j),msg.get(j+1),msg.get(j+2),msg.get(j+3)));
			j+=4;
		}
		return result;
	}
	
	/**
	 * Prepare message holding ArrayList of Restaurant class objects.
	 *
	 * @param restaurantsList the ArrayList of restaurants to be sent
	 * @param requestType the request type
	 * @return the message object
	 */
	public static Object prepareMessageWithDataType_Restaurants(ArrayList<Restaurant> restaurantsList, RequestType requestType) {
		// this method is preparing user messages, data string for user are:
		// String orderNumber,String restaurantName,String orderTime,String
		// PhoneNumber,String TypeOfOrder,String orderAddress
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.RESTAURANTS_LIST.toString());
		
		for(Restaurant restaurant: restaurantsList) {
			messageToPrepare.add(String.valueOf(restaurant.getRes_ID()));
			messageToPrepare.add(restaurant.getResName());
			messageToPrepare.add(restaurant.getCategory());
			messageToPrepare.add(restaurant.getBranch());
		}

		return messageToPrepare;
	}

	/**
	 * Handle message extract data of a single restaurant.
	 *
	 * @param message the message to extract data from
	 * @return the restaurant object
	 */
	public static Restaurant handleMessageExtractDataType_singleRestaurant(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		if (!msg.get(1).equals("RESTAURANT")) {
			// TODO:ADD ERROR HANDLING
			return null;
		}
		return new Restaurant(msg.get(2),msg.get(3),msg.get(4),msg.get(5));
	}

	/**
	 * Prepare message with data type of a single restaurant class object.
	 *
	 * @param result the result class object
	 * @param requestType the request type
	 * @return the message object
	 */
	public static Object prepareMessageWithDataType_singleRestaurant(Restaurant result, RequestType requestType) {
			
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.RESTAURANT.toString());
		
		messageToPrepare.add(String.valueOf(result.getRes_ID()));
		messageToPrepare.add(result.getResName());
		messageToPrepare.add(result.getCategory());
		messageToPrepare.add(result.getBranch());

		return messageToPrepare;
	}

}
