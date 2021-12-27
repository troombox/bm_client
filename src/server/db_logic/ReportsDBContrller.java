package server.db_logic;

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

import com.itextpdf.text.Document;   
import com.itextpdf.text.DocumentException;  
import com.itextpdf.text.Paragraph;  
import com.itextpdf.text.pdf.PdfWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import server.exceptions.BMServerException;
import utility.enums.ErrorType;
import utility.enums.UserType;
//TODO: func get month as arg, delete files maybe? generate only on dates 
public class ReportsDBContrller {

	private final String ordersTableInDB = "orders";
	private final String restaurantTableInDB = "restaurant";
	private final String dishInOrderTableInDB = "dish_in_order";
	private final String dishesTableInDB = "dishes";
	private final String IncomeReportTableNameInDB = "income_report";
	private final String orderReportTableNameInDB = "order_report";
	private final String performanceReportTableNameInDB = "performance_report";
	HashMap <Integer,Integer> restaurantTotalIncome = new HashMap<Integer,Integer>();
	HashMap <Integer,Integer> restaurantOrder = new HashMap<Integer,Integer>();
	HashMap <Integer,String> restaurantNameAndId = new HashMap<Integer,String>();
	HashMap <Integer,ArrayList<String>> orderRowPdf = new HashMap<Integer,ArrayList<String>>();
	HashMap <Integer,ArrayList<Integer>> resCountDelays = new HashMap<Integer,ArrayList<Integer>>();
	HashMap <String,ArrayList<String>> PerformanceRowPdf = new HashMap<String,ArrayList<String>>();
	ArrayList <String> rowInPdf = new ArrayList<String>();
	
	Connection dbConnection;
	String dbName;
	
	public ReportsDBContrller(DBController dbController) {
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
	
	public void createIncomeReport()  throws BMServerException{
		
		int currTotalPrice;
		PreparedStatement ps,ps2;
		try {
			String query = "SELECT resId,totalPrice FROM  `"+ dbName + "`." + ordersTableInDB + " WHERE status = 'done' and (timeOfOrder BETWEEN '2021-12-01' and '2021-12-31') ";
			ps = dbConnection.prepareStatement(query);
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
				//System.out.println(rs.getInt(1)+":"+restaurantTotalIncome.get(rs.getInt(1)));
			
			}
			query = "SELECT resId,resName  FROM  `"+ dbName + "`." + restaurantTableInDB;
			ps2=dbConnection.prepareStatement(query);
			rs= ps2.executeQuery();
			while(rs.next()) {
				if(restaurantTotalIncome.containsKey(rs.getInt(1))) {
					rowInPdf.add(rs.getString(2)+": "+restaurantTotalIncome.get(rs.getInt(1)));
					//System.out.println(rs.getString(2)+": "+restaurantTotalIncome.get(rs.getInt(1)));
					
				}
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new BMServerException(ErrorType.CLIENT_ALREADY_EXIST, "CLIENT_ALREADY_EXIST");
		}
		
		generatePDF(IncomeReportTableNameInDB,"Income Report",rowInPdf);
		
	}
	
	public void createOrderReport()  throws BMServerException{
		int counter;
		PreparedStatement ps,ps2;
		try {
			String query = "SELECT dishId FROM  `"+ dbName + "`." + dishInOrderTableInDB;
			ps = dbConnection.prepareStatement(query);
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
				//System.out.println(rs.getInt(1)+":"+restaurantTotalIncome.get(rs.getInt(1)));

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
			generatePDF(orderReportTableNameInDB, "order report", rowInPdf);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new BMServerException(ErrorType.CLIENT_ALREADY_EXIST, "CLIENT_ALREADY_EXIST");
		}	
	}
	
	public void createPerformanceReport()  throws BMServerException{
		
		int countonTime=0,countDelay=0;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		PreparedStatement ps,ps2;
		try {
			// Get resID all delayed orders  
			String query = "SELECT resId FROM  `"+ dbName + "`." + ordersTableInDB 
					+ " WHERE (((typeOfOrder=?) OR (typeOfOrder=?)) AND (time_to_sec(TIMEDIFF(timeOfArrival,timeOfOrder))/60>60)) "
					+ " OR (typeOfOrder=? AND (time_to_sec(TIMEDIFF(timeOfArrival,timeOfOrder))/60>20))" ;
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, "regular");
			ps.setString(2, "robot");
			ps.setString(3, "early");
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
				//System.out.println(rs.getInt(1)+":"+restaurantTotalIncome.get(rs.getInt(1)));

			}
			// Get resId all orders on time 
			query = "SELECT resId FROM  `"+ dbName + "`." + ordersTableInDB 
					+ " WHERE (((typeOfOrder=?) OR (typeOfOrder=?)) AND (time_to_sec(TIMEDIFF(timeOfArrival,timeOfOrder))/60<=60)) "
					+ " OR (typeOfOrder=? AND (time_to_sec(TIMEDIFF(timeOfArrival,timeOfOrder))/60<=20))" ;
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, "regular");
			ps.setString(2, "robot");
			ps.setString(3, "early");
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
				//System.out.println(rs.getInt(1)+":"+restaurantTotalIncome.get(rs.getInt(1)));

			}
			// Get names and ID of restaurant
			query = "SELECT resId,resName  FROM  `"+ dbName + "`." + restaurantTableInDB;
			ps2=dbConnection.prepareStatement(query);
			rs= ps2.executeQuery();
			while(rs.next()) {
				ArrayList<Integer> delayeAndOnTime = resCountDelays.get(rs.getInt(1));
				rowInPdf.add(rs.getString(2)+": Delayed- "+String.valueOf(delayeAndOnTime.get(0))+" On time- "+String.valueOf(delayeAndOnTime.get(1)));
			}
			generatePDF(performanceReportTableNameInDB,"performance report",rowInPdf);
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new BMServerException(ErrorType.CLIENT_ALREADY_EXIST, "CLIENT_ALREADY_EXIST");
		}
		
		
	}
	
	public void generatePDF(String table,String title, ArrayList<String> txt) {
		Document doc =  new Document(); 
		try {
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("C:\\Workspace\\bm\\bm_project\\Motivation3.pdf"));
			System.out.println("PDF created."); 
			String location = "C:\\Workspace\\bm\\bm_project\\Motivation2.pdf";
			doc.open();
			doc.add(new Paragraph(title));
			for(String row : txt) {
				doc.add(new Paragraph(row));
			}
			doc.close();
			writer.close();
			saveReport(table,location);
			System.out.println("pdf saved to db");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main( String[ ] args ) throws BMServerException {
		DBController dbc;
		dbc = DBController.getDBControllerInstanceFor("bm-db", "root", "Aa123456");
		dbc.connectToDBServer();
		ReportsDBContrller report= new ReportsDBContrller(dbc);
		report.createPerformanceReport();
	}

	private byte[] getByteArrayFromFile(String location) {
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
	    }
		return null;
	}
	
	private void saveReport(String tableName,String location) {
		PreparedStatement ps;
		String query;
		int b=0;
		File report = new File("C:\\Workspace\\bm\\bm_project\\readTry.pdf");
		FileOutputStream output = null;
		try {
		query = "INSERT INTO `"+ dbName + "`." + tableName+"(date,file) values(?,?)";
		ps = dbConnection.prepareStatement(query);
		ps.setInt(1,12);
		ByteArrayInputStream bais = new ByteArrayInputStream(getByteArrayFromFile(location));
		ps.setBlob(2, bais);
		ps.execute();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	/*
	 * opening the pdf from the db 
		query = "SELECT file  FROM  `"+ dbName + "`." + tableName;
		try {
			ps = dbConnection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			rs.next();
			InputStream ins =rs.getBinaryStream(1);
			BufferedInputStream bis = new BufferedInputStream(ins);
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			byte[] data =new byte[4096];
			output = new FileOutputStream(report);
			while ((b = ins.read(data)) != -1) {
				 output.write(data, 0, b);
			}
			output.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}*/
	}
	
}
