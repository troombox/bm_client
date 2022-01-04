package client.gui.controllers;

import java.io.IOException;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import utility.entity.Client;
import utility.enums.DataType;
import utility.enums.RequestType;
import utility.enums.UserType;

/**
 * The Class ControllerFX_RegisterClientScreen.
 * this screen is shown is branch manager, he can register a client to his branch according to the imported users 
 */
public class ControllerFX_RegisterClientScreen implements IClientFxController {

	/** The signout btn. */
	@FXML
	private Button signoutBtn;

	/** The back btn. */
	@FXML
	private Button backBtn;
	
	/** The first name txt. */
	@FXML
	private TextField firstNameTxt;

	/** The Last name txt. */
	@FXML
	private TextField LastNameTxt;

	/** The phone num txt. */
	@FXML
	private TextField phoneNumTxt;

	/** The email txt. */
	@FXML
	private TextField emailTxt;

	/** The id txt. */
	@FXML
	private TextField idTxt;

	/** The cardit num txt. */
	@FXML
	private TextField carditNumTxt;

	/** The send btn. */
	@FXML
	private Button sendBtn;

	/** The business check box. */
	@FXML
	private CheckBox businessCheckBox;

	/** The employer cod txt. */
	@FXML
	private TextField employerCodTxt;

	/** The monthly balance txt. */
	@FXML
	private TextField monthlyBalanceTxt;

	/** The message label txt. */
	@FXML
	private Label messageLabelTxt;

	/** The success message pane. */
	@FXML
	private Pane successMessagePane;

	/** The Error msg. */
	@FXML
	private Label ErrorMsg;

	/**
	 * Enable business fields according checked checkbox of business client 
	 *
	 * @param event the event
	 */
	@FXML
	void enableBusinessFields(ActionEvent event) {
		if (businessCheckBox.isSelected()) {
			employerCodTxt.setDisable(false);
			monthlyBalanceTxt.setDisable(false);
		} else {
			employerCodTxt.setDisable(true);
			monthlyBalanceTxt.setDisable(true);
		}
	}

	/**
	 * Send information. register the client to his branch. 
	 *
	 * @param event the event
	 */
	@FXML
	void sendInformation(ActionEvent event) {
		ErrorMsg.setVisible(false);
		if (!checkInputFilledData()) {
			ErrorMsg.setVisible(true);
			ErrorMsg.setText("all fields must be filled!");
			return;
		}
		if (!checkInputNumericalData()) {
			return;
		}
		String id = idTxt.getText(), first = firstNameTxt.getText(), last = LastNameTxt.getText();
		String email = emailTxt.getText(), phone = phoneNumTxt.getText(), cradit = carditNumTxt.getText();
		String employerCode = employerCodTxt.getText(), budget = monthlyBalanceTxt.getText();
		Client client = null;
		String personalBranch = ClientUI.clientLogic.getLoggedUser().getPersonalBranch();
		boolean flag = false;
		UserType type;
		if (businessCheckBox.isSelected()
				&& checkValidInputForBusiness(id, first, last, email, phone, cradit, employerCode, budget)) {
			flag = true;
			type = UserType.CLIENT_BUSINESS;
			client = new Client(Integer.parseInt(id), first, last, personalBranch, email, phone,
					Integer.parseInt(cradit), Integer.parseInt(employerCode), Integer.parseInt(budget), type);
		} else if (checkValidInputForPersonal(id, first, last, email, phone, cradit)) {
			flag = true;
			type = UserType.CLIENT_PERSONAL;
			client = new Client(Integer.parseInt(id), first, last, personalBranch, email, phone,
					Integer.parseInt(cradit), -1, -1, type);
		} else if (!flag) {
			return;
		}
		ClientUI.clientLogic.sendMessageToServer(client, DataType.CLIENT,
				RequestType.CLIENT_REQUEST_TO_SERVER_REGISTER_CLIENT);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO error handling
			e.printStackTrace();
		}
		if (ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE) {
			System.out.println("");
			ErrorMsg.setVisible(true);
			ErrorMsg.setText("error: incorrect data provided");
			return;
		}
		// Successfully
		ErrorMsg.setVisible(false);
		successMessagePane.setVisible(true);
		PauseTransition delay = new PauseTransition(Duration.seconds(2));
		delay.setOnFinished(e -> successMessagePane.setVisible(false));
		delay.play();
		return;
	}

	/**
	 * Check all input fields are full 
	 *
	 * @return true, if successful
	 */
	private boolean checkInputFilledData() {
		if (firstNameTxt.getText().equals(""))
			return false;
		if (LastNameTxt.getText().equals(""))
			return false;
		if (phoneNumTxt.getText().equals(""))
			return false;
		if (emailTxt.getText().equals(""))
			return false;
		if (idTxt.getText().equals(""))
			return false;
		if (carditNumTxt.getText().equals(""))
			return false;
		if (businessCheckBox.isSelected()) {
			if (employerCodTxt.getText().equals(""))
				return false;
			if (monthlyBalanceTxt.getText().equals(""))
				return false;
		}
		return true;
	}
	
	/**
	 * Check input numerical data of relevant numerical fields 
	 *
	 * @return true, if successful
	 */
	private boolean checkInputNumericalData() {
		if (!isNumeric(idTxt.getText())) {
			ErrorMsg.setVisible(true);
			ErrorMsg.setText("ID must be number!");
			return false;
		}
		if (!isNumeric(carditNumTxt.getText())) {
			ErrorMsg.setVisible(true);
			ErrorMsg.setText("card number must be number!");
			return false;
		}
		if (businessCheckBox.isSelected()) {
			if (!isNumeric(employerCodTxt.getText())) {
				ErrorMsg.setVisible(true);
				ErrorMsg.setText("employer Code must be number!");
				return false;
			}
			if (!isNumeric(monthlyBalanceTxt.getText())) {
				ErrorMsg.setVisible(true);
				ErrorMsg.setText("balance must be number!");
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if given txt is numeric.
	 *
	 * @param str the str
	 * @return true, if is numeric
	 */
	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
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
	 * Check valid input for business client. all fields of the user must suit to the user that was imported - in DB
	 *
	 * @param id the id
	 * @param first the first
	 * @param last the last
	 * @param email the email
	 * @param phone the phone
	 * @param cradit the cradit
	 * @param employerCode the employer code
	 * @param monthlyBalance the monthly balance
	 * @return true, if successful
	 */
	private boolean checkValidInputForBusiness(String id, String first, String last, String email, String phone,
			String cradit, String employerCode, String monthlyBalance) {
		if (id.trim().isEmpty()) {
			messageLabelTxt.setText("must enter all fields");
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

	/**
	 * Check valid input for personal client. all fields of the user must suit to the user that was imported - in DB
	 *
	 * @param id the id
	 * @param first the first
	 * @param last the last
	 * @param email the email
	 * @param phone the phone
	 * @param cradit the cradit
	 * @return true, if successful
	 */
	private boolean checkValidInputForPersonal(String id, String first, String last, String email, String phone,
			String cradit) {
		if (id.trim().isEmpty()) {
			messageLabelTxt.setText("must enter all fields");
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
