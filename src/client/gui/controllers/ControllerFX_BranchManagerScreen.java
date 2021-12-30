package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControllerFX_BranchManagerScreen implements IClientFxController, Initializable{

	IClientFxController previous;
	
    @FXML
    private Label titleTxt;
    
    @FXML
    private Button signoutBtn;

    @FXML
    private Button backBtn;
    
    @FXML
    private Button registerClientBtn;

    @FXML
    private Button registerSupBtn;

    @FXML
    private Button viewRepBtn;
    

    @FXML
    private Button approveBusiness;
    

    @FXML
    private Button permissionBtn;
    
    

    @FXML
    void moveToRegisterClientWin(ActionEvent event) {
    	IClientFxController nextScreen = new ControllerFX_RegisterClientScreen();
    	ClientUI.historyStack.pushFxController(this);
    	nextScreen.start(ClientUI.parentWindow);
    }

    @FXML
    void moveToRegisterSupWin(ActionEvent event) {
    	IClientFxController nextScreen = new ControllerFX_RegisterSupplierScreen();
    	ClientUI.historyStack.pushFxController(this);
    	nextScreen.start(ClientUI.parentWindow);
    }

    @FXML
    void moveToViewReportWin(ActionEvent event) {
    	IClientFxController nextScreen = new ControllerFX_OpenReportBranch();
    	ClientUI.historyStack.pushFxController(this);
    	nextScreen.start(ClientUI.parentWindow);
    }
    
    @FXML
    void moveToChangePermissionWin(ActionEvent event) {
    	IClientFxController nextScreen = new ControllerFX_ChangePermission();
    	ClientUI.historyStack.pushFxController(this);
    	nextScreen.start(ClientUI.parentWindow);
    }
    

    @FXML
    void doApproveBusiness(ActionEvent event) {
    	IClientFxController nextScreen = new ControllerFX_ApproveBusinesses();
    	ClientUI.historyStack.pushFxController(this);
    	nextScreen.start(ClientUI.parentWindow);
    }

	@Override
	public void start(Stage stage) {
		Parent root = null;
        try {
        	
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/BranchManagerScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
//      scene.getStylesheets().add(getClass().getResource("/client/client_gui/LoginFxml.css").toExternalForm());
        stage.setTitle("Branch Manager");
        stage.setScene(scene);
        stage.show();
		
	}
	
	
	public void setWelcomeMessage() {
		String name = ClientUI.clientLogic.getLoggedUser().getFirstName();
		String last = ClientUI.clientLogic.getLoggedUser().getLastName();
        String branch = ClientUI.clientLogic.getLoggedUser().getPersonalBranch();
        String welcomeMessage = "Hello " + name + " " + last + "," + branch + " region manager";
        titleTxt.setText(welcomeMessage);
	}
	
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

		@Override
		public void initialize(URL location, ResourceBundle resources) {	
			setWelcomeMessage();
		}

		
	

}
