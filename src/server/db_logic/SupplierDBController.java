package server.db_logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.exceptions.BMServerException;
import utility.entity.Client;
import utility.entity.Supplier;
import utility.enums.ErrorType;

public class SupplierDBController {
	
	private final String supplierTableInDB = "restaurant";
	Connection dbConnection;
	String dbName;
	
	public SupplierDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
	}
	
	public void setNewSupplier(Supplier supplier)  throws BMServerException{
		ResultSet rs = null;
		PreparedStatement ps;
		try {
			String query = "SELECT COUNT * FROM  `"+ dbName + "`." + supplierTableInDB + " WHERE resName = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, supplier.getRestaurantName());
			rs = ps.executeQuery();
			if(rs.next()) {//if user doesn't exist 
				throw new BMServerException(ErrorType.INVALID_CREDENTIALS_USER_NOT_FOUND, "supplier already exsit");
			}
			ps.executeQuery("SELECT COUNT(*) AS rowcount FROM `"+ dbName + "`." + supplierTableInDB);
			rs.next();
			int count = rs.getInt("rowcount");
			rs.close();
			int id = count+1;
			query = "INSERT INTO `" + dbName + "`." + supplierTableInDB + "(resId,resName,category,branch) VALUES(?,?,?,?)";
			PreparedStatement ps2 = dbConnection.prepareStatement(query);
			ps2.setInt(1,id);
			ps2.setString(2, supplier.getRestaurantName());
			ps2.setString(3, supplier.getCategories());
			ps2.setString(4, supplier.getPersonalBranch());
			//need to add the picture
			ps2.executeUpdate();
			ps2.close();
			ps.close();
		
		}catch (SQLException e) {
			e.printStackTrace();
			throw new BMServerException(ErrorType.CLIENT_ALREADY_EXIST, "CLIENT_ALREADY_EXIST");
		}
	}
	
	

}
