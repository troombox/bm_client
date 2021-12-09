package server.server_gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import server.server_logic.BMServerLogic;

public class ServerUI extends Application {
	
	private static BMServerLogic bmServer;
	
	final public static int DEFAULT_PORT = 5555;
	final public static String DB_NAME = "prototype"; //name of the schema in MYSQL
	final public static String DB_USER = "root";
	final public static String DB_PASS = "789852";

	public static void main(String[] args) {
		 launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//prepare and call javaFX window
        ServerMainWindowController serverController = new ServerMainWindowController();
        serverController.setServerUI(this);
        serverController.start(primaryStage);
        
	}
	
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
	
	public static boolean stopServerListening() {
		if (bmServer == null)
			return false;
		bmServer.stopListening();
		return true;
	}

}
