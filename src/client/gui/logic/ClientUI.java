package client.gui.logic;

import java.io.IOException;

import client.gui.controllers.ControllerFX_Login;
import client.gui.history.FxControllerHistory;
import client.interfaces.IClientFxController;
import client.logic.BMClientLogic;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientUI.
 */
public class ClientUI extends Application{
	
	/** The Constant DEBUG_MODE. */
	//debug mode setting, used to show the basic settings window on start
	final private static boolean DEBUG_MODE = false;
	
	/** The Constant DEFAULT_PORT. */
	//defaults
	final private static int DEFAULT_PORT = 5555;
	
	/** The Constant DEFAULT_HOST. */
	final private static String DEFAULT_HOST = "localhost";
	
	/** The starting port. */
	//settings
	private int starting_port = DEFAULT_PORT;
	
	/** The starting host. */
	private String starting_host = DEFAULT_HOST;
	
	/** The client logic. */
	//client side logic object
	public static BMClientLogic clientLogic;
	
	/** The parent window. */
	public static Stage parentWindow;
	
	/** The login screen. */
	public static IClientFxController loginScreen;
	
	/** The history stack. */
	public static FxControllerHistory historyStack;
	

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Starting the clintUI window.
	 *
	 * @param primaryStage is the primary stage
	 */
	@Override
	public void start(Stage primaryStage) {
		//preparing stage:
		parentWindow = primaryStage;
		parentWindow.setResizable(false);
		parentWindow.setOnCloseRequest(e->{
			clientLogic.logOutUser();
			clientLogic.closeConnectionWithMessageToServer();
			System.exit(0);
		});
		loginScreen = new ControllerFX_Login();
        historyStack = new FxControllerHistory(loginScreen);
        loginScreen.start(parentWindow);
		if(DEBUG_MODE) {
			debugModeStart();
		} else {
			try {
				clientLogic = new BMClientLogic(starting_host, starting_port);
			} catch (IOException exception) {
				exception.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	/**
	 * Debug mode start. will allow the client run
	 * before connect to server, will provide the right data to 
	 * client for connect to server.
	 * 
	 */
	private void debugModeStart() {
		Label headerLabel = new Label("Debug Window");
		Button buttonSetDebugParams = new Button("Start");
		TextField txtFieldSetPort = new TextField(Integer.toString(DEFAULT_PORT));
		TextField txtFieldSetHost = new TextField(DEFAULT_HOST);
		txtFieldSetPort.setPromptText("PORT");
		txtFieldSetHost.setPromptText("HOST");
    	VBox root = new VBox();
    	root.getChildren().addAll(headerLabel,txtFieldSetPort,txtFieldSetHost,buttonSetDebugParams);
    	VBox.setMargin(txtFieldSetPort, new Insets(10));
    	VBox.setMargin(txtFieldSetHost, new Insets(10));
    	VBox.setMargin(buttonSetDebugParams, new Insets(5));
    	root.setAlignment(Pos.CENTER);
    	Scene scene = new Scene(root);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.setResizable(false);
    	stage.show();
    	buttonSetDebugParams.setOnAction(e ->{
    		starting_port = Integer.valueOf(txtFieldSetPort.getText());
    		starting_host = txtFieldSetHost.getText();
    		stage.close();
    		try {
				clientLogic = new BMClientLogic(starting_host, starting_port);
			} catch (IOException exception) {
				exception.printStackTrace();
				System.exit(1);
			}
    	});
	}

}
