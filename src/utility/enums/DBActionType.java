package utility.enums;

/**
 * The Enum DBActionType.
 * represent all actions related to db in system.
 */
public enum DBActionType {
	
	/** The unknown action. */
	UNKNOWN_ACTION("UNKNOWN_ACTION"),
	
	/** The update existing data in db. */
	UPDATE_EXISTING_DATA_IN_DB("UPDATE_EXISTING_DATA_IN_DB"),
	
	/** The write new data to db. */
	WRITE_NEW_DATA_TO_DB("WRITE_NEW_DATA_TO_DB");
	
	/** The action type. */
	private String actionType;
	
	/**
	 * Instantiates a new DB action type.
	 *
	 * @param request the actionType
	 */
	private DBActionType(String request) {
		this.actionType = request;
	}
	
	/**
	 * To string.
	 *
	 * @return the actionType as string
	 */
	@Override
	public String toString() {
		return actionType;
	}
}
