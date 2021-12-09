package client_gui;

import client_logic.BMClientLogic;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {	
	
	//TODO: EDIT LATER
	final public static int DEFAULT_PORT = 5555;
	final public static String DEFAULT_HOST = "192.168.242.111";
	
	public static BMClientLogic clientLogic;

	
    public static void main( String args[] ) throws Exception { 
         launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		//JAVAFX INIT:
        OrderFrameController orderFrameControl = new OrderFrameController(); // create StudentFrame
        orderFrameControl.start(primaryStage);
        
	}

	public static void connectWithHostIP(String host,  OrderFrameController orderFrameControl) throws Exception {
		//LOGIC INIT:
		clientLogic = new BMClientLogic(host, DEFAULT_PORT);
		orderFrameControl.setClientLogic(clientLogic);
	}
}



