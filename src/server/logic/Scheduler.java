package server.logic;

import java.util.Calendar;


import server.db_logic.DBController;
import server.db_logic.ReportsDBController;

public class Scheduler implements Runnable {
	
	ReportsDBController reportsDBContrller;
	
	public Scheduler (DBController dbController) {
		this.reportsDBContrller=new ReportsDBController(dbController);
	}

	@Override
	public void run() {
		boolean flag = false;
		while(true) {
			//String currDate=reportsDBContrller.checkDate();
			Calendar cal = Calendar.getInstance();
			int day=cal.get(Calendar.DAY_OF_MONTH);
			//every 1th day of month create reports
			if(day==31) {
					// If month is January 0 is returned, %12 set month of report to 11 December
					int month = ((cal.get(Calendar.MONTH)-1)%12)+1; 
					int year = (cal.get(Calendar.YEAR));
					try {
						if(flag) month++;
						reportsDBContrller.createIncomeReport(month,year,"north");
						reportsDBContrller.createOrderReport(month,year,"north");
						reportsDBContrller.createPerformanceReport(month,year,"north");
						reportsDBContrller.createIncomeReport(month,year,"center");
						reportsDBContrller.createOrderReport(month,year,"center");
						reportsDBContrller.createPerformanceReport(month,year,"center");
						reportsDBContrller.createIncomeReport(month,year,"south");
						reportsDBContrller.createOrderReport(month,year,"south");
						reportsDBContrller.createPerformanceReport(month,year,"south");
						// Check if a quarter of the year has passed 
						if(month == 3 || month == 6 || month ==9 || month == 12) {
							reportsDBContrller.ceoReport(month,year);
						}
					}
					catch(Exception e) {
					
					}
		
			}
			try {
				// Sleep for 24 hours//86400000
				Thread.sleep(10000);
				flag = true;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
