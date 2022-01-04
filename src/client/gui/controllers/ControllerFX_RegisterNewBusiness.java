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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.entity.Business;
import utility.enums.DataType;
import utility.enums.RequestType;

/**
 * The Class ControllerFX_RegisterNewBusiness.
 */
public class ControllerFX_RegisterNewBusiness implements IClientFxController {

    /** The signout btn. */
    @FXML
    private Button signoutBtn;

    /** The back btn. */
    @FXML
    private Button backBtn;

    /** The business name. */
    @FXML
    private TextField businessName;

    /** The business ID. */
    @FXML
    private TextField businessID;

    /** The business branch. */
    @FXML
    private TextField businessBranch;

    /** The register btn. */
    @FXML
    private Button registerBtn;

    /** The result txt. */
    @FXML
    private Label resultTxt;

    /**
     * Do go back.
     *
     * @param event the event
     */
    @FXML
    void doGoBack(ActionEvent event) {
    	ClientUI.historyStack.popFxController().start(ClientUI.parentWindow);
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
     * Register new business.
     *
     * @param event the event
     */
    @FXML
    void registerNewBusiness(ActionEvent event) {
    	String businessName1 = businessName.getText();
    	String businessId = businessID.getText();
    	String businessBranch1 = businessBranch.getText();
    	
    	if(!checkValidInputForBusiness(businessName1,businessId,businessBranch1)) {
    		return;
    	}
    	Business business = new Business(Integer.parseInt(businessId), businessName1, 1, ClientUI.clientLogic.getLoggedUser().getUser_ID(), businessBranch1);
    	ClientUI.clientLogic.sendMessageToServer(business,DataType.HR_MANAGER, RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_NEW_BUSINESS);
    	 try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO error handling
			e.printStackTrace();
		}
    	if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE) {
    		//resultLabel.setText(ClientUI.clientLogic.getLastDataRecieved().toString());
    		return;
    	}
    	doGoBack(null);
    	
    	
    }
    
     /**
      * Check valid input for business.
      *
      * @param businessName the business name
      * @param businessId the business id
      * @param businessBranch the business branch
      * @return true, if successful
      */
     private boolean checkValidInputForBusiness(String businessName,String businessId, String businessBranch) {
		if (businessName.trim().isEmpty()) {
			resultTxt.setText("You must enter a business name");
			return false;
   	 	}
		if(businessId == null) {
			resultTxt.setText("You must enter business id");
			return false;
		}
		if(businessBranch.trim().isEmpty()) {
			resultTxt.setText("You must enter business branch");
			return false;
		}
		
		return true;
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
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/registerBusinessHR.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	    Scene scene = new Scene(root);
	    stage.setTitle("HR Screen");
	    stage.setScene(scene);
	    stage.show();
	
		
	}

}
