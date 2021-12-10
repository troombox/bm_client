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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerFX_ClientW4Cscreen implements IClientFxController {
	
	IClientFxController previous;
	
    @FXML
    private Button buttonSignOut;

    @FXML
    private Button buttonGoBack;

    @FXML
    private Button buttonContinue;

    @FXML
    private Button buttonScanQRCode;
    
    @FXML
    private TextField textBoxW4CCode;
    
    @FXML
    private Label ErrorMsg;

    @FXML
    void doContinue(ActionEvent event) {
    	String w4cText = textBoxW4CCode.getText();
    	if(!checkW4CInputText(w4cText)) {
    		return;
    	}
    	IClientFxController newScreen = new TempScreenControllerFx();
    	newScreen.start(ClientUI.parentWindow);
    }

    @FXML
    void doGoBack(ActionEvent event) {
    	ClientUI.historyStack.popFxController().start(ClientUI.parentWindow);
    }

    @FXML
    void doScanQRCode(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Not Yet Implemented");
    	alert.setHeaderText(null);
    	alert.setContentText("This functionality is not implemented yet.\nSorry.");
    	alert.showAndWait();
    }

    @FXML
    void doSignOut(ActionEvent event) {
    	ClientUI.clientLogic.logOutUser();
    	ClientUI.loginScreen.start(ClientUI.parentWindow);
    }

	@Override
	public void start(Stage stage) {
        Parent root = null;
        try {
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/ClientW4Cscreen.fxml"));
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
	
	private boolean checkW4CInputText(String w4cInput) {
		if((w4cInput.trim().isEmpty())){
			ErrorMsg.setText("Error: no code entered");
			return false;
		}
		if(ClientUI.clientLogic.getLoggedUser().getW4c().equals(w4cInput)) {
			return true;
		}
		ErrorMsg.setText("Error: the code does't match user");
		return false;
	}

}
