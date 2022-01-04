package utility.message_parsers;

import java.util.ArrayList;

import utility.enums.DataType;
import utility.enums.RequestType;

/**
 * The Class MessageParserTextString - holds all the functions needed to 
 * send messages holding String data in the system
 */
public class MessageParserTextString {

	/**
	 * Handle message extract data type single text string.
	 *
	 * @param message the message to extract data from 
	 * @return the string object
	 */
	public static String handleMessageExtractDataType_SingleTextString(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		if (!msg.get(1).equals("SINGLE_TEXT_STRING")) {
			// TODO: ADD error handling for this case;
			System.out.println("Error parseMessageDataType_SingleTextString not string");
			return null;
		}
		String string = msg.get(2);
		return string;
	}
	
	/**
	 * Prepare message with a single text string as data
	 *
	 * @param textString the text string to send
	 * @param requestType the request type
	 * @return the message object
	 */
	public static Object prepareMessageWithDataType_SingleTextString(String textString, RequestType requestType) {
		//DEBUG MESSAGE:
		// [CLIENT_REQUEST_TO_SERVER_DEBUG_MESSAGE | SINGLE_TEXT_STRING | _TEXT_STRING_ ]
		ArrayList<String> messageToServer = new ArrayList<String>();	
		messageToServer.add(requestType.toString());
		messageToServer.add(DataType.SINGLE_TEXT_STRING.toString());
		messageToServer.add(textString);
		
		return messageToServer;
	}
	
	/**
	 * Prepare message to server with data type of ArrayList string.
	 *
	 * @param dataToSendToServer the data to send to server
	 * @param requestType the request type
	 * @return the message object
	 */
	public static Object prepareMessageToServerDataType_ArrayListString(ArrayList<String> dataToSendToServer, RequestType requestType) {
		ArrayList<String> messageToServer = new ArrayList<String>();	
		messageToServer.add(requestType.toString());
		messageToServer.add(DataType.ARRAYLIST_STRING.toString());
		messageToServer.add(dataToSendToServer.get(0));
		messageToServer.add(dataToSendToServer.get(1));
		return messageToServer;
	}
	
	/**
	 * Prepare message to server data type array list string all string.
	 *
	 * @param dataToSendToServer the data to send to server
	 * @param requestType the request type
	 * @return the message object
	 */
	public static Object prepareMessageToServerDataType_ArrayListStringAllString(ArrayList<String> dataToSendToServer, RequestType requestType) {
		ArrayList<String> messageToServer = new ArrayList<String>();	
		messageToServer.add(requestType.toString());
		messageToServer.add(DataType.ARRAYLIST_STRING.toString());
		messageToServer.addAll(dataToSendToServer);
		return messageToServer;
	}
	
	/**
	 * Handle message extract data into a string ArrayList .
	 *
	 * @param message the message to extract data from
	 * @return the array list of strings
	 */
	public static ArrayList<String> handleMessageExtractDataType_ArrayListString(Object message){
		ArrayList<String> msg = (ArrayList<String>) message;
		msg.remove(0);
		msg.remove(0);
		return msg;
	}
	
	/**
	 * Parses the message data type array list string.
	 *
	 * @param message the message to parse
	 * @return the array list of strings
	 */
	public static ArrayList<String> parseMessageDataType_ArrayListString(Object message) {
		ArrayList<String> msg = (ArrayList<String>)message;
		System.out.println(msg.get(1));
		if (!msg.get(1).equals("ARRAYLIST_STRING")) {
			// TODO: ADD error handling for this case;
			System.out.println("Error parseMessageDataType_ArrayListString not arraylist");
			return null;
		}
		ArrayList<String> arraylist = new ArrayList<>();
		arraylist.add(msg.get(2));
		arraylist.add(msg.get(3));
		return arraylist;
	}
}
