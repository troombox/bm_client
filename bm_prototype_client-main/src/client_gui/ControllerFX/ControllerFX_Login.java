package client_gui.ControllerFX;
import client_gui.ClientUI;
import client_gui.OrderDetailsFrameController;

import java.io.IOException;
import java.util.ArrayList;

import entity.Order;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utility.DataType;
import utility.RequestType;
import utility.UserType;

public class ControllerFX_Login {

    @FXML
    private TextField Useremail;

    @FXML
    private TextField Password;

    @FXML
    private Button loginbutton;
    
    @FXML
    private Label ErrorMsg;

    @FXML
    void loginButtonPressed(ActionEvent event) throws IOException {
    	String userEmail = Useremail.getText();
    	String password = Password.getText();
    	
    	FXMLLoader loader = new FXMLLoader();
    	
    	if(checkValidInput(userEmail,password)) {
    		
    		User user = new User(-1, "", "", "", userEmail, "", UserType.USER,"", "", false,password);
    		ClientUI.clientLogic.sendMessageToServer(user, DataType.USER, RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA);
 
	    	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO error handling
				e.printStackTrace();
			}
	    	if (ClientUI.clientLogic.getLastDataTypeRecieved() == DataType.ERROR_MESSAGE) {
				// TODO: TYPE OF ERROR
				ErrorMsg.setText((String) ClientUI.clientLogic.getLastDataRecieved());
				} else {
					//on successful data delivery - open new window
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
					Stage primaryStage = new Stage();
					Object recievedData = ClientUI.clientLogic.getLastDataRecieved();
					Pane root = openWantedWindow(loader,(String)((ArrayList)recievedData).get(8));
					
					//what does OrderDetailsFrameController do ??
					OrderDetailsFrameController orderDetailsFrameController = loader.getController();
					orderDetailsFrameController.loadOrder((Order) ClientUI.clientLogic.getLastDataRecieved());
					Scene scene = new Scene(root);
//					scene.getStylesheets().add(getClass().getResource("/gui_/OrderDetailsFrame.css").toExternalForm());
					primaryStage.setTitle("Show Order");
					primaryStage.setScene(scene);
					primaryStage.show();
				}
    	}

    }
    
    private Pane openWantedWindow(FXMLLoader loader, String userTypeString) throws IOException {
    	Pane root = null;
    	UserType userType = UserType.valueOf(userTypeString);
    	if(userType == UserType.CLIENT_PERSONAL || userType == UserType.CLIENT_BUSINESS) {
    		root = loader.load(getClass().getResource("/client_gui/fxml_files/restaurants.fxml").openStream());
    		ControllerFX_Restaurant controllerFX_Restaurant = loader.getController();
    		// TODO: create function in ControllerFX_Restaurant to get data from BD about the restaurants
    	}
    	if(userType == UserType.HR_MANAGER)
    		root = loader.load(getClass().getResource("/client_gui/fxml_files/HRpage.fxml").openStream());
    	if(userType == UserType.BM_BRANCH_MANAGER)
    		root = loader.load(getClass().getResource("/client_gui/fxml_files/openBranchManagerPage.fxml").openStream());
    	if(userType == UserType.BM_CEO)
    		root = loader.load(getClass().getResource("/client_gui/fxml_files/CEOopenPage.fxml").openStream());
    	if(userType == UserType.RESTAURANT_OWNER || userType == UserType.RESTAURANT_OWNER)
    		root = loader.load(getClass().getResource("/client_gui/fxml_files/openPageSupplier.fxml").openStream());
		return root;
    	
    }
    
    private boolean checkValidInput(String userEmail, String password) {
    	 if (userEmail.trim().isEmpty()) {
			ErrorMsg.setText("You must enter an Email");
			return false;
    	 }
    	
    	  if (password.trim().isEmpty()) {
			ErrorMsg.setText("You must enter a password");
			return false;
    	  }
    	  return true;
    }

}
