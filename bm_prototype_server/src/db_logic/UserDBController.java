package db_logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Order;
import entity.User;
import utility.DataType;
import utility.UserType;

public class UserDBController {
	
	private final String userTableNameInDB = "users";
	private final String w4cTableNameInDB = "w4c";
	
	Connection dbConnection;
	String dbName;
	
	public UserDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
	}
	
	public User	checkIfUserInDB(User user) throws Exception{
		User result;
		String userName,password;
		userName = user.getEmail();
		password = user.getPassword();
		PreparedStatement ps;
		try {
			String query = "SELECT * FROM "+dbName+"."+userTableNameInDB+" WHERE email = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next()) {//if user doesn't exist 
				throw new LogInException("user doesn't exist"); 
			}
			if(rs.getString(5) != password) { //password invalid
				throw new LogInException("password invalid"); 
			}
			
			UserType userType = UserType.fromString(rs.getString(7)); 
//			result = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)
//					,userType,rs.getString(8),rs.getString(9),rs.getBoolean(10),rs.getString(11));
			
			result = new User(0,"","","","","",userType,"","",rs.getBoolean(10),"");
			rs.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public String CheckIfW4cInDB(String personalNum, String businessNum) {
		PreparedStatement ps;
		
		try {
			String query = "SELECT * FROM "+dbName+"."+userTableNameInDB+" WHERE personal = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, personalNum);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				return "Wrong W4c";
			}
			if(rs.getString(2) != businessNum) {
				return "password is incorrect";
			}
			rs.close();
			return "found";
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	
	}

}
