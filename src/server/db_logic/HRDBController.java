package server.db_logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utility.entity.BusinessClient;
import utility.enums.DataType;
import utility.enums.RequestType;

public class HRDBController {
	
		private final static String business_client = "business_client";
		private final static String Users = "users";
		private final static String HR_businessId = "hr_businessid";
	
		static Connection dbConnection;
		static String dbName;
	
	public HRDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
	}
	
	public static boolean updateUsersInDB(ArrayList<String> usersToUpdate){
		
		PreparedStatement ps;
		
		for(int i = 0 ; i < usersToUpdate.size() ; i += 2) {
			try {
				String query = "UPDATE `" + dbName + "`." +  business_client +
							" SET isApproved = ?" +
							" WHERE userId = ?";
				ps = dbConnection.prepareStatement(query);
				ps.setString(1, usersToUpdate.get(i));
				ps.setString(2, usersToUpdate.get(i+1));
				int numberOfChangedRows = ps.executeUpdate();
				
				if(usersToUpdate.size() / 2 != numberOfChangedRows)
					return false;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	
	public static ArrayList<String> getUsersToApproveFromDB(int hrId){
		ArrayList<String> userListToApprove = new ArrayList<>();
		
		userListToApprove.add(RequestType.SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED.toString());
		userListToApprove.add(DataType.HR_MANAGER.toString());
		
		BusinessClient tempUser;
		PreparedStatement ps;
		PreparedStatement ps2;
		PreparedStatement ps3;

		try {
			String query = "SELECT U.userId, U.firstName, U.phoneNumber, U.email "
					+ "FROM `" + dbName + "`."+  HR_businessId +" HRB ,`" + dbName + "`."+  business_client +" BC ,`" + dbName +"`." + Users + " U "
					+ "WHERE HRB.hr_Id = ? AND HRB.business_Id = BC.businessId AND BC.userId = U.userId";
			
//			String query = "SELECT business_Id"
//					+ " FROM `" + dbName +"`." + HR_businessId +
//					" WHERE hr_Id = ?";
//			
			ps = dbConnection.prepareStatement(query);
			ps.setInt(1, hrId);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				userListToApprove.add(String.valueOf(rs.getInt(1)));
				userListToApprove.add(rs.getString(2));
				userListToApprove.add(rs.getString(3));
				userListToApprove.add(rs.getString(4));
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return userListToApprove;
	}

}
