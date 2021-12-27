package client.gui.controllers;

import java.io.IOException;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utility.enums.DataType;
import utility.enums.RequestType;

public class ControllerFX_CeoScreen implements IClientFxController{

	IClientFxController previous;
	    @FXML
	    private Label titleTxt;

	    @FXML
	    private Button signoutBtn;

	    @FXML
	    private Button backBtn;

	    @FXML
	    private Button watchReportBtn;

	    @FXML
	    void doGoBack(ActionEvent event) {
	    	ClientUI.historyStack.popFxController().start(ClientUI.parentWindow);
	    }
	  
	    @FXML
	    void doSignOut(ActionEvent event) {
	    	ClientUI.clientLogic.logOutUser();
	    	ClientUI.historyStack.clearControllerHistory();
	    	ClientUI.loginScreen.start(ClientUI.parentWindow);
	    }

	    @FXML
	    void watchReport(ActionEvent event) {
    		ClientUI.clientLogic.sendMessageToServer(user, DataType.USER, RequestType.CLIENT_REQUEST_TO_SERVER_LOGIN_REQUEST);
	    	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO error handling
				e.printStackTrace();
			}
	    }
	    
		@Override
		public void start(Stage stage) {
			Parent root = null;
	        try {
	        	
				root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/ControllerFX_CeoScreen.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
	        Scene scene = new Scene(root);
//	      scene.getStylesheets().add(getClass().getResource("/client/client_gui/LoginFxml.css").toExternalForm());
	        stage.setTitle("CEO");
	        stage.setScene(scene);
	        stage.show();
			
			
		}
}


