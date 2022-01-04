package client.gui.controllers;

import java.io.IOException;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import client.interfaces.IQRReader;
import client.utility.qr.QRReader;
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

/**
 * The Class ControllerFX_ClientW4Cscreen - used as a controller for JavaFX,
 *  implementing code needed for the W4C screen in the application
 */
public class ControllerFX_ClientW4Cscreen implements IClientFxController {
	
	/** The previous screen (should be login screen controller) */
	IClientFxController previous;
	
	/** The QR reader, should be initialized to an */
	IQRReader reader;
	
    /** The button sign out. */
    @FXML
    private Button buttonSignOut;

    /** The button go back. */
    @FXML
    private Button buttonGoBack;

    /** The button continue. */
    @FXML
    private Button buttonContinue;

    /** The button scan QR code. */
    @FXML
    private Button buttonScanQRCode;
    
    /** The text box W 4 C code. */
    @FXML
    private TextField textBoxW4CCode;
    
    /** The Error msg. */
    @FXML
    private Label ErrorMsg;

    /**
     * Do continue.
     *
     * @param event the event
     */
    @FXML
    void doContinue(ActionEvent event) {
    	String w4cText = textBoxW4CCode.getText();
    	if(!checkW4CInputText(w4cText)) {
    		return;
    	}
    	//System.out.println(ClientUI.clientLogic.getLoggedUser().toString());
    	IClientFxController nextScreen = new ControllerFX_CategoriesScreen();
    	nextScreen.start(ClientUI.parentWindow);
    }

    /**
     * Do go back.
     *
     * @param event the event
     */
    @FXML
    void doGoBack(ActionEvent event) {
    	ClientUI.clientLogic.logOutUser();
    	ClientUI.historyStack.popFxController().start(ClientUI.parentWindow);
    }

    /**
     * Do scan QR code.
     *
     * @param event the event
     */
    @FXML
    void doScanQRCode(ActionEvent event) {
//    	Alert alert = new Alert(AlertType.INFORMATION);
//    	alert.setTitle("Not Yet Implemented");
//    	alert.setHeaderText(null);
//    	alert.setContentText("This functionality is not implemented yet.\nSorry.");
//    	alert.showAndWait();
    	reader = new QRReader();
    	textBoxW4CCode.setText(reader.getQRCode());
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
	 * Start.
	 *
	 * @param stage the stage
	 */
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
        stage.setTitle("W4C Login");
        stage.setScene(scene);
        stage.show();
        
	}
	
	/**
	 * Check W 4 C input text.
	 *
	 * @param w4cInput the w 4 c input
	 * @return true, if successful
	 */
	private boolean checkW4CInputText(String w4cInput) {
		if((w4cInput.trim().isEmpty())){
			ErrorMsg.setVisible(true);
			ErrorMsg.setText("You must enter a code");
			return false;
		}
		if(!ClientUI.clientLogic.getLoggedUser().getW4c().equals(w4cInput)) {
			ErrorMsg.setVisible(true);
			ErrorMsg.setText("The code does't match your user");
			return false;
		}
		ClientUI.clientLogic.sendMessageToServer(ClientUI.clientLogic.getLoggedUser(),
				DataType.USER, RequestType.CLIENT_REQUEST_TO_SERVER_W4C_REQUEST);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.USER) {
			System.out.println("Houston, we got a problem!");
			return false;
		}
		User user = (User)ClientUI.clientLogic.getLastDataRecieved();
		ClientUI.clientLogic.getLoggedUser().setPersonalCode(user.getPersonalCode());
		ClientUI.clientLogic.getLoggedUser().setBuisnessCode(user.getBuisnessCode());
		return true;
	}

}
