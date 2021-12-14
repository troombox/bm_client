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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.entity.Client;
import utility.enums.DataType;
import utility.enums.RequestType;
import utility.enums.UserType;

public class ControllerFX_RegisterClientScreen implements IClientFxController {

	@FXML
    private Button signoutBtn;

    @FXML
    private Button backBtn;
	@FXML
    private TextField firstNameTxt;

    @FXML
    private TextField LastNameTxt;

    @FXML
    private TextField phoneNumTxt;

    @FXML
    private TextField emailTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField carditNumTxt;

    @FXML
    private Button sendBtn;

    @FXML
    private CheckBox businessCheckBox;

    @FXML
    private TextField employerCodTxt;

    @FXML
    private TextField monthlyBalanceTxt;

    @FXML
    private Label messageLabelTxt;

    @FXML
    void enableBusinessFields(ActionEvent event) {
    	if(businessCheckBox.isSelected()) {
    		employerCodTxt.setDisable(false);
        	monthlyBalanceTxt.setDisable(false);
    	}else {
    		employerCodTxt.setDisable(true);
        	monthlyBalanceTxt.setDisable(true);
    	}
    }

    @FXML
    void sendInformation(ActionEvent event) {
    	String id = idTxt.getText(), first = firstNameTxt.getText(), last =LastNameTxt.getText();
    	String email = emailTxt.getText(), phone = phoneNumTxt.getText() , cradit = carditNumTxt.getText();
    	String employerCode = employerCodTxt.getText(),budget = monthlyBalanceTxt.getText();
    	Client client = null;
    	UserType type;
    	if(businessCheckBox.isSelected() &&checkValidInputForBusiness(id,first,last,email,phone,cradit,employerCode,budget)) {
    		type = UserType.CLIENT_BUSINESS;
    		client = new Client(Integer.parseInt(id),first,last,email,phone,
    									Integer.parseInt(cradit),Integer.parseInt(employerCode),Integer.parseInt(budget),type);
    	}
    	else if(checkValidInputForPersonal(id,first,last,email,phone,cradit)) {
    		type = UserType.CLIENT_PERSONAL;
    		client = new Client(Integer.parseInt(id),first,last,email,phone,Integer.parseInt(cradit),-1,-1,type);
    	}
    		ClientUI.clientLogic.sendMessageToServer(client,DataType.CLIENT, RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_CLIENT);
    		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO error handling
				e.printStackTrace();
			}
	    	if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE) {
	    		messageLabelTxt.setText(ClientUI.clientLogic.getLastDataRecieved().toString());
	    		return;
	    	}else //Successfully
	    		messageLabelTxt.setText(ClientUI.clientLogic.getLastDataRecieved().toString());
    			return;
    	
    	}
    

	
	
	@Override
	public void start(Stage stage) {
		Parent root = null;
        try {
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/registerClient.fxml"));
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
	
	  @FXML
	    void doGoBack(ActionEvent event) {
	    	ClientUI.historyStack.popFxController().start(ClientUI.parentWindow);
	    }
	  
	    @FXML
	    void doSignOut(ActionEvent event) {
	    	ClientUI.clientLogic.logOutUser();
	    	ClientUI.loginScreen.start(ClientUI.parentWindow);
	    }
	    
	    private boolean checkValidInputForBusiness(String id, String first ,String last, String email,String phone,String cradit,String employerCode,String monthlyBalance) {
	    	 if (id.trim().isEmpty()) {
	    		 messageLabelTxt.setText("You must enter an Email");
				return false;
	    	 }
	    	 if (first.trim().isEmpty()) {
	    		 messageLabelTxt.setText("must enter all fields");
	    		 return false;
	    	 }
	    	 if (last.trim().isEmpty()) {
	    		 messageLabelTxt.setText("must enter all fields");
	    		 return false;
	    	 }
	    	 if (email.trim().isEmpty()) {
	    		 messageLabelTxt.setText("must enter all fields");
	    		 return false;
	    	 }
	    	 if (phone.trim().isEmpty()) {
	    		 messageLabelTxt.setText("must enter all fields");
	    		 return false;
	    	 }
	    	 if (cradit.trim().isEmpty()) {
	    		 messageLabelTxt.setText("must enter all fields");
	    		 return false;
	    	 }
	    	 if (employerCode.trim().isEmpty() && !employerCodTxt.isDisable()) {
	    		 messageLabelTxt.setText("must enter all fields");
	    		 return false;
	    	 }
	    	 if (monthlyBalance.trim().isEmpty() && !monthlyBalanceTxt.isDisable()) {
	    		 messageLabelTxt.setText("must enter all fields");
	    		 return false;
	    	 }
	    	 return true;
	    }
	    private boolean checkValidInputForPersonal(String id, String first ,String last, String email,String phone,String cradit) {
	    	 if (id.trim().isEmpty()) {
	    		 messageLabelTxt.setText("You must enter an Email");
				return false;
	    	 }
	    	 if (first.trim().isEmpty()) {
	    		 messageLabelTxt.setText("must enter all fields");
	    		 return false;
	    	 }
	    	 if (last.trim().isEmpty()) {
	    		 messageLabelTxt.setText("must enter all fields");
	    		 return false;
	    	 }
	    	 if (email.trim().isEmpty()) {
	    		 messageLabelTxt.setText("must enter all fields");
	    		 return false;
	    	 }
	    	 if (phone.trim().isEmpty()) {
	    		 messageLabelTxt.setText("must enter all fields");
	    		 return false;
	    	 }
	    	 if (cradit.trim().isEmpty()) {
	    		 messageLabelTxt.setText("must enter all fields");
	    		 return false;
	    	 }
	    	 return true;
	    }

}
