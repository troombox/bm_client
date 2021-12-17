package client.gui.controllers;

import java.io.File;

import java.io.IOException;
import java.awt.Desktop;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utility.entity.Supplier;
import utility.enums.DataType;
import utility.enums.RequestType;

public class ControllerFX_RegisterSupplyerScreen implements IClientFxController {
	
    final FileChooser fileChooser = new FileChooser();
    private Desktop desktop = Desktop.getDesktop();

    @FXML
    private Button registerBtn;
	
    @FXML
    private Button signoutBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TextField resNameTxt;

    @FXML
    private Button buttonOpenImage;
    
    @FXML
    private Label resultTxt;

  
    
    @FXML
    void doOpenFile(ActionEvent event) {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
        File file = fileChooser.showOpenDialog(ClientUI.parentWindow);
        if (file != null) {
        	 try {
                 desktop.open(file);
             } catch (IOException ex) {
            	 ex.printStackTrace();
             }
        }
    }
    

    @FXML
    void sendInformation(ActionEvent event) {
    	String personalBranch = ClientUI.clientLogic.getLoggedUser().getPersonalBranch();
    	Supplier supplier = null;
    	if(checkValidInputForBusiness(resNameTxt.getText())) {
    		supplier = new Supplier(resNameTxt.getText(), personalBranch);	
    	}
    	ClientUI.clientLogic.sendMessageToServer(supplier,DataType.SUPPLIER, RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_SUPPLIER);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO error handling
			e.printStackTrace();
		}
    	if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE) {
    		resultTxt.setText(ClientUI.clientLogic.getLastDataRecieved().toString());
    		return;
    	}else //Successfully
    		resultTxt.setText(ClientUI.clientLogic.getLastDataRecieved().toString());
			return;
    }
    
    private boolean checkValidInputForBusiness(String restaurantName) {
		if (restaurantName.trim().isEmpty()) {
			resultTxt.setText("You must enter a restaurant name");
			return false;
   	 	}
		return true;
    }

	@Override
	public void start(Stage stage) {
		Parent root = null;
        try {
        	
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/registerSupplier.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
//      scene.getStylesheets().add(getClass().getResource("/client/client_gui/LoginFxml.css").toExternalForm());
        stage.setTitle("Register New Supplyer");
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

}
