package server.gui;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.stage.Stage;
import server.logic.BMServerLogic;

/**
 * The Class ServerUI.
 */
public class ServerUI extends Application {
	
	/** The BM server. */
	private static BMServerLogic bmServer;
	
	/** The Constant DEFAULT_PORT. */
	final public static int DEFAULT_PORT = 5555;
	
	/** The Constant DB_NAME. */
	final public static String DB_NAME = "bm-db"; //name of the schema in MYSQL
	
	/** The Constant DB_USER. */
	final public static String DB_USER = "root";
	
	/** The Constant DB_PASS. */
	final public static String DB_PASS = "Tzachi1234!";

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		 launch(args);
	}

	/**
	 * Start.
	 *
	 * @param primaryStage the primary stage
	 * @throws Exception the exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		//prepare and call javaFX window
        ServerMainWindowController serverController = new ServerMainWindowController();
        serverController.setServerUI(this);
        serverController.start(primaryStage);
        
	}
	
	/**
	 * this method checks if all the data about the DB is currect.
	 * creates an instance of BMServerLofic and listens to the client
	 *
	 * @param port the port
	 * @param schemaName the schema name
	 * @param DBuser the user
	 * @param DBpassword the password
	 * @param ctrl the ctrl
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	//this function needs ServerMainWindowController class to update gui from server logic
	public static boolean startServer(int port, String schemaName, String DBuser, 
			String DBpassword, ServerMainWindowController ctrl) throws Exception {
		if(port <= 0 || port >= 65535 || schemaName == null || DBuser == null || DBpassword == null) {
			//TODO: add error handling
			return false;
		}
		//SERVER INIT:
		bmServer = new BMServerLogic(port, schemaName, DBuser, DBpassword);
		try {
			bmServer.listen(); // Start listening for connections
			bmServer.setGuiController(ctrl); //set gui controller to let logic update gui
			
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
			throw new Exception("Error Starting server");
		}
		return true;
	}
	
	/**
	 * Stop server.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public static boolean stopServer() throws Exception{
		try {
			if(bmServer == null)
				return false;
			bmServer.close();
		} catch (IOException e) {
			System.out.println("ERROR - Could not stop the server!");
			throw new Exception("Error Stopping server");
		}
		bmServer = null;
		return true;
	}
	
	//----------------LISTEN FUNCTIONS

	/**
	 * Start server listening to clients
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public static boolean startServerListening() throws Exception{
		try {
			if(bmServer == null)
				return false;
			bmServer.listen();
		} catch (IOException e) {
			System.out.println("ERROR - Could not listen for clients!");
			throw new Exception("Error Listening to clients");
		}
		return true;
	}
	
	/**
	 * Stop server listening to clients
	 *
	 * @return true, if successful
	 */
	public static boolean stopServerListening() {
		if (bmServer == null)
			return false;
		bmServer.stopListening();
		return true;
	}
	
	/**
	 * Import users from system outside this system
	 * checks if the users doesn't exist already in our system.
	 * if they don't exist the method will add them
	 *
	 * @return true, if successful
	 */
	//why whould we need to import users!?
	public static boolean importUsers() {
		if(bmServer == null) {
			return false;
		}
        Connection dbConnection = bmServer.getDBController().getDBConnection();
        PreparedStatement ps;
        PreparedStatement ps1;
        try {
            String query = "SELECT * FROM `bm-db`.manage_users";
            ps = dbConnection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String query1 = "SELECT email,password FROM `bm-db`.users WHERE email = ?";
                ps1 = dbConnection.prepareStatement(query1);
                ps1.setString(1, rs.getString(3));
                ResultSet rs1 = ps1.executeQuery();
                if(!rs1.next()) { //if the user doesn't exist
                     String query2 = "INSERT INTO `bm-db`.users (firstName,lastName,email,phoneNumber,userType,status,password,personalBranch,userTZ) VALUES(?,?,?,?,?,?,?,?,?)";
                    ps1 = dbConnection.prepareStatement(query2);
                    ps1.setString(1, rs.getString(1));
                    ps1.setString(2, rs.getString(2));
                    ps1.setString(3, rs.getString(3));
                    ps1.setString(4, rs.getString(4));
                    ps1.setString(5, rs.getString(5));
                    ps1.setString(6, "unregistered");
                    ps1.setString(7, rs.getString(7));
                    ps1.setString(8, rs.getString(6));
                    ps1.setString(9, rs.getString(8));
                    ps1.executeUpdate();
                }
                rs1.close();
            }
            
            rs.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
