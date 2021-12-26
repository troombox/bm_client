package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.entity.Dish;
import utility.entity.Order;
import utility.entity.Restaurant;
import utility.enums.DataType;
import utility.enums.RequestType;

public class ControllerFX_ActiveOrdersScreen implements IClientFxController, Initializable {
	
	@FXML
    private TableView<Order> waitForApprovalTable;

    @FXML
    private TableView<Order> inTheKitchenTable;

    @FXML
    private TableView<Order> readyTable;

    @FXML
    private TableView<Order> completedTable;
    
    @FXML
    private TableColumn<Order, Integer> orderIDwaitForApproval;

    @FXML
    private TableColumn<Order, String> orderTimeWaitForApproval;

    @FXML
    private TableColumn<Order, String> typeOfOrderWaitForApproval;

    @FXML
    private TableColumn<Order, String> phoneWaitForApproval;

    @FXML
    private TableColumn<Order, String> addressWaitForApproval;

    
    public static Restaurant res;
    
    

    @FXML
    void goBack(ActionEvent event) {
    	ClientUI.historyStack.popFxController().start(ClientUI.parentWindow);
    }

    @FXML
    void signOut(ActionEvent event) {
    	ClientUI.clientLogic.logOutUser();
    	ClientUI.loginScreen.start(ClientUI.parentWindow);
    }
    
    @FXML
    void approveOrder(ActionEvent event) {

    }

    @FXML
    void cancelOrder(ActionEvent event) {

    }

    @FXML
    void completedShowDishesInOrder(ActionEvent event) {

    }

    @FXML
    void inThekitchenShowDishesInOrder(ActionEvent event) {

    }

    @FXML
    void orderCompleted(ActionEvent event) {

    }

    @FXML
    void orderReady(ActionEvent event) {

    }

    @FXML
    void readyShowDishesInOrder(ActionEvent event) {

    }

    @FXML
    void showDishesInOrderWaitForApproval(ActionEvent event) {

    }


	@Override
	public void start(Stage stage) {
		Parent root = null;
        try {
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/activeOrders.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
        stage.setTitle("welcome");
        stage.setScene(scene);
        stage.show();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderIDwaitForApproval.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
		orderTimeWaitForApproval.setCellValueFactory(new PropertyValueFactory<Order, String>("timeOfOrder"));
		typeOfOrderWaitForApproval.setCellValueFactory(new PropertyValueFactory<Order, String>("typeOfOrder"));
		phoneWaitForApproval.setCellValueFactory(new PropertyValueFactory<Order, String>("userPhone"));
		addressWaitForApproval.setCellValueFactory(new PropertyValueFactory<Order, String>("deliveryAddress"));

		
//		ClientUI.clientLogic.sendMessageToServer(res.getRes_ID(),
//				DataType.SINGLE_TEXT_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST);
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
//		{
//			Alert alert = new Alert(AlertType.INFORMATION);
//	    	alert.setTitle("Error");
//	    	alert.setHeaderText(null);
//	    	alert.setContentText(ClientUI.clientLogic.getLastDataRecieved().toString());
//	    	alert.showAndWait();
//	    	return;
//		}
//		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.ORDERS_LIST) {
//			System.out.println("Houston, we got a problem!");
//			return;
//		}
//	
		
	}

}
