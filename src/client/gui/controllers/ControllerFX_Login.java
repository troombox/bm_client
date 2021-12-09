package client.gui.controllers;
import java.io.IOException;

import client.debug.TempScreenControllerFx;
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
import utility.entity.User;
import utility.enums.DataType;
import utility.enums.RequestType;
import utility.enums.UserType;

public class ControllerFX_Login implements IClientFxController {

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
    	if(checkValidInput(userEmail,password)) {
    		
    		User user = new User(-1, "", "", "", userEmail, "", UserType.USER,"", "", false,password);
    		ClientUI.clientLogic.sendMessageToServer(user, DataType.USER, RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA);
 
	    	try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO error handling
				e.printStackTrace();
			}
//	    	if (ClientUI.clientLogic.getLastDataTypeRecieved() == DataType.ERROR_MESSAGE) {
//				// TODO: TYPE OF ERROR
//				ErrorMsg.setText((String) ClientUI.clientLogic.getLastDataRecieved());
//				} else {
//					//on successful data delivery - open new window
//					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
//					Stage primaryStage = new Stage();
//					Object recievedData = ClientUI.clientLogic.getLastDataRecieved();
//					Pane root = openWantedWindow(loader,(String)((ArrayList)recievedData).get(8));
//					
//					//what does OrderDetailsFrameController do ??
//					OrderDetailsFrameController orderDetailsFrameController = loader.getController();
//					orderDetailsFrameController.loadOrder((Order) ClientUI.clientLogic.getLastDataRecieved());
//					Scene scene = new Scene(root);
////					scene.getStylesheets().add(getClass().getResource("/gui_/OrderDetailsFrame.css").toExternalForm());
//					primaryStage.setTitle("Show Order");
//					primaryStage.setScene(scene);
//					primaryStage.show();
//				}
    	}

    }
    
    private void openWantedWindow(UserType userType) throws IOException {
    	IClientFxController nextScreen = new TempScreenControllerFx();
    	if(userType == UserType.CLIENT_PERSONAL || userType == UserType.CLIENT_BUSINESS) {
    		nextScreen.start(ClientUI.parentWindow);
    	}
    	if(userType == UserType.HR_MANAGER)
    		nextScreen.start(ClientUI.parentWindow);
    	if(userType == UserType.BM_BRANCH_MANAGER)
    		nextScreen.start(ClientUI.parentWindow);
    	if(userType == UserType.BM_CEO)
    		nextScreen.start(ClientUI.parentWindow);
    	if(userType == UserType.RESTAURANT_OWNER || userType == UserType.RESTAURANT_OWNER)
    		nextScreen.start(ClientUI.parentWindow);
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

	@Override
	public void start(Stage stage) {
        Parent root = null;
        try {
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/Login.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
//      scene.getStylesheets().add(getClass().getResource("/client/client_gui/LoginFxml.css").toExternalForm());
        stage.setTitle("User Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
	}

}
