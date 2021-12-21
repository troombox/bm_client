package client.gui.controllers;

import java.io.File;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utility.entity.Supplier;
import utility.enums.DataType;
import utility.enums.RequestType;

public class ControllerFX_RegisterSupplierScreen implements IClientFxController, Initializable {
	
    final FileChooser fileChooser = new FileChooser();
    
    File file = null;

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
    private TextField txtFieldFilePath;

    @FXML
    private ComboBox<String> categoryCmbo;
    
    @FXML
    private TextField workerID;
    
    @FXML
    void chooseCategoryInCmbo(ActionEvent event) {

    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	String[] categoryNames = {"Italian","Dessert","Fast Food","Coffee","Asian","Meat","Indian","Hummus"};
    	categoryCmbo.getItems().addAll(categoryNames);
    }
  
    
    @FXML
    void doOpenFile(ActionEvent event) {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
        file = fileChooser.showOpenDialog(ClientUI.parentWindow);
        if (file != null) {
        	txtFieldFilePath.setText(file.getAbsolutePath());
        }
    }
    

    @FXML
    void sendInformation(ActionEvent event) {
    	String personalBranch = ClientUI.clientLogic.getLoggedUser().getPersonalBranch();
    	Supplier supplier = null;
    	if(!checkValidInputForBusiness(resNameTxt.getText(),categoryCmbo.getValue(),workerID.getText())) {
    		return;
    	}
    	supplier = new Supplier(resNameTxt.getText(),categoryCmbo.getValue(), personalBranch,workerID.getText());
    	ClientUI.clientLogic.sendMessageToServer(supplier,DataType.SUPPLIER, RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_SUPPLIER);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE) {
    		resultTxt.setText(ClientUI.clientLogic.getLastDataRecieved().toString());
    		return;
    	}else {
    		resultTxt.setText(ClientUI.clientLogic.getLastDataRecieved().toString());
			return;
    	}
    	//if we succeeded with the registration (i.e. no error message) we proceed to upload the image (if exists)
//    	if(file == null) {
//    		return;
//    	}
//    	ClientUI.clientLogic.sendMessageToServer(file.getName(),DataType.SINGLE_TEXT_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_INCOMING_FILE);
//    	ClientUI.clientLogic.sendImageToServer(FileWrapper image);
    }
    
    private boolean checkValidInputForBusiness(String restaurantName,String categoryCmbo, String workerID) {
		if (restaurantName.trim().isEmpty()) {
			resultTxt.setText("You must enter a restaurant name");
			return false;
   	 	}
		if(categoryCmbo.trim().isEmpty()) {
			resultTxt.setText("You must enter a restaurant name");
			return false;
		}
		if(workerID.trim().isEmpty()) {
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
	    	ClientUI.historyStack.clearControllerHistory();
	    	ClientUI.loginScreen.start(ClientUI.parentWindow);
	    }

}
