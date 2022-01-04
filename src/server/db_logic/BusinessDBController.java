package server.db_logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.exceptions.BMServerException;
import utility.entity.Business;
import utility.enums.ErrorType;

/**
 * The Class BusinessDBController.
 * represents all the functions needed to connect with the data base
 */
public class BusinessDBController {
	
	/** The businesses table in DB. */
	private final String businessesTableInDB = "businesses";
	
	/** The DB connection. */
	Connection dbConnection;
	
	/** The DB name. */
	String dbName;
	
	/**
	 * Instantiates a new business DB controller.
	 *
	 * @param dbController the DB controller
	 */
	public BusinessDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
	}
	
	/**
	 * get all businesses that are in the wanted branch and that they are approved by their HR manager
	 *
	 * @param banchName - the branch where we want to find businesses
	 * @return the businesses names
	 */
	public ArrayList<String> getBusinessesNames(String banchName) {	
		PreparedStatement ps;
		ArrayList<String> names = new ArrayList<String>();
		try {
			String query = "SELECT businessName FROM  `"+ dbName + "`." + businessesTableInDB + " WHERE branch = ? AND isApproved = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, banchName);
			ps.setInt(2,1);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
			names.add(rs.getString(1));	
			}
			rs.close();
			return names;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Approve business after the HR registered it.
	 *
	 * @param the business that need to be approved
	 * @throws BMServerException the BM server exception - if there is an SQL exception
	 */
	public void approveBusiness(Business business) throws BMServerException{
		PreparedStatement ps;
		try {
				String query = "UPDATE `" + dbName +  "`." + businessesTableInDB  + " SET isApproved = ?" +
								" WHERE branch = ? AND businessName = ?";
				
				ps = dbConnection.prepareStatement(query);
				ps.setInt(1, business.getIsApproved());
				ps.setString(2, business.getBranch());
				ps.setString(3, business.getBusinessName());
				ps.executeUpdate();
				}catch (SQLException e) {
					e.printStackTrace();
					throw new BMServerException(ErrorType.BUSINESS_DOESNT_EXIST, "BUSINESS_DOESNT_EXIST");
				}
	}
	
	
}
