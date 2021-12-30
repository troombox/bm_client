package server.db_logic;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.awt.geom.Rectangle2D.Double;
import com.itextpdf.text.Document;   
import com.itextpdf.text.DocumentException;  
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;

import server.exceptions.BMServerException;
import utility.enums.ErrorType;
import utility.enums.UserType;
//TODO: func get month as arg, delete files maybe? generate only on dates 
public class ReportsDBController {

	private final String ordersTableInDB = "orders";
	private final String restaurantTableInDB = "restaurant";
	private final String dishInOrderTableInDB = "dish_in_order";
	private final String dishesTableInDB = "dishes";
	private final String IncomeReportTableNameInDB = "income_report";
	private final String orderReportTableNameInDB = "order_report";
	private final String performanceReportTableNameInDB = "performance_report";
	private final String ceoReportTableNameInDB = "ceo_report";
	HashMap <Integer,Integer> restaurantTotalIncome; 
	HashMap <Integer,Integer> restaurantOrder = new HashMap<Integer,Integer>();
	HashMap <Integer,String> restaurantNameAndId = new HashMap<Integer,String>();
	HashMap <Integer,ArrayList<String>> orderRowPdf = new HashMap<Integer,ArrayList<String>>();
	HashMap <Integer,ArrayList<Integer>> resCountDelays = new HashMap<Integer,ArrayList<Integer>>();
	HashMap <String,ArrayList<String>> PerformanceRowPdf = new HashMap<String,ArrayList<String>>();
	ArrayList <String> rowInPdf ;
	
	Connection dbConnection;
	String dbName;
	
	public ReportsDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
		
	}
	
	public String checkDate() {
		PreparedStatement ps;
		String query = "SELECT CURRENT_DATE()";
		try {
			ps = dbConnection.prepareStatement(query);
			ResultSet rs= ps.executeQuery();
			if(rs.next()){
				return rs.getString(1);
				
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void createIncomeReport(int month,int year)  throws BMServerException{
		rowInPdf = new ArrayList<String>();
		restaurantTotalIncome = new HashMap<Integer,Integer>();
		int currTotalPrice;
		PreparedStatement ps,ps2;
		try {
			System.out.println(String.valueOf(year)+"-"+String.valueOf(month)+"-01");
			String query = "SELECT resId,totalPrice FROM  `"+ dbName + "`." + ordersTableInDB + " WHERE status = ? and (timeOfOrder BETWEEN ? and ?) ";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, "done");
			ps.setString(2, String.valueOf(year)+"-"+String.valueOf(month)+"-01");
			ps.setString(3, String.valueOf(year)+"-"+String.valueOf(month)+"-31");
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				// Add price of next order to existing restaurant
				if (restaurantTotalIncome.containsKey(rs.getInt(1))){
					currTotalPrice = restaurantTotalIncome.get(rs.getInt(1));
					currTotalPrice += rs.getInt(2);
					restaurantTotalIncome.replace(rs.getInt(1),currTotalPrice);
				}
				else
				{
					// Add new restaurant
					restaurantTotalIncome.put(rs.getInt(1), rs.getInt(2));
				}
			
			}
			query = "SELECT resId,resName  FROM  `"+ dbName + "`." + restaurantTableInDB;
			ps2=dbConnection.prepareStatement(query);
			rs= ps2.executeQuery();
			while(rs.next()) {
				if(restaurantTotalIncome.containsKey(rs.getInt(1))) {
					rowInPdf.add(rs.getString(2)+": "+restaurantTotalIncome.get(rs.getInt(1)));
					
				}
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new BMServerException(ErrorType.ERROR_CREATING_REPORT, "ERROR_CREATING_REPORT");
		}
		
		generatePDF(IncomeReportTableNameInDB,"Income Report",month,year,rowInPdf,null);
		
	}
	
	public void createOrderReport(int month,int year)  throws BMServerException{
		rowInPdf = new ArrayList<String>();
		int counter;
		PreparedStatement ps,ps2;
		try {
			String query = "SELECT dishId FROM `"+ dbName + "`." + dishInOrderTableInDB +" as A, "+"`"+ dbName + "`." + ordersTableInDB+" as B WHERE (( A.orderId = B.orderId) AND (B.timeOfOrder LIKE ?))";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, "____-"+month+"%");
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				// Add amount of dishes to existing dish
				if (restaurantOrder.containsKey(rs.getInt(1))){
					counter = restaurantOrder.get(rs.getInt(1));
					counter ++;
					restaurantOrder.replace(rs.getInt(1),counter);
				}
				else
				{
					// Add new dish
					restaurantOrder.put(rs.getInt(1), 1);
				}

			}
			query = "SELECT resId,resName  FROM  `"+ dbName + "`." + restaurantTableInDB;
			ps2=dbConnection.prepareStatement(query);
			rs= ps2.executeQuery();
			while(rs.next()) {
				restaurantNameAndId.put(rs.getInt(1), rs.getString(2));
			}
			query = "SELECT dishId,resId,name  FROM  `"+ dbName + "`." + dishesTableInDB;
			ps2=dbConnection.prepareStatement(query);
			rs= ps2.executeQuery();
			while(rs.next()) {
				if(restaurantOrder.containsKey(rs.getInt(1))) {
					if(orderRowPdf.containsKey(rs.getInt(2))) {
						orderRowPdf.get(rs.getInt(2)).add(rs.getString(3)+": "+restaurantOrder.get(rs.getInt(1)));
						
					}
					else {
						orderRowPdf.put(rs.getInt(2), new ArrayList<String>());
						orderRowPdf.get(rs.getInt(2)).add(rs.getString(3)+": "+restaurantOrder.get(rs.getInt(1)));
						
					}
				}
			}
			
			for(int key : orderRowPdf.keySet()) {
				rowInPdf.add(restaurantNameAndId.get(key));
				rowInPdf.addAll(orderRowPdf.get(key));
				
			}
			generatePDF(orderReportTableNameInDB, "order report",month,year, rowInPdf,null);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new BMServerException(ErrorType.ERROR_CREATING_REPORT, "ERROR_CREATING_REPORT");
		}	
	}
	
	public void createPerformanceReport(int month,int year)  throws BMServerException{
		rowInPdf = new ArrayList<String>();
		int countonTime=0,countDelay=0;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		PreparedStatement ps,ps2;
		try {
			// Get resID all delayed orders  
			String query = "SELECT resId FROM  `"+ dbName + "`." + ordersTableInDB 
					+ " WHERE (timeOfOrder LIKE ? AND ((typeOfOrder=?) OR (typeOfOrder=?)) AND (time_to_sec(TIMEDIFF(timeOfArrival,timeOfOrder))/60>60)) "
					+ " OR (timeOfOrder LIKE ? AND typeOfOrder=? AND (time_to_sec(TIMEDIFF(timeOfArrival,timeOfOrder))/60>20))" ;
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, "____-"+month+"%");
			ps.setString(2, "regular");
			ps.setString(3, "robot");
			ps.setString(4, "____-"+month+"%");
			ps.setString(5, "early");
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				// Add amount of delays to existing res
				if (resCountDelays.containsKey(rs.getInt(1))){
					temp = resCountDelays.get(rs.getInt(1));
					countDelay=temp.remove(0);
					temp.add(0, ++countDelay);
					resCountDelays.replace(rs.getInt(1),temp); 
				}
				else
				{
					// Add new res
					ArrayList<Integer> res = new ArrayList<Integer>();
					res.add(1);
					res.add(0);
					resCountDelays.put(rs.getInt(1), res);
				}

			}
			
			// Get resId all orders on time 
			query = "SELECT resId FROM  `"+ dbName + "`." + ordersTableInDB 
					+ " WHERE (timeOfOrder LIKE ? AND ((typeOfOrder=?) OR (typeOfOrder=?)) AND (time_to_sec(TIMEDIFF(timeOfArrival,timeOfOrder))/60<=60)) "
					+ " OR (timeOfOrder LIKE ? AND typeOfOrder=? AND (time_to_sec(TIMEDIFF(timeOfArrival,timeOfOrder))/60<=20))" ;
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, "____-"+month+"%");
			ps.setString(2, "regular");
			ps.setString(3, "robot");
			ps.setString(4, "____-"+month+"%");
			ps.setString(5, "early");
			rs= ps.executeQuery();
			while(rs.next()) {
				// Add amount of on time to existing res
				if (resCountDelays.containsKey(rs.getInt(1))){
					temp = resCountDelays.get(rs.getInt(1));
					countonTime=temp.remove(1);
					temp.add(1, ++countonTime);
					resCountDelays.replace(rs.getInt(1),temp); 
				}
				else
				{
					// Add new res and no delays
					ArrayList<Integer> res = new ArrayList<Integer>();
					res.add(0);
					res.add(1);
					resCountDelays.put(rs.getInt(1), res);
				}

			}
			// Get names and ID of restaurant
			query = "SELECT resId,resName  FROM  `"+ dbName + "`." + restaurantTableInDB;
			ps2=dbConnection.prepareStatement(query);
			rs= ps2.executeQuery();
			while(rs.next()) {
				ArrayList<Integer> delayeAndOnTime = resCountDelays.get(rs.getInt(1));
				if(delayeAndOnTime != null){
				rowInPdf.add(rs.getString(2)+": Delayed- "+String.valueOf(delayeAndOnTime.get(0))+" On time- "+String.valueOf(delayeAndOnTime.get(1)));
				}else {
					rowInPdf.add(rs.getString(2)+": Delayed- 0 On time- 0");
				}
			}
			generatePDF(performanceReportTableNameInDB,"performance report",month,year,rowInPdf,null);
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new BMServerException(ErrorType.ERROR_CREATING_REPORT, "ERROR_CREATING_REPORT");
		}
		
		
	}
	
	public void ceoReport(int month,int year) throws BMServerException {
		rowInPdf = new ArrayList<String>();
		restaurantTotalIncome = new HashMap<Integer,Integer>();
		PreparedStatement ps;
		HashMap<Integer,Integer> countOrder = new HashMap<Integer,Integer>(); 
		int currTotalPrice,count,totalIncomeInQuarter=0;
		try {
			
			String query = "SELECT resId,totalPrice FROM  `"+ dbName + "`." + ordersTableInDB + " WHERE status = 'done' and (timeOfOrder BETWEEN ? and ?) ";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, String.valueOf(year)+"-"+String.valueOf((month-3))+"-01");
			ps.setString(2, String.valueOf(year)+"-"+String.valueOf(month)+"-31");
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				// Add price of next order to existing restaurant
				if (restaurantTotalIncome.containsKey(rs.getInt(1))){
					currTotalPrice = restaurantTotalIncome.get(rs.getInt(1));
					currTotalPrice += rs.getInt(2);
					restaurantTotalIncome.replace(rs.getInt(1),currTotalPrice);
					//Count how many orders for each restaurant
					count = countOrder.get(rs.getInt(1));
					countOrder.replace(rs.getInt(1),++count);
				}
				else
				{
					// Add new restaurant
					restaurantTotalIncome.put(rs.getInt(1), rs.getInt(2));
					countOrder.put(rs.getInt(1), 1);
				}

	
			}
			
			JFreeChart barChart = ChartFactory.createBarChart("Order quarterly report","Restaurant","Amount"
	    		  ,createDataset(countOrder),PlotOrientation.VERTICAL,true, true, false);
		      
			for (Integer key : restaurantTotalIncome.keySet()) {
				totalIncomeInQuarter+=restaurantTotalIncome.get(key);
		      }
	

			rowInPdf.add("Total income in quarter: "+totalIncomeInQuarter);
			
			query = "SELECT resId,resName  FROM  `"+ dbName + "`." + restaurantTableInDB;
			ps=dbConnection.prepareStatement(query);
			rs= ps.executeQuery();
			while(rs.next()) {
				if(restaurantTotalIncome.containsKey(rs.getInt(1))) {
					rowInPdf.add(rs.getString(2)+": "+restaurantTotalIncome.get(rs.getInt(1)));
				}
			}
			generatePDF(ceoReportTableNameInDB,"ceo quarterly report for",month,year,rowInPdf,barChart);
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new BMServerException(ErrorType.ERROR_CREATING_REPORT, "ERROR_CREATING_REPORT");
		}
		
	}
	
	   public CategoryDataset createDataset(HashMap<Integer,Integer> data ) {      
		     
		      final DefaultCategoryDataset dataset = 
		      new DefaultCategoryDataset( );  
		      
		      for (Integer key : data.keySet()) {
				dataset.addValue((double)data.get(key), key, key);
		      }

		      return dataset; 
		   }
	
	public void generatePDF(String table,String title,int month,int year, ArrayList<String> txt,JFreeChart barChart) throws BMServerException {
		Document doc =  new Document(); 
		String location;
		try {
			if(table == "ceo_report") {
				location="C:\\reports\\"+title+String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(month-3)+".pdf";
			}
			else {
				location = "C:\\reports\\"+title+String.valueOf(year)+"-"+String.valueOf(month)+".pdf";
			}
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(location));
			System.out.println("PDF created."); 
			
			doc.open();
			doc.add(new Paragraph(title));
			if(barChart != null) {
				PdfContentByte cb = writer.getDirectContent();
				PdfTemplate bar = cb.createTemplate(600, 400);
				Graphics2D g2d2 = new PdfGraphics2D(bar, 600, 400);
				Rectangle2D r2d2 = new Rectangle2D.Double(0, 0, 600, 400);
				barChart.draw(g2d2, r2d2);
				g2d2.dispose();
				cb.addTemplate(bar, 0, 0);
			}
			for(String row : txt) {
				doc.add(new Paragraph(row));
			}
			doc.close();
			writer.close();
			saveReport(table,location,month,year);
			System.out.println("pdf saved to db");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BMServerException(ErrorType.ERROR_CREATING_REPORT, "ERROR_CREATING_REPORT");
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new BMServerException(ErrorType.ERROR_CREATING_REPORT, "ERROR_CREATING_REPORT");
		}

	}
	

	public static void main( String[ ] args ) throws BMServerException {
		DBController dbc;
		dbc = DBController.getDBControllerInstanceFor("bm-db", "root", "Tzachi1234!");
		dbc.connectToDBServer();
		ReportsDBController report= new ReportsDBController(dbc);
		report.ceoReport(12,2021);
		report.createOrderReport(12, 2021);
		report.createIncomeReport(12, 2021);
		report.createPerformanceReport(12, 2021);
	}

	private byte[] getByteArrayFromFile(String location) throws BMServerException {
	    try {
	    	
		    final byte[] buffer = new byte[500];
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			File newFile = new File (location);
			FileInputStream fis = new FileInputStream(newFile); 
		    BufferedInputStream bis = new BufferedInputStream(fis);
	
		    int read = -1;
		    while ((read = bis.read(buffer)) > 0) {
		        baos.write(buffer, 0, read);
		    }
		    bis.close();
		    return baos.toByteArray();
	    }

	    catch(IOException e) {
			e.printStackTrace();
			throw new BMServerException(ErrorType.ERROR_CREATING_REPORT, "ERROR_CREATING_REPORT");
	    }

	}
	
	private void saveReport(String tableName,String location,int month,int year) {
		PreparedStatement ps;
		String query;

		try {
		query = "INSERT INTO `"+ dbName + "`." + tableName+"(date,file) values(?,?)";
		ps = dbConnection.prepareStatement(query);
		ps.setString(1,String.valueOf(year)+"-"+String.valueOf(month));
		ByteArrayInputStream bais = new ByteArrayInputStream(getByteArrayFromFile(location));
		ps.setBlob(2, bais);
		ps.execute();
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}
	
}
