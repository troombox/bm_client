package server.db_logic;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.exceptions.BMServerException;
import utility.enums.ErrorType;

import java.awt.Desktop;

public class OpenReoprtDBController {
	Connection dbConnection;
	String dbName;
	
	public OpenReoprtDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
		
	}
	
	
	public void openReoprt(String type,String year,String month,String path) throws BMServerException {
		PreparedStatement ps;
		FileOutputStream output = null;
		String date = year + "-" + month;
		int b=0;
        try {
        	String tableName = getTableName(type);
        	if(tableName.equals("error")) {
        		throw new BMServerException(ErrorType.REPORT_NOT_EXIST, "ERROR_OPEN_REPORT");
        	}
        	String query = "SELECT file  FROM  `"+ dbName + "`." + tableName + " WHERE date = ?";
            ps = dbConnection.prepareStatement(query);
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
            	throw new BMServerException(ErrorType.REPORT_NOT_EXIST, "REPORT_NOT_EXIST");
            }
            String fileName = "\\" + type + "(" + date + ").pdf";
    		File report = new File(path + fileName);
            InputStream ins =rs.getBinaryStream(1);
            BufferedInputStream bis = new BufferedInputStream(ins);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data =new byte[4096];
            try {
            output = new FileOutputStream(report);
            }catch(IOException e2) {
            	e2.printStackTrace();
    			throw new BMServerException(ErrorType.FILE_ALREADY_OPEN, "FILE_ALREADY_OPEN");
            }
            while ((b = ins.read(data)) != -1) {
                 output.write(data, 0, b);
            }
            output.close();
            if(report.exists()) {
            	Desktop desktop = Desktop.getDesktop();
            	desktop.open(report);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
			throw new BMServerException(ErrorType.REPORT_NOT_EXIST, "REPORT_NOT_EXIST");
		}
		 catch (IOException e1) {
        	e1.printStackTrace();
			throw new BMServerException(ErrorType.REPORT_NOT_EXIST, "REPORT_NOT_EXIST");
		}
}

	//genarte name for the right table according to the requset
	private String getTableName(String type) {
		String tableName="";
		switch(type) {
			case "Income":
				tableName = "income_report";
				break;
			case "Order":
				tableName = "order_report";
				break;
			case "Performance":
				tableName = "performance_report";
				break;
			default:
				tableName = "error";
				break;
		}
		return tableName;
				
	
	}
	//check if the file is open 
	 private boolean isFileClosed(File file) {  
         boolean closed;
         Channel channel = null;
         try {
             channel = new RandomAccessFile(file, "rw").getChannel();
             closed = true;
         } catch(Exception ex) {
             closed = false;
         } finally {
             if(channel!=null) {
                 try {
                     channel.close();
                 } catch (IOException ex) {
                     // exception handling
                 }
             }
         }
         return closed;
 }
	
	
	
}
