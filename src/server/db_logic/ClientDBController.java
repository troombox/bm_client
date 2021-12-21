package server.db_logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.exceptions.BMServerException;
import utility.entity.Client;
import utility.enums.ErrorType;
import utility.enums.UserType;

public class ClientDBController {
	
	private final String personalClientTableInDB = "personal_client";
	private final String businesslClientTableInDB = "business_client";
	private final String businessesTableInDB = "businesses";
	private final String userTableNameInDB = "users";
	Connection dbConnection;
	String dbName;
	
	public ClientDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
		
	}
	
	
	public void setNewClient(Client client)  throws BMServerException{
		String userEmail = client.getEmail();
		UserType userType = client.getUserType();
		String personalBranch = client.getPersonalBranch();
		ResultSet rs = null;
		PreparedStatement ps;
		try {
			String query = "SELECT * FROM  `"+ dbName + "`." + userTableNameInDB + " WHERE email = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, userEmail);
			rs = ps.executeQuery();
			if(!rs.next()) {//if user doesn't exist 
				throw new BMServerException(ErrorType.INVALID_CREDENTIALS_USER_NOT_FOUND, "USER DOESN'T EXIST");
			}
			query = "UPDATE `" + dbName + "`." + userTableNameInDB + " SET userType = ?  , personalBranch = ?  WHERE email = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, userType.toString());
			ps.setString(2, personalBranch);
			ps.setString(3, userEmail);
			ps.executeUpdate();
			if(userType == UserType.CLIENT_PERSONAL) {
				query = "INSERT INTO `" + dbName + "`." + personalClientTableInDB + "(personalCode,userId,balance,personalCreditNumber) VALUES(?,?,?,?)";
				ps = dbConnection.prepareStatement(query);
				ps.setInt(1,client.getUser_ID()*2);
				ps.setInt(2, client.getUser_ID());
				ps.setInt(3, 0);
				ps.setInt(4, client.getPersonalCreditNumber());
				ps.executeUpdate();
				rs.close();
				ps.close();
				
			}else if(userType == UserType.CLIENT_BUSINESS) {
				query = "SELECT * FROM  `"+ dbName + "`." + personalClientTableInDB + " WHERE userID = ?";
				PreparedStatement  ps1 = dbConnection.prepareStatement(query);
				ps1.setInt(1, client.getUser_ID());
				ResultSet rs1 = ps1.executeQuery();
				if(rs1.next()) {//if user exist, delete him 
					query = "DELETE FROM `" + dbName + "`." + personalClientTableInDB + " WHERE userID = ?";
					ps1 = dbConnection.prepareStatement(query);
					ps1.setInt(1, client.getUser_ID());
					ps1.execute();
				}
				//check if the business is approved
				query = "SELECT * FROM  `"+ dbName + "`." + businessesTableInDB + " WHERE businessID = ?";
				ps1 = dbConnection.prepareStatement(query);
				ps1.setInt(1, client.getBusinessId());
				rs1 = ps1.executeQuery();
				if(!rs1.next()) { //business not in the table 
					throw new BMServerException(ErrorType.BUSINESS_DOESNT_EXIST, "BUSINESS_DOESNT_EXIST");
				}
				if(rs1.getInt(3) != 1) {
					throw new BMServerException(ErrorType.BUSINESS_NOT_APPROVE, "BUSINESS_NOT_APPROVE");
				}
				
				query = "INSERT INTO `" + dbName + "`." + businesslClientTableInDB + "(businessCode,personalCode,balanceInApp,userID,budget,isApproved,businessId,personalCreditNumber) VALUES(?,?,?,?,?,?,?,?)";
				PreparedStatement ps2 = dbConnection.prepareStatement(query);
				ps2.setInt(1,client.getUser_ID()*3);
				ps2.setInt(2,client.getUser_ID()*2);
				ps2.setInt(3, 0);
				ps2.setInt(4, client.getUser_ID());
				ps2.setInt(5, client.getBudget());
				ps2.setInt(6, 0);
				ps2.setInt(7, client.getEmployerCode());
				ps2.setInt(8, client.getPersonalCreditNumber());
				ps2.executeUpdate();
				ps2.close();
				
			}
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new BMServerException(ErrorType.CLIENT_ALREADY_EXIST, "CLIENT_ALREADY_EXIST");
		}
		
	}

}
