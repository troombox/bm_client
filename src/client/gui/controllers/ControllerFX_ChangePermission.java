package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utility.entity.ClientChangePermission;
import utility.entity.User;
import utility.enums.DataType;
import utility.enums.ErrorType;
import utility.enums.RequestType;

/**
 * The Class ControllerFX_ChangePermission.
 * this screen is shown to branch manager account, he can change permissions of users in his branch
 */

public class ControllerFX_ChangePermission implements IClientFxController, Initializable{

	/** The observable client list. */
	ObservableList<ClientChangePermission> observableClient;
	
	/** The selected client list. */
	ObservableList<ClientChangePermission> selectedClient;

	/** The button sign out. */
	@FXML
	private Button buttonSignOut;

	/** The button go back. */
	@FXML
	private Button buttonGoBack;

	/** The first name column. */
	@FXML
	private TableColumn<ClientChangePermission,String> firstName;

	/** The last name column. */
	@FXML
	private TableColumn<ClientChangePermission,String> lastName;

	/** The status column. */
	@FXML
	private TableColumn<ClientChangePermission,String> status;

	/** The branch column. */
	@FXML
	private TableColumn<ClientChangePermission,String> branch;
    
    /** The id column. */
    @FXML
    private TableColumn<ClientChangePermission,String> id;
    
	/** The users table. */
	@FXML
	private TableView<ClientChangePermission> usersTable;

	/** The change btn. */
	@FXML
	private Button changeBtn;

	/** The status cmbobox. */
	@FXML
	private ComboBox<String> statusCmbo;
	
    /** The result label. */
    @FXML
    private Label resultLabel;

	/**
	 * Do change status.
	 *
	 * @param event the event
	 */
	@FXML
	void doChangeStatus(ActionEvent event) {
		String newStatus = statusCmbo.getValue();
		selectedClient = usersTable.getSelectionModel().getSelectedItems();
		if(checkValidInputForBusiness(newStatus,selectedClient)) {
			ClientChangePermission selected = selectedClient.get(0);
			if(selected.getStatus().equals(newStatus)) {
				resultLabel.setText("already with this status");
				return;
			}
			selected.setStatus(newStatus);
			ClientUI.clientLogic.sendMessageToServer(selected, DataType.CHANGE_PERMISSION,
					RequestType.CLIENT_REQUEST_TO_SERVER_CHANGE_PERMISSION);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			resultLabel.setText(ClientUI.clientLogic.getLastDataRecieved().toString());
			populateTable();
		}
		
	}



	/**
	 * Populate table.
	 */
	private void populateTable() {
		User user = ClientUI.clientLogic.getLoggedUser();
		ClientUI.clientLogic.sendMessageToServer(user.getPersonalBranch(), DataType.GET_DATA_OF_CLIENT,
				RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA_CLIENT_INFO);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}if(!(ClientUI.clientLogic.getLastDataRecieved() instanceof ErrorType)) {	
			changeBtn.setDisable(false);
			ArrayList<ClientChangePermission> recievedData = (ArrayList<ClientChangePermission>) ClientUI.clientLogic.getLastDataRecieved();
			usersTable.getItems().setAll(recievedData);
		}else {
			usersTable.getItems().clear();
			changeBtn.setDisable(true);
			resultLabel.setText(ClientUI.clientLogic.getLastDataRecieved().toString());
		}
	}


	/**
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String[] categoryNames = {"frozen","active","unregistered"};
		statusCmbo.getItems().addAll(categoryNames);
		firstName.setCellValueFactory(new PropertyValueFactory<ClientChangePermission, String>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<ClientChangePermission, String>("lastName"));
		status.setCellValueFactory(new PropertyValueFactory<ClientChangePermission, String>("status"));
		branch.setCellValueFactory(new PropertyValueFactory<ClientChangePermission, String>("branch"));
		id.setCellValueFactory(new PropertyValueFactory<ClientChangePermission, String>("id"));
		populateTable();
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

			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/ChangePermisssionBranchManager.fxml"));
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
	 * Check valid input for business. user ,ust select a row before pressing the change premission btn
	 *
	 * @param newStatus the new status
	 * @param selectedClient the selected client
	 * @return true, if successful
	 */
	private boolean checkValidInputForBusiness(String newStatus,ObservableList<ClientChangePermission> selectedClient) {
		if (newStatus == null) {
			resultLabel.setText("You must select new status");
			return false;
   	 	}
		if(selectedClient.isEmpty()){
			resultLabel.setText("You must select a row");
			return false;
		}
		return true;
    }

}
