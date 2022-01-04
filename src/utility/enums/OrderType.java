package utility.enums;

/**
 * The Enum OrderType.
 * represent all actions related to order type in system.
 */
public enum OrderType {
	
	/** The pickup. */
	PICKUP("PICKUP"),
	
	/** The delivery done early. */
	DELIVERY_EARLY("DELIVERY_EARLY"),
	
	/** The regular delivery. */
	DELIVERY_REGULAR("DELIVERY_REGULAR"),
	
	/** The delivery by robot. */
	DELIVERY_ROBOT("DELIVERY_ROBOT"),
	/** The delivery that is shared. */
	SHARED("SHARED"),
	
	/** The unknown. */
	UNKNOWN("UNKNOWN");
	
	/** The order type. */
	private String orderType;
	
	/**
	 * Instantiates a new order type.
	 *
	 * @param orderType the order type
	 */
	private OrderType(String orderType) {
		this.orderType = orderType;
	}
	
	/**
	 * To string.
	 *
	 * @return the orderType string
	 */
	@Override
	public String toString() {
		return orderType;
	}
	
	/**
	 * From string.
	 *
	 * @param orderTypeString the order type string
	 * @return the order type
	 */
	public static OrderType fromString(String orderTypeString) {
		switch(orderTypeString) {
		case "PICKUP":
			return OrderType.PICKUP;
		case "DELIVERY_EARLY":
			return OrderType.DELIVERY_EARLY;
		case "DELIVERY_REGULAR":
			return OrderType.DELIVERY_REGULAR;
		case "DELIVERY_ROBOT":
			return OrderType.DELIVERY_ROBOT;
		case "SHARED":
			return OrderType.SHARED;
		default:
			return OrderType.UNKNOWN;
		}
	}
}
