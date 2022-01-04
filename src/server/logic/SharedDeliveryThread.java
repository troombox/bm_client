package server.logic;

import java.text.SimpleDateFormat;

import server.db_logic.DBController;
import server.db_logic.OrderDBController;
import server.db_logic.ReportsDBController;
import utility.entity.Order;

public class SharedDeliveryThread implements Runnable{
	
	private OrderDBController orderController;
	private Order order;
	
	public SharedDeliveryThread (OrderDBController orderdbController,Order order) {
		this.orderController = orderdbController;
		this.order = order;
	}
	

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
		orderController.sharedDelivery(timeStampBefore,timeStampAfter,order.getTimeOfOrder());

	}


}
