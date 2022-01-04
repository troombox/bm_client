package server.db_logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.exceptions.BMServerException;
import utility.entity.BusinessClientData;
import utility.enums.ErrorType;

/**
 * The Class BusinessClientDBController.
 * represents all the functions needed to connect with the data base
 */
public class BusinessClientDBController {
	
	/** The business client table name in DB. */
	private final String businessClientTableNameInDB = "business_client";
	
	/** The DB connection. */
	Connection dbConnection;
	
	/** The DB name. */
	String dbName;
	
	/**
	 * Instantiates a new business client DB controller.
	 *
	 * @param dbController the DB controller
	 */
	public BusinessClientDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
	}
	
	/**
	 * Gets all the data of a specific business, with its business code.
	 *
	 * @param businessCode the business code of this specific business
	 * @return the wanted business client
	 * @throws BMServerException the BM server exception - when user doesn't exist
	 */
	public BusinessClientData getBUD(String businessCode) throws BMServerException {
		int bCode = Integer.parseInt(businessCode);
		PreparedStatement ps;
		try {
			String query = "SELECT * FROM  `"+ dbName + "`." + businessClientTableNameInDB + " WHERE businessCode = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setInt(1, bCode);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {//if user doesn't exist 
				throw new BMServerException(ErrorType.BUSINESS_CLIENT_DATA_NOT_FOUND, "BUSINESS_CLIENT_DATA_NOT_FOUND" + " client bCode:" + bCode);
			}
			BusinessClientData result = new BusinessClientData(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
					rs.getBoolean(6), rs.getInt(7), rs.getInt(8));
			
			rs.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
	}

	/**
	 * Update budget for a given business client.
	 *
	 * @param arrayList updateRequest - the userId and the budget
	 */
	public void updateBudgetForClient(ArrayList<String> updateRequest) {
		String userID = updateRequest.get(0);
		String newBudget = updateRequest.get(1);
		PreparedStatement ps;
		String query = "UPDATE `" + dbName + "`." + businessClientTableNameInDB + " SET budget = ? WHERE userId = ?";
		try {
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, newBudget);
			ps.setString(2, userID);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	
}
