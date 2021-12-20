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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utility.entity.BusinessClient;
import utility.entity.User;
import utility.enums.DataType;
import utility.enums.RequestType;

public class ControllerFX_HRScreen implements IClientFxController, Initializable {
	
	IClientFxController previous;
	ObservableList<BusinessClient> observableList;

    @FXML
    private Button buttonSignOut;

    @FXML
    private Button buttonGoBack;

    @FXML
    private Button ApproveBtn;

    @FXML
    private Button DeclineBtn;
   
    @FXML
    private TableView<BusinessClient> Table;
    
    @FXML
    private TableColumn<BusinessClient, String> workerID;

    @FXML
    private TableColumn<BusinessClient, String> workerName;

    @FXML
    private TableColumn<BusinessClient, String> workerPhone;

    @FXML
    private TableColumn<BusinessClient, String> workerEmail;
    
    @FXML
    private Button ButtonTest;

    @FXML
    void aproveRequest(ActionEvent event) {

    }

    @FXML
    void declineRequest(ActionEvent event) {

    }

    @FXML
    void doGoBack(ActionEvent event) {
    	doSignOut(event);
    }

    @FXML
    void doSignOut(ActionEvent event) {
    	ClientUI.clientLogic.logOutUser();
    	ClientUI.historyStack.clearControllerHistory();
    	ClientUI.loginScreen.start(ClientUI.parentWindow);
    }
    
	@Override
	public void start(Stage stage) {
    	Parent root = null;
        try {
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/HrScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
//      scene.getStylesheets().add(getClass().getResource("/client/client_gui/LoginFxml.css").toExternalForm());
        stage.setTitle("HR Screen");
        stage.setScene(scene);
//      populateTable();
        stage.show();
	}
    	
//    @SuppressWarnings("unchecked")
//	public void initialize(ObservableList<BusinessClient> data) {
//    	Table = new TableView<BusinessClient>();
//    	workerID = new TableColumn<BusinessClient,String>("worker ID");
//    	workerName = new TableColumn<BusinessClient,String>("worker name");
//    	workerPhone = new TableColumn<BusinessClient,String>("worker phone");
//    	workerEmail = new TableColumn<BusinessClient,String>("worker email");
//    	
//    	workerID.setCellValueFactory(new PropertyValueFactory<BusinessClient, String>("workerID"));
//    	workerName.setCellValueFactory(new PropertyValueFactory<BusinessClient, String>("workerName"));
//    	workerPhone.setCellValueFactory(new PropertyValueFactory<BusinessClient, String>("workerPhone"));
//    	workerEmail.setCellValueFactory(new PropertyValueFactory<BusinessClient, String>("workerEmail"));
//    	Table.getColumns().add(workerID);
//    	Table.getColumns().add(workerName);
//    	Table.getColumns().add(workerPhone);
//    	Table.getColumns().add(workerEmail);
//
//    	Table.setItems(data);
//    	Table.refresh();
//    }
	
	
	private void populateTable() {
		User user = ClientUI.clientLogic.getLoggedUser();
		ClientUI.clientLogic.sendMessageToServer(user.getUser_ID(), DataType.HR_MANAGER,
				RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		ArrayList<BusinessClient> recievedData = (ArrayList<BusinessClient>) ClientUI.clientLogic.getLastDataRecieved();
		Table.getItems().setAll(recievedData);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		workerID.setCellValueFactory(new PropertyValueFactory<BusinessClient, String>("workerID"));
    	workerName.setCellValueFactory(new PropertyValueFactory<BusinessClient, String>("workerName"));
    	workerPhone.setCellValueFactory(new PropertyValueFactory<BusinessClient, String>("workerPhone"));
    	workerEmail.setCellValueFactory(new PropertyValueFactory<BusinessClient, String>("workerEmail"));
    	
    	populateTable();
	}
	
	///TEST--------------------------------
    @FXML
    private Button button;


    @FXML
    void doButtton(ActionEvent event) {
//		User user = ClientUI.clientLogic.getLoggedUser();
//		ClientUI.clientLogic.sendMessageToServer(user.getUser_ID(), DataType.HR_MANAGER,
//				RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA);
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}	
//		ArrayList<BusinessClient> recievedData = (ArrayList<BusinessClient>) ClientUI.clientLogic.getLastDataRecieved();
////		observableList = FXCollections.observableList(recievedData);
//		ObservableList<BusinessClient> obslist = Table.getItems();
//		while(!recievedData.isEmpty()) {
//			obslist.add(recievedData.remove(0));
//		}
//		Table.setItems(obslist);
    }
}
    	