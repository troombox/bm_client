package client.gui.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import utility.entity.Supplier;
import utility.enums.DataType;
import utility.enums.RequestType;

/**
 * The Class ControllerFX_RegisterSupplierScreen.
 * this screen is shown to branch manager, it can register supplier (own a restaurant) to his branch
 */
public class ControllerFX_RegisterSupplierScreen implements IClientFxController, Initializable {
	
    /** The file chooser. */
    final FileChooser fileChooser = new FileChooser();
    
    /** The file. */
    File file = null;

    /** The register btn. */
    @FXML
    private Button registerBtn;
	
    /** The signout btn. */
    @FXML
    private Button signoutBtn;

    /** The back btn. */
    @FXML
    private Button backBtn;

    /** The res name txt. */
    @FXML
    private TextField resNameTxt;

    /** The button open image. */
    @FXML
    private Button buttonOpenImage;
    
//    @FXML
//    private Label resultTxt;
    
    /** The txt field file path. */
@FXML
    private TextField txtFieldFilePath;

    /** The category cmbo. */
    @FXML
    private ComboBox<String> categoryCmbo;
    
    /** The worker ID. */
    @FXML
    private TextField workerID;
    
    /** The success message pane. */
    @FXML
    private Pane successMessagePane;

    /** The Error msg. */
    @FXML
    private Label ErrorMsg;
    
    /**
     * Choose category in cmbo.
     *
     * @param event the event
     */
    @FXML
    void chooseCategoryInCmbo(ActionEvent event) {
    	/*
    	 * will be implemented in next version 
    	 */
    }
    
    /**
     * Initialize.
     *
     * @param location the location
     * @param resources the resources
     */
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	String[] categoryNames = {"Italian","Dessert","Fast Food","Coffee","Asian","Meat","Indian","Hummus"};
    	categoryCmbo.getItems().addAll(categoryNames);
    }
  
    
    /**
     * Do open file.
     *
     * @param event the event
     */
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
    

    /**
     * Send information. register the supplier to the branch in biteme 
     *
     * @param event the event
     */
    @FXML
    void sendInformation(ActionEvent event) {
    	ErrorMsg.setVisible(false);
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
    		ErrorMsg.setVisible(true);
    		ErrorMsg.setText(ClientUI.clientLogic.getLastDataRecieved().toString());
    		return;
    	}
    	
		successMessagePane.setVisible(true);
		PauseTransition delay = new PauseTransition(Duration.seconds(2));
		delay.setOnFinished(e -> successMessagePane.setVisible(false));
		delay.play();
		return;
		
    	//if we succeeded with the registration (i.e. no error message) we proceed to upload the image (if exists)
//    	if(file == null) {
//    		return;
//    	}
//    	ClientUI.clientLogic.sendMessageToServer(file.getName(),DataType.SINGLE_TEXT_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_INCOMING_FILE);
//    	ClientUI.clientLogic.sendImageToServer(FileWrapper image);
    }
    
    /**
     * Check valid input for business all fields are full 
     *
     * @param restaurantName the restaurant name
     * @param categoryCmbo the category cmbo
     * @param workerID the worker ID
     * @return true, if successful
     */
    private boolean checkValidInputForBusiness(String restaurantName,String categoryCmbo, String workerID) {
    	ErrorMsg.setVisible(false);
		if (restaurantName.trim().isEmpty()) {
			ErrorMsg.setVisible(true);
			ErrorMsg.setText("You must enter a restaurant name");
			return false;
   	 	}
		if(categoryCmbo == null) {
			ErrorMsg.setVisible(true);
			ErrorMsg.setText("You must enter a category");
			return false;
		}
		if(workerID.trim().isEmpty()) {
			ErrorMsg.setVisible(true);
			ErrorMsg.setText("You must enter a worker id");
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

}
