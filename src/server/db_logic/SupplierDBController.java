package server.db_logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.exceptions.BMServerException;
import utility.entity.Client;
import utility.entity.Supplier;
import utility.enums.ErrorType;

/**
 * The Class SupplierDBController.
 * represents all the functions needed to connect with the data base
 */
public class SupplierDBController {
	
	/** The supplier table in DB. */
	private final String supplierTableInDB = "restaurant";
	
	/** The user table name in DB. */
	private final String userTableNameInDB = "users";
	
	/** The res worker table in DB. */
	private final String resWorkerTableInDB  = "res_workers";
	
	/** The DB connection. */
	Connection dbConnection;
	
	/** The DB name. */
	String dbName;
	
	/**
	 * Instantiates a new supplier DB controller.
	 *
	 * @param dbController the DB controller
	 */
	public SupplierDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
	}
	
	/**
	 * Sets the new supplier and updates it in the relevant tables
	 *
	 * @param supplier the new new supplier
	 * @throws BMServerException the BM server exception
	 */
	public void setNewSupplier(Supplier supplier)  throws BMServerException{
		ResultSet rs = null;
		PreparedStatement ps;
		try {
			String query = "SELECT * FROM  `"+ dbName + "`." + userTableNameInDB + " WHERE userID = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, supplier.getWorkerID());
			rs = ps.executeQuery();
			if(!rs.next()) { //worker doesn't exist
				throw new BMServerException(ErrorType.WORKER_DOSENT_EXIST, "WORKER_DOSENT_EXIST");
			}
			if(!rs.getString(7).equals("RESTAURANT_OWNER")) { //the user isn't RESTAURANT_OWNER
				throw new BMServerException(ErrorType.WORKER_DOSENT_RESTAURANT_OWNER, "WORKER_DOSENT_RESTAURANT_OWNER");
			}
			//change the status of RESTAURANT_OWNER
			query = "UPDATE `" + dbName + "`." + userTableNameInDB + " SET status = ?, personalBranch = ? WHERE userID = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, "active");
			ps.setString(2, supplier.getPersonalBranch());
			ps.setInt(3, Integer.parseInt(supplier.getWorkerID()));
			ps.executeUpdate();
			
			Statement stmt = dbConnection.createStatement(); 
			rs = stmt.executeQuery("SELECT count(*) FROM `"+ dbName + "`." + supplierTableInDB);
			rs.next();
			int count = rs.getInt(1);
			query = "INSERT INTO `" + dbName + "`." + supplierTableInDB + "(resId,resName,category,branch) VALUES(?,?,?,?)";
			PreparedStatement ps2 = dbConnection.prepareStatement(query);
			ps2.setInt(1,count+1);
			ps2.setString(2, supplier.getRestaurantName());
			ps2.setString(3, supplier.getCategories());
			ps2.setString(4, supplier.getPersonalBranch());
			//need to add the picture
			ps2.executeUpdate();
			
			//adding the worker of restaurant  to res_worker
			query = "INSERT INTO `" + dbName + "`." + resWorkerTableInDB + "(userID,resId) VALUES(?,?)";
			ps2 = dbConnection.prepareStatement(query);
			ps2.setString(1, supplier.getWorkerID());
			ps2.setInt(2,count+1);
			ps2.executeUpdate();
			
			ps2.close();
			rs.close();
		
		}catch (SQLException e) {
			//e.printStackTrace();
			throw new BMServerException(ErrorType.SUPPLIER_ALREADY_EXIST, "SUPPLIER_ALREADY_EXIST");
		}
	}
	
	

}
