package utility.entity;

import java.util.HashMap;

public class ClientRefundsData {
	private int userID;
	private HashMap<Integer, Integer> resRefundPairs;
	
	public ClientRefundsData(int userID) {
		this.userID = userID;
	}
	
	public void addResRefundPair(int resID, int refundValue) {
		resRefundPairs.put(resID, refundValue);
	}
	
	public int getRefundByResID(int resID) {
		return resRefundPairs.get(resID);
	}
	
	public int getUserID() {
		return userID;
	}
	
	public HashMap<Integer, Integer> getResRefundHashmap(){
		return resRefundPairs;
	}
	
}
