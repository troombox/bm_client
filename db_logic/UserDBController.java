package server.db_logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.exceptions.BMServerException;
import utility.entity.User;
import utility.enums.ErrorType;
import utility.enums.UserType;

/**
 * The Class UserDBController.
 * represents all the functions needed to connect with the data base
 */
public class UserDBController {
	
	/** The user table name in DB. */
	private final String userTableNameInDB = "users";
	
	/** The w 4 c table name in DB. */
	private final String w4cTableNameInDB = "w4c";
	
	/** The DB connection. */
	Connection dbConnection;
	
	/** The DB name. */
	String dbName;
	
	/**
	 * Instantiates a new user DB controller.
	 *
	 * @param dbController the DB controller
	 */
	public UserDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
	}
	
	/**
	 * Authenticate and get full user data.
	 * checks the user status (frozen/active...) and returns all the full data about the user
	 *
	 * @param user the user
	 * @return the user
	 * @throws BMServerException the BM server exception
	 */
	public User	authenticateAndGetFullUserData(User user) throws BMServerException{
		User result;
		String userEmail,password;
		userEmail = user.getEmail();
		password = user.getPassword();
		PreparedStatement ps;
		try {
			String query = "SELECT * FROM  `"+ dbName + "`." + userTableNameInDB + " WHERE email = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, userEmail);
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next()) {//if user doesn't exist 
				throw new BMServerException(ErrorType.INVALID_CREDENTIALS_USER_NOT_FOUND, "user doesn't exist");
			}
			if(!rs.getString(11).equals(password)) { //password invalid
				throw new BMServerException(ErrorType.INVALID_CREDENTIALS_WRONG_PASSWORD, "password invalid"); 
			}
			if(rs.getBoolean(10) == true) {
				throw new BMServerException(ErrorType.INVALID_CREDENTIALS_USER_ALREADY_LOGGED_IN, "user currently logged");
			}
			if(rs.getString(8).equals("frozen")) {
				System.out.println("im in the frozen if");
                throw new BMServerException(ErrorType.USER_ACCOUNT_IS_FROZEN, "user acount is frozen");
            }
            if(rs.getString(8).equals("unregistered")) {
                throw new BMServerException(ErrorType.USER_IS_UNREGISTERED, "user is unregistered");
            }
			UserType userType = UserType.fromString(rs.getString(7)); 
			//DB holds:[user_ID|firstName|lastName|personalBranch|email|phone|userType|status|w4c(!)|isSignedIn|password]
			//we don't want to return some fields back to client side after authentication, so we leave them blank
			result = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)
					,userType,rs.getString(8),rs.getString(9),rs.getBoolean(10),"");
			
			rs.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Gets the personal code and business code of the user according to W 4 C.
	 *
	 * @param user the user
	 * @return the user with the codes from W 4 C
	 * @throws BMServerException the BM server exception
	 */
	public User getCodesAccordingToW4C(User user) throws BMServerException {
		//we assume that the user provided got an w4c
		User result;
		String w4cString = user.getW4c();
		PreparedStatement ps;
		try {
			String query = "SELECT * FROM  `"+ dbName + "`." + w4cTableNameInDB + " WHERE w4cCode = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, w4cString);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {//if w4c doesn't exist 
				throw new BMServerException(ErrorType.INVALID_W4_VALUE_NOT_FOUND, "w4c not in the system");
			}
			int personalCode = rs.getInt(2);
			int buisnessCode = -1;
			if(rs.getInt(3) != -1) {
				buisnessCode = rs.getInt(3);
			}
			return new User(-1, "", "", "", "", "", UserType.USER, "", "", false, "", personalCode, buisnessCode);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
//	public String CheckIfW4cInDB(String personalNum, String businessNum) {
//		PreparedStatement ps;
//		try {
//			String query = "SELECT * FROM "+dbName+"."+userTableNameInDB+" WHERE personal = ?";
//			ps = dbConnection.prepareStatement(query);
//			ps.setString(1, personalNum);
//			ResultSet rs = ps.executeQuery();
//			if(!rs.next()) {
//				return "Wrong W4c";
//			}
//			if(rs.getString(2) != businessNum) {
//				return "password is incorrect";
//			}
//			rs.close();
//			return "found";
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	/**
 * Sets the user to logged in into the system by updating the DB
 *
 * @param user the new user to logged in
 */
public void setUserToLoggedIn(User user) {
		if (user == null) {
			throw new IllegalArgumentException("setUserToLoggedIn: user is null");
		}
		String userEmail = user.getEmail();
		PreparedStatement ps;
		String query = "UPDATE `" + dbName + "`." + userTableNameInDB + " SET isSignedIn = 1 WHERE email = ?";
		try {
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, userEmail);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the user to logged out by updating the DB
	 *
	 * @param user the new user to logged out
	 */
	public void setUserToLoggedOut(User user) {
		if (user == null) {
			throw new IllegalArgumentException("setUserToLoggedIn: user is null");
		}
		String userEmail = user.getEmail();
		PreparedStatement ps;
		String query = "UPDATE `" + dbName + "`." + userTableNameInDB + " SET isSignedIn = 0 WHERE email = ?";
		try {
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, userEmail);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the all users to logged out.
	 */
	public void setAllUsersToLoggedOut() {
		String query = "UPDATE `" + dbName + "`." + userTableNameInDB + " SET isSignedIn = 0";
		try {
			PreparedStatement ps = dbConnection.prepareStatement(query);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
