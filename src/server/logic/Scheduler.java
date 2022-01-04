package server.logic;

import java.util.Calendar;


import server.db_logic.DBController;
import server.db_logic.ReportsDBController;

/**
 * The Class Scheduler charge of creating reports.
 * this class check every day if today is the 1th 
 * of month ,if it is create the report for every branch.
 *	If its end of quarter create quarterly report.
 */
public class Scheduler implements Runnable {
	
	/** The reports DB contrller. */
	ReportsDBController reportsDBContrller;
	
	/**
	 * Instantiates a new scheduler.
	 *
	 * @param dbController the db controller
	 */
	public Scheduler (DBController dbController) {
		this.reportsDBContrller=new ReportsDBController(dbController);
	}

	/**
	 * Run will work when that thread will start.
	 */
	@Override
	public void run() {
		String monthString;
		while(true) {
			//String currDate=reportsDBContrller.checkDate();
			Calendar cal = Calendar.getInstance();
			int day=cal.get(Calendar.DAY_OF_MONTH);
			//every 1th day of month create reports
			if(day==1) {
					// If month is January 0 is returned, %12 set month of report to 11 December
					int month = (((cal.get(Calendar.MONTH)+11))%12)+1;
					String year = String.valueOf((cal.get(Calendar.YEAR)));
					try {
						if(month < 10) {
							monthString = "0" + String.valueOf(month);
						}else {
							monthString = String.valueOf(month);
						}
						if(month == 12) {
							year = String.valueOf((cal.get(Calendar.YEAR)-1));
						}
						reportsDBContrller.createIncomeReport(monthString,year,"north");
						reportsDBContrller.createOrderReport(monthString,year,"north");
						reportsDBContrller.createPerformanceReport(monthString,year,"north");
						reportsDBContrller.createIncomeReport(monthString,year,"center");
						reportsDBContrller.createOrderReport(monthString,year,"center");
						reportsDBContrller.createPerformanceReport(monthString,year,"center");
						reportsDBContrller.createIncomeReport(monthString,year,"south");
						reportsDBContrller.createOrderReport(monthString,year,"south");
						reportsDBContrller.createPerformanceReport(monthString,year,"south");
						reportsDBContrller.createIncomeReportForEachSupplier(monthString,year);
						// Check if a quarter of the year has passed 
						if(month == 3 || month == 6 || month ==9 || month == 12) {
							reportsDBContrller.ceoReport(monthString,year);
						}
					}
					catch(Exception e) {
					
					}
		
			}
			try {
				// Sleep for 24 hours
				Thread.sleep(86400000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
