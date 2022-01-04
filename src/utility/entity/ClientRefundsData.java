package utility.entity;

import java.util.HashMap;

/**
 * The Class ClientRefundsData.
 * represent the entity of client if refund is needed
 */
public class ClientRefundsData {
	
	/** The user id in system. */
	private int userID;
	
	/** The restaurant refund pairs. */
	private HashMap<Integer, Integer> resRefundPairs;
	
	/**
	 * Instantiates a new client refunds data.
	 *
	 * @param userID the user ID
	 */
	public ClientRefundsData(int userID) {
		this.resRefundPairs = new HashMap<>();
		this.userID = userID;
	}
	
	/**
	 * Adds a restaurant refund pair.
	 *
	 * @param resID the restaurant ID
	 * @param refundValue the refund value
	 */
	public void addResRefundPair(int resID, int refundValue) {
		resRefundPairs.put(resID, refundValue);
	}
	
	/**
	 * Gets the refund by res ID.
	 *
	 * @param resID the res ID
	 * @return the refund by res ID
	 */
	public int getRefundByResID(int resID) {
		if(resRefundPairs.get(resID) != null) {
			return resRefundPairs.get(resID);
		}
		 return 0;
	}
	
	/**
	 * Gets the user ID.
	 *
	 * @return the user ID
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * Gets the restaurant refund hashmap.
	 *
	 * @return the restaurant refund hashmap
	 */
	public HashMap<Integer, Integer> getResRefundHashmap(){
		return resRefundPairs;
	}
	
	/**
	 * Total refunds value.
	 *
	 * @return the result of total refund
	 */
	public int totalRefundsValue() {
		int result = 0;
		for(int k : resRefundPairs.keySet()) {
			result += resRefundPairs.get(k);
		}
		return result;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ClientRefundsData [userID=" + userID + ", resRefundPairs=" + resRefundPairs.toString() + "]";
	}
	
}
