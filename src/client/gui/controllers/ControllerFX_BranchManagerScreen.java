package client.gui.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import utility.entity.User;
import utility.enums.DataType;
import utility.enums.RequestType;

/**
 * The Class ControllerFX_BranchManagerScreen.
 * this screen is shown in branch manager account, it shows all options that branch manager can do in biteme 
 */

public class ControllerFX_BranchManagerScreen implements IClientFxController, Initializable{

	/** The previous. */
	IClientFxController previous;
	
    /** The title txt. hello to user message*/
    @FXML
    private Label titleTxt;
    
    /** The signout btn. */
    @FXML
    private Button signoutBtn;

    /** The back btn. */
    @FXML
    private Button backBtn;
    
    /** The register client btn. */
    @FXML
    private Button registerClientBtn;

    /** The register supplier btn. */
    @FXML
    private Button registerSupBtn;

    /** The view reports btn. */
    @FXML
    private Button viewRepBtn;
    

    /** The approve business. */
    @FXML
    private Button approveBusiness;
    

    /** The permission btn. */
    @FXML
    private Button permissionBtn;
    

    /** The report CEO btn. */
    @FXML
    private Button reportCEObtn;
    

    /** The report success popup. */
    @FXML
    private Pane reportSuccessPopup;
    
    

    /**
     * Move to register client window
     *
     * @param event the event
     */
    @FXML
    void moveToRegisterClientWin(ActionEvent event) {
    	IClientFxController nextScreen = new ControllerFX_RegisterClientScreen();
    	ClientUI.historyStack.pushFxController(this);
    	nextScreen.start(ClientUI.parentWindow);
    }

    /**
     * Move to register supplier window
     *
     * @param event the event
     */
    @FXML
    void moveToRegisterSupWin(ActionEvent event) {
    	IClientFxController nextScreen = new ControllerFX_RegisterSupplierScreen();
    	ClientUI.historyStack.pushFxController(this);
    	nextScreen.start(ClientUI.parentWindow);
    }

    /**
     * Move to view report window
     *
     * @param event the event
     */
    @FXML
    void moveToViewReportWin(ActionEvent event) {
    	IClientFxController nextScreen = new ControllerFX_OpenReportBranch();
    	ClientUI.historyStack.pushFxController(this);
    	nextScreen.start(ClientUI.parentWindow);
    }
    
    /**
     * Move to change permission of users window
     *
     * @param event the event
     */
    @FXML
    void moveToChangePermissionWin(ActionEvent event) {
    	IClientFxController nextScreen = new ControllerFX_ChangePermission();
    	ClientUI.historyStack.pushFxController(this);
    	nextScreen.start(ClientUI.parentWindow);
    }
    

    /**
     * Move to approve business window
     *
     * @param event the event
     */
    @FXML
    void doApproveBusiness(ActionEvent event) {
    	IClientFxController nextScreen = new ControllerFX_ApproveBusinesses();
    	ClientUI.historyStack.pushFxController(this);
    	nextScreen.start(ClientUI.parentWindow);
    }
    

    /**
     * Send report to CEO.
     *
     * @param event the event
     */
    @FXML
    void sendReportToCEO(ActionEvent event) {
    	User user = ClientUI.clientLogic.getLoggedUser();
    	String branchName = user.getPersonalBranch();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	File file = fileChooser.showOpenDialog(null);
    	try{
            byte [] mybytearray  = new byte [(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            String bytesToString = Base64.getEncoder().encodeToString(mybytearray);
            String message = bytesToString + "branch" + branchName; 
            ClientUI.clientLogic.sendMessageToServer(message,DataType.REPORT,RequestType.CLIENT_REQUEST_TO_SERVER_SEND_REPORT_TO_CEO);
            if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.ERROR_MESSAGE) {
            	reportSuccessPopup.setVisible(true);
            	PauseTransition delay = new PauseTransition(Duration.seconds(2));
            	delay.setOnFinished(e -> reportSuccessPopup.setVisible(false) );
            	delay.play();
            }else {
            	Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Error");
		    	alert.setHeaderText(null);
		    	alert.setContentText("There is a problem sending your report, sorry");
		    	alert.showAndWait();
		    	return;
            }
          }
      catch (Exception e) {
          System.out.println("Error send (Files)msg to Server");
          e.printStackTrace();
      }
    }
    

	/**
	 * Start.
	 *
	 * @param stage the stage
	 */
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
	
	
	/**
	 * Sets the welcome message.
	 */
	public void setWelcomeMessage() {
		String name = ClientUI.clientLogic.getLoggedUser().getFirstName();
		String last = ClientUI.clientLogic.getLoggedUser().getLastName();
        String branch = ClientUI.clientLogic.getLoggedUser().getPersonalBranch();
        String welcomeMessage = "Hello " + name + " " + last + "," + branch + " region manager";
        titleTxt.setText(welcomeMessage);
	}
	
	  /**
  	 * Do go back.
  	 *
  	 * @param event the event
  	 */
  	@FXML
	    void doGoBack(ActionEvent event) {
		  	ClientUI.clientLogic.logOutUser();
	    	ClientUI.historyStack.clearControllerHistory();
	    	ClientUI.loginScreen.start(ClientUI.parentWindow);
	    }
	  
	    /**
    	 * Do sign out.
    	 *
    	 * @param event the event
    	 */
    	@FXML
	    void doSignOut(ActionEvent event) {
	    	ClientUI.clientLogic.logOutUser();
	    	ClientUI.historyStack.clearControllerHistory();
	    	ClientUI.loginScreen.start(ClientUI.parentWindow);
	    }

		/**
		 * Initialize.
		 *
		 * @param location the location
		 * @param resources the resources
		 */
		@Override
		public void initialize(URL location, ResourceBundle resources) {	
			setWelcomeMessage();
		}

		
	

}
