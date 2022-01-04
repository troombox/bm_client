package server.logic;

import java.text.SimpleDateFormat;

import server.db_logic.DBController;
import server.db_logic.OrderDBController;
import server.db_logic.ReportsDBController;
import utility.entity.Order;

/**
 * The Class SharedDeliveryThread charge of shred delivery.
 * Its will work when the first shed delivery order will start the thread.
 * the thread save time stamp of starting go to sleep 15 min and than save time stamp 
 * and than we go to orderController to update refund to all order in this shared delivery.  
 */
public class SharedDeliveryThread implements Runnable{
	
	/** The order controller. */
	private OrderDBController orderController;
	
	/** The first order. */
	private Order order;
	
	/**
	 * Instantiates a new shared delivery thread.
	 *
	 * @param orderdbController the orderdb controller
	 * @param first order 
	 */
	public SharedDeliveryThread (OrderDBController orderdbController,Order order) {
		this.orderController = orderdbController;
		this.order = order;
	}
	

	/**
	 * Run will wait 15 min for other orders join to the first shred delivery order.
	 */
	@Override
	public void run() {
		//waiting 15 min for other clients
		String timeStampBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String timeStampAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		orderController.sharedDelivery(timeStampBefore,timeStampAfter,order.getTimeOfOrder());//sending the range time and the first order time of order 

	}


}
