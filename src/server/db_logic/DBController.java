package server.db_logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The Class DBController.
 * represents all the functions needed to connect with the data base
 */
public class DBController {
	
	/** The controller. */
	private static DBController controller = null;
	
	/** The connection. */
	private static Connection conn;
	
	/** The password. */
	private static String dbName, user, password;
	
	/** The connected. */
	private static boolean connected = false;
	
	/**
	 * Gets the DB controller instance for.
	 *
	 * @param givenDbName the given DB name
	 * @param givenUser the given user name
	 * @param givenPassword the given password
	 * @return the DB controller instance for
	 */
	public static DBController getDBControllerInstanceFor(String givenDbName, String givenUser, String givenPassword) {
		if(givenDbName.equals(dbName) && givenUser.equals(user) && givenPassword.equals(password)) {
			if(controller == null) {
				controller = new DBController(givenDbName, givenUser, givenPassword);
			}
			return controller;
		}
		if(connected) {
			disconnectFromDB();
		}
		controller = new DBController(givenDbName, givenUser, givenPassword);
		return controller;
	}

	/**
	 * Instantiates a new DB controller.
	 *
	 * @param dbName the DB name
	 * @param user the user name
	 * @param password the password
	 */
	private DBController(String dbName, String user, String password) {
		DBController.dbName = dbName;
		DBController.password = password;
		DBController.user = user;
	}
	
	/**
	 * Connect to DB server.
	 */
	public void connectToDBServer() {
		if (connected)
			return;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
			return;
		}
		try {

			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/" + dbName + "?serverTimezone=IST", user,password);
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return;
		}
		connected = true;
	}
	
	/**
	 * Gets the DB connection.
	 *
	 * @return the DB connection
	 */
	public Connection getDBConnection() {
		return conn;
	}
	
	/**
	 * Gets the DB name.
	 *
	 * @return the DB name
	 */
	public String getDBName() {
		return dbName;
	}
	
	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected() {
		return connected;
	}
	
	/**
	 * Disconnect from DB.
	 */
	public static void disconnectFromDB() {
		try {
			if(connected)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connected = false;
	}
	
	
	
}
