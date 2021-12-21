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

public class ClientUI extends Application{
	
	//debug mode setting, used to show the basic settings window on start
	final private static boolean DEBUG_MODE = false;
	//defaults
	final private static int DEFAULT_PORT = 5555;
	final private static String DEFAULT_HOST = "localhost";
	//settings
	private int starting_port = DEFAULT_PORT;
	private String starting_host = DEFAULT_HOST;
	//client side logic object
	public static BMClientLogic clientLogic;
	
	public static Stage parentWindow;
	public static IClientFxController loginScreen;
	public static FxControllerHistory historyStack;
	

	
	public static void main(String[] args) {
		launch(args);
	}

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
