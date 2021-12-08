package client.client_gui;

import client.client_logic.BMClientLogic;
import client.interfaces.IClientFxController;
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
	private static BMClientLogic clientLogic;

	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		if(DEBUG_MODE) {
			debugModeStart();
		}
        IClientFxController clientFxController = new LoginControllerFx();
        clientFxController.start(primaryStage);
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
    	});
	}

}
