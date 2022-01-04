package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import utility.entity.Business;
import utility.entity.User;
import utility.enums.DataType;
import utility.enums.RequestType;

/**
 * The Class ControllerFX_ApproveBusinesses.
 * this screen is shown in branch manager account, the manager can approve businesses that are waiting for approval
 */
public class ControllerFX_ApproveBusinesses  implements  IClientFxController, Initializable{
	
	/** The selected client. */
	ObservableList<String> selectedClient;

    /** The button sign out. */
    @FXML
    private Button buttonSignOut;

    /** The button go back. */
    @FXML
    private Button buttonGoBack;

    /** The Approve btn. */
    @FXML
    private Button ApproveBtn;

    /** The Decline btn. */
    @FXML
    private Button DeclineBtn;


    /** The list view. */
    @FXML
    private ListView<String> listView;
    
    /** The result label. */
    @FXML
    private Label resultLabel;

    

    /**
     * Approve request of business.
     *
     * @param event the event
     */
    @FXML
    void aproveRequest(ActionEvent event) {
    	User user = ClientUI.clientLogic.getLoggedUser();
    	ObservableList<String> item  = listView.getSelectionModel().getSelectedItems();
    	if(item.isEmpty()) {
    		resultLabel.setText("you must choose business");
    		return;
    	}
    	String selected = item.get(0);
    	Business business = new Business(selected,2,user.getPersonalBranch());
    	ClientUI.clientLogic.sendMessageToServer(business, DataType.APPROVE_BUSINESS,
				RequestType.CLIENT_REQUEST_TO_SERVER_APPROVE_BUSINESS);
    	
    	if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE) {
    		resultLabel.setText(ClientUI.clientLogic.getLastDataRecieved().toString());
    		
    	}else{
    		resultLabel.setText(RequestType.SERVER_MESSAGE_TO_CLIENT_APPROVE_BUSINESS_SUCCESS.toString());
    	}
    	populateTable();
    }

    /**
     * Decline request of business.
     *
     * @param event the event
     */
    @FXML
    void declineRequest(ActionEvent event) {
    	User user = ClientUI.clientLogic.getLoggedUser();
    	ObservableList<String> item  = listView.getSelectionModel().getSelectedItems();
    	if(item.isEmpty()) {
    		resultLabel.setText("you must choose business");
    		return;
    	}
    	String selected = item.get(0);
    	Business business = new Business(selected,0,user.getPersonalBranch());
    	ClientUI.clientLogic.sendMessageToServer(business, DataType.APPROVE_BUSINESS,
				RequestType.CLIENT_REQUEST_TO_SERVER_APPROVE_BUSINESS);
    	
    	if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE) {
    		resultLabel.setText(ClientUI.clientLogic.getLastDataRecieved().toString());
    		
    	}else{
    		resultLabel.setText(RequestType.SERVER_MESSAGE_TO_CLIENT_DECLINE_BUSINESS_SUCCESS.toString());
    	}
    	populateTable();
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
     * Populate table. insert values to business to approve table
     */
    private void populateTable() {
		User user = ClientUI.clientLogic.getLoggedUser();
		ClientUI.clientLogic.sendMessageToServer(user.getPersonalBranch(), DataType.GET_DATA_OF_BUSINESS,
				RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA_BUSINESSES_NAMES);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		ArrayList<String> recievedData = (ArrayList<String>) ClientUI.clientLogic.getLastDataRecieved();
		List<String> recievedData1 = recievedData.subList(2, recievedData.size());
		listView.getItems().setAll(recievedData1);
		if(recievedData.size() == 2) { //if there is nothing in the list, disable the btn
			ApproveBtn.setDisable(true);
			DeclineBtn.setDisable(true);
		}else {
			ApproveBtn.setDisable(false);
			DeclineBtn.setDisable(false);
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
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/ApproveBusinessesBranchManager.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
        stage.setTitle("Approve Bussinesses");
        stage.setScene(scene);
        stage.show();
		
	}

}
