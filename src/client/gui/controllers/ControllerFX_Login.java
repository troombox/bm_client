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
import utility.enums.ErrorType;
import utility.enums.RequestType;
import utility.enums.UserType;

/**
 * The Class ControllerFX_Login.
 * this is the first screen that present to every user that is in biteme. the user login to biteme according to his user type
 */
public class ControllerFX_Login implements IClientFxController {

    /** The User email. */
    @FXML
    private TextField Useremail;

    /** The Password. */
    @FXML
    private TextField Password;

    /** The login button. */
    @FXML
    private Button loginbutton;
    
    /** The Error msg. */
    @FXML
    private Label ErrorMsg;

    /**
     * Login button pressed. move to the next screen according to the user type that is logging in 
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void loginButtonPressed(ActionEvent event) throws IOException {
    	String userEmail = Useremail.getText();
    	String password = Password.getText();
    	if(checkValidInput(userEmail,password)) {
    		User user = new User(-1, "", "", "", userEmail, "", UserType.USER,"", "", false,password);
    		ClientUI.clientLogic.sendMessageToServer(user, DataType.USER, RequestType.CLIENT_REQUEST_TO_SERVER_LOGIN_REQUEST);
	    	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO error handling
				e.printStackTrace();
			}
	    	if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE) {
	    		ErrorMsg.setVisible(true);
	    		String errorString = ClientUI.clientLogic.getLastDataRecieved().toString();
	    		switch(errorString) {
		    		case "INVALID_CREDENTIALS_WRONG_PASSWORD":
		    			errorString = "Wrong password. Please try again";
		    			break;
		    		case "INVALID_CREDENTIALS_USER_NOT_FOUND":
		    			errorString = "Account not found. Please try again";
		    			break;
		    		case "INVALID_CREDENTIALS_USER_ALREADY_LOGGED_IN":
		    			errorString = "You already logged in";
		    			break;
		    		case "USER_ACCOUNT_IS_FROZEN":
		    			errorString = "user acount is frozen";
	                    break;
	                case "USER_IS_UNREGISTERED":
	                	errorString = "user is unregistered";
	                    break;
		    		default:
		    			errorString = ErrorType.UNKNOWN.toString();

	    		}
	    		ErrorMsg.setText(errorString);
	    		return;
	    		}
	    	if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.USER) {
	    		//something went *very* wrong!
	    		return;
	    	}
	    	User userDataFromServer = (User)ClientUI.clientLogic.getLastDataRecieved();
	    	ClientUI.clientLogic.loginUser(userDataFromServer);
	    	openWantedWindow(userDataFromServer.getUserType());
	    	}
    }
    
    /**
     * Open wanted window - according to user type
     *
     * @param userType the user type
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void openWantedWindow(UserType userType) throws IOException {
    	IClientFxController nextScreen = new TempScreenControllerFx();
    	if(userType == UserType.CLIENT_PERSONAL || userType == UserType.CLIENT_BUSINESS) {
    		nextScreen = new ControllerFX_ClientW4Cscreen();
    		ClientUI.historyStack.pushFxController(this);
    		nextScreen.start(ClientUI.parentWindow);
    	}
    	if(userType == UserType.HR_MANAGER) {
    		nextScreen = new ControllerFX_HRApproveBusiness();
            ClientUI.historyStack.pushFxController(this);
            nextScreen.start(ClientUI.parentWindow);
    	}
    	if(userType == UserType.BM_BRANCH_MANAGER) {
            nextScreen = new ControllerFX_BranchManagerScreen();
            ClientUI.historyStack.pushFxController(this);
            nextScreen.start(ClientUI.parentWindow);
    	}
    	if(userType == UserType.BM_CEO) {
    		 nextScreen = new ControllerFX_CEOScreen();
             ClientUI.historyStack.pushFxController(this);
             nextScreen.start(ClientUI.parentWindow);
    	}
    	if(userType == UserType.RESTAURANT_OWNER) {
            nextScreen = new ControllerFX_SupplierScreen();
            ClientUI.historyStack.pushFxController(this);
            nextScreen.start(ClientUI.parentWindow);
        }
    		
    }
    
    /**
     * Check valid input. - all fields must be full
     *
     * @param userEmail the user email
     * @param password the password
     * @return true, if successful
     */
    private boolean checkValidInput(String userEmail, String password) {
    	 if (userEmail.trim().isEmpty()) {
    		 ErrorMsg.setVisible(true);
			ErrorMsg.setText("You must enter an Email");
			return false;
    	 }
    	 if (password.trim().isEmpty()) {
    		 ErrorMsg.setVisible(true);
    		 ErrorMsg.setText("You must enter a password");
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
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/Login.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
//      scene.getStylesheets().add(getClass().getResource("/client/client_gui/LoginFxml.css").toExternalForm());
        stage.setTitle("User Login");
        stage.setScene(scene);
        stage.show();
	}

}
