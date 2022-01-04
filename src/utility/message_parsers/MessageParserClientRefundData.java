package utility.message_parsers;

import java.util.ArrayList;

import utility.entity.ClientRefundsData;
import utility.enums.DataType;
import utility.enums.RequestType;

/**
 * The Class MessageParserClientRefundData - used to prepare and parse messages containing ClientRefundData
 */
public class MessageParserClientRefundData {
	
	/**
	 * Prepare message with data type ClientRefundData.
	 *
	 * @param cfd the ClientRefundData object to send
	 * @param requestType the request type
	 * @return the object message
	 */
	public static Object prepareMessageWithDataType_ClientRefundData(ClientRefundsData cfd, RequestType requestType) {
		ArrayList<String> messageToPrepare = new ArrayList<String>();
		messageToPrepare.add(requestType.toString());
		messageToPrepare.add(DataType.CLIENT_REFUNDS_DATA.toString());
		messageToPrepare.add(String.valueOf(cfd.getUserID()));
		for(int k : cfd.getResRefundHashmap().keySet()) {
			messageToPrepare.add(String.valueOf(k));
			messageToPrepare.add(String.valueOf(cfd.getResRefundHashmap().get(k)));
		}
		return messageToPrepare;
	}
	
	/**
	 * Handle message extract ClientRefundData data
	 *
	 * @param message the message to extract the data from 
	 * @return the client refunds data as a ClientRefundData class object
	 */
	public static ClientRefundsData handleMessageExtractDataType_ClientRefundData(Object message) {
		ArrayList<String> msg = (ArrayList<String>) message;
		if (!msg.get(1).equals("CLIENT_REFUNDS_DATA")) {
			// TODO:ADD ERROR HANDLING
			return null;
		}
		ClientRefundsData crf = new ClientRefundsData(Integer.parseInt(msg.get(2)));
		for(int i=3; i < msg.size(); i = i+2) {
			crf.addResRefundPair(Integer.parseInt(msg.get(i)), Integer.parseInt(msg.get(i+1)));
		}
		return crf;
	}
}