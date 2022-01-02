package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
    private TabPane tabPane;

    @FXML
    private Tab tabWaitForApproval;
    
    @FXML
    private Tab tabWaitInTheKitchen;

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
    
    @FXML
    private TableColumn<Order, Integer> orderIDInTheKitchen;

    @FXML
    private TableColumn<Order, String> orderTimeInTheKitchen;

    @FXML
    private TableColumn<Order, String> typeOfOrderInTheKitchen;

    @FXML
    private TableColumn<Order, String> phoneNumberInTheKitchen;

    @FXML
    private TableColumn<Order, String> addressInTheKitchen;
    
    @FXML
    private Tab tabReady;

    @FXML
    private TableColumn<Order, Integer> orderIDReady;

    @FXML
    private TableColumn<Order, String> orderTimeReady;

    @FXML
    private TableColumn<Order, String> typeOfOrderReady;

    @FXML
    private TableColumn<Order, String> phoneNumberReady;

    @FXML
    private TableColumn<Order, String> addressReady;
    
    @FXML
    private Tab tabCompleted;

    @FXML
    private TableColumn<Order, Integer> orderIDCompleted;

    @FXML
    private TableColumn<Order, String> orderTimeCompleted;

    @FXML
    private TableColumn<Order, String> typeOfOrderCompleted;

    @FXML
    private TableColumn<Order, String> phoneNumberCompleted;

    @FXML
    private TableColumn<Order, String> addressCompleted;
    
    @FXML
    private Button approveOrderBtn;
    


    
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
    void approveOrderInWaitingForApprovalTable(ActionEvent event) {
//    	errorMsg.setVisible(false);
    	ObservableList<Order> currentData = waitForApprovalTable.getSelectionModel().getSelectedItems();
    	Order currentOrder = currentData.get(0); 
    	ArrayList<String> sendData = new ArrayList<>();
    	sendData.add(String.valueOf(currentOrder.getOrderID()));
    	sendData.add("in the kitchen");
		ClientUI.clientLogic.sendMessageToServer(sendData,
				DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_SUPPLIER_UPDATE_ORDER);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Error");
		    	alert.setHeaderText(null);
		    	alert.setContentText(ClientUI.clientLogic.getLastDataRecieved().toString());
		    	alert.showAndWait();
		    	return;
			}
		waitForApprovalTable.getItems().remove(currentOrder);
		populateInKitchenTable();
    }

    @FXML
    void cancelOrderInWaitingForApprovalTable(ActionEvent event) {
    	ObservableList<Order> currentData = waitForApprovalTable.getSelectionModel().getSelectedItems();
    	Order currentOrder = currentData.get(0); 

		ClientUI.clientLogic.sendMessageToServer(String.valueOf(currentOrder.getOrderID()),
				DataType.SINGLE_TEXT_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_SUPPLIER_CANCEL_ORDER);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Error");
		    	alert.setHeaderText(null);
		    	alert.setContentText(ClientUI.clientLogic.getLastDataRecieved().toString());
		    	alert.showAndWait();
		    	return;
			}
		waitForApprovalTable.getItems().remove(currentOrder);

    }

    @FXML
    void completedShowDishesInOrder(ActionEvent event) {
    	ObservableList<Order> currentData = completedTable.getSelectionModel().getSelectedItems();
    	if(currentData.isEmpty()) {
    		Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Error");
	    	alert.setHeaderText(null);
	    	alert.setContentText("please select an order to show");
	    	alert.showAndWait();
	    	return;
    	}
    	Order currentOrder = currentData.get(0); 
    	showDishInOrder(currentOrder);
    }

   @FXML
    void inThekitchenShowDishesInOrder(ActionEvent event) {
    	ObservableList<Order> currentData = inTheKitchenTable.getSelectionModel().getSelectedItems();
    	if(currentData.isEmpty()) {
    		Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Error");
	    	alert.setHeaderText(null);
	    	alert.setContentText("please select an order to show");
	    	alert.showAndWait();
	    	return;
    	}
    	Order currentOrder = currentData.get(0); 
    	showDishInOrder(currentOrder);
    }

    @FXML
    void orderCompleted(ActionEvent event) {
    	ObservableList<Order> currentData = readyTable.getSelectionModel().getSelectedItems();
    	Order currentOrder = currentData.get(0); 
    	
    	ArrayList<String> sendData = new ArrayList<>();
    	sendData.add(String.valueOf(currentOrder.getOrderID()));
    	sendData.add("done");

		ClientUI.clientLogic.sendMessageToServer(sendData,
				DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_SUPPLIER_UPDATE_ORDER);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Error");
	    	alert.setHeaderText(null);
	    	alert.setContentText(ClientUI.clientLogic.getLastDataRecieved().toString());
	    	alert.showAndWait();
	    	return;
		}
		readyTable.getItems().remove(currentOrder);
		populateCompletedTable();
    }

    @FXML
    void orderReady(ActionEvent event) {
    	ObservableList<Order> currentData = inTheKitchenTable.getSelectionModel().getSelectedItems();
    	Order currentOrder = currentData.get(0); 
    	
    	ArrayList<String> sendData = new ArrayList<>();
    	sendData.add(String.valueOf(currentOrder.getOrderID()));
    	sendData.add("ready");

		ClientUI.clientLogic.sendMessageToServer(sendData,
				DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_SUPPLIER_UPDATE_ORDER);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Error");
		    	alert.setHeaderText(null);
		    	alert.setContentText(ClientUI.clientLogic.getLastDataRecieved().toString());
		    	alert.showAndWait();
		    	return;
			}
		inTheKitchenTable.getItems().remove(currentOrder);
		populateReadyTable();

    }
    
    

    @FXML
    void readyShowDishesInOrder(ActionEvent event) {
    	ObservableList<Order> currentData = readyTable.getSelectionModel().getSelectedItems();
    	if(currentData.isEmpty()) {
    		Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Error");
	    	alert.setHeaderText(null);
	    	alert.setContentText("please select an order to show");
	    	alert.showAndWait();
	    	return;
    	}
    	Order currentOrder = currentData.get(0); 
    	showDishInOrder(currentOrder);
    }

    @FXML
    void showDishesInOrderWaitForApproval(ActionEvent event) {
    	ObservableList<Order> currentData = waitForApprovalTable.getSelectionModel().getSelectedItems();
    	if(currentData.isEmpty()) {
    		Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Error");
	    	alert.setHeaderText(null);
	    	alert.setContentText("please select an order to show");
	    	alert.showAndWait();
	    	return;
    	}
    	Order currentOrder = currentData.get(0); 
    	showDishInOrder(currentOrder);
    	
    }
    
    private void showDishInOrder(Order currentOrder) {
    	IClientFxController nextScreen = new ControllerFX_ActiveOrdersShowDishesInOrderScreen();
    	ControllerFX_ActiveOrdersShowDishesInOrderScreen.order = currentOrder;
    	Stage stage = new Stage();
	    nextScreen.start(stage);
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
	
	private void populateTable() {
		populateWaitingForApprovalTable();
//		ClientUI.clientLogic.sendMessageToServer(request,
//				DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST);
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
//			{
//				Alert alert = new Alert(AlertType.INFORMATION);
//		    	alert.setTitle("Error");
//		    	alert.setHeaderText(null);
//		    	alert.setContentText(ClientUI.clientLogic.getLastDataRecieved().toString());
//		    	alert.showAndWait();
//		    	return;
//			}
//			if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.ORDERS_LIST) {
//				System.out.println("Houston, we got a problem!");
//				return;
//			}	
//		ArrayList<Order> recievedData = (ArrayList<Order>) ClientUI.clientLogic.getLastDataRecieved();
//		waitForApprovalTable.getItems().setAll(recievedData);
		
		// in the kitchen table load
//		request.clear();
//		request.add(res.getRes_ID());
//		request.add("in the kitchen");
		populateInKitchenTable();
//		ClientUI.clientLogic.sendMessageToServer(request,
//			DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST);
//				try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
//			{
//				Alert alert = new Alert(AlertType.INFORMATION);
//		    	alert.setTitle("Error");
//		    	alert.setHeaderText(null);
//		    	alert.setContentText(ClientUI.clientLogic.getLastDataRecieved().toString());
//		    	alert.showAndWait();
//		    	return;
//			}
//			if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.ORDERS_LIST) {
//				System.out.println("Houston, we got a problem!");
//				return;
//			}	
//		ArrayList<Order> recievedData = (ArrayList<Order>) ClientUI.clientLogic.getLastDataRecieved();
//		inTheKitchenTable.getItems().setAll(recievedData);
//		
		
		// ready table load
//		request.clear();
//		request.add(res.getRes_ID());
//		request.add("ready");
		populateReadyTable();
//		ClientUI.clientLogic.sendMessageToServer(request,
//			DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST);
//				try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
//			{
//				Alert alert = new Alert(AlertType.INFORMATION);
//		    	alert.setTitle("Error");
//		    	alert.setHeaderText(null);
//		    	alert.setContentText(ClientUI.clientLogic.getLastDataRecieved().toString());
//		    	alert.showAndWait();
//		    	return;
//			}
//			if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.ORDERS_LIST) {
//				System.out.println("Houston, we got a problem!");
//				return;
//			}	
//		ArrayList<Order> recievedData = (ArrayList<Order>) ClientUI.clientLogic.getLastDataRecieved();
//		readyTable.getItems().setAll(recievedData);
		
		
		// completed table load
//		request.clear();
//		request.add(res.getRes_ID());
//		request.add("done");
		populateCompletedTable();
//		ClientUI.clientLogic.sendMessageToServer(request,
//			DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST);
//				try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
//			{
//				Alert alert = new Alert(AlertType.INFORMATION);
//		    	alert.setTitle("Error");
//		    	alert.setHeaderText(null);
//		    	alert.setContentText(ClientUI.clientLogic.getLastDataRecieved().toString());
//		    	alert.showAndWait();
//		    	return;
//			}
//			if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.ORDERS_LIST) {
//				System.out.println("Houston, we got a problem!");
//				return;
//			}	
//		ArrayList<Order> recievedData = (ArrayList<Order>) ClientUI.clientLogic.getLastDataRecieved();
//		completedTable.getItems().setAll(recievedData);
		
		

	}
	
	public void populateCompletedTable() {
		completedTable.getItems().clear();
		ArrayList<String> request = new ArrayList<String>();
		request.add(res.getRes_ID());
		request.add("done");
		ClientUI.clientLogic.sendMessageToServer(request,
			DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST);
				try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Error");
		    	alert.setHeaderText(null);
		    	alert.setContentText(ClientUI.clientLogic.getLastDataRecieved().toString());
		    	alert.showAndWait();
		    	return;
			}
			if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.ORDERS_LIST) {
				System.out.println("Houston, we got a problem!");
				return;
			}	
		ArrayList<Order> recievedData = (ArrayList<Order>) ClientUI.clientLogic.getLastDataRecieved();
		completedTable.getItems().setAll(recievedData);
	}
	
	public void populateReadyTable() {
		readyTable.getItems().clear();
		ArrayList<String> request = new ArrayList<String>();
		request.add(res.getRes_ID());
		request.add("ready");
		ClientUI.clientLogic.sendMessageToServer(request,
			DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST);
				try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Error");
		    	alert.setHeaderText(null);
		    	alert.setContentText(ClientUI.clientLogic.getLastDataRecieved().toString());
		    	alert.showAndWait();
		    	return;
			}
			if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.ORDERS_LIST) {
				System.out.println("Houston, we got a problem!");
				return;
			}	
		ArrayList<Order> recievedData = (ArrayList<Order>) ClientUI.clientLogic.getLastDataRecieved();
		readyTable.getItems().setAll(recievedData);
	}
	
	public void populateInKitchenTable() {
		inTheKitchenTable.getItems().clear();
		ArrayList<String> request = new ArrayList<String>();
		request.add(res.getRes_ID());
		request.add("in the kitchen");
		ClientUI.clientLogic.sendMessageToServer(request,
			DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST);
				try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Error");
		    	alert.setHeaderText(null);
		    	alert.setContentText(ClientUI.clientLogic.getLastDataRecieved().toString());
		    	alert.showAndWait();
		    	return;
			}
			if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.ORDERS_LIST) {
				System.out.println("Houston, we got a problem!");
				return;
			}	
		ArrayList<Order> recievedData = (ArrayList<Order>) ClientUI.clientLogic.getLastDataRecieved();
		inTheKitchenTable.getItems().setAll(recievedData);
		
	}
	
	public void populateWaitingForApprovalTable(){
		waitForApprovalTable.getItems().clear();
		ArrayList<String> request = new ArrayList<>();
		request.add(String.valueOf(res.getRes_ID()));
		request.add("waiting for approval");
		ClientUI.clientLogic.sendMessageToServer(request,
				DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Error");
		    	alert.setHeaderText(null);
		    	alert.setContentText(ClientUI.clientLogic.getLastDataRecieved().toString());
		    	alert.showAndWait();
		    	return;
			}
			if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.ORDERS_LIST) {
				System.out.println("Houston, we got a problem!");
				return;
			}	
		ArrayList<Order> recievedData = (ArrayList<Order>) ClientUI.clientLogic.getLastDataRecieved();
		waitForApprovalTable.getItems().setAll(recievedData);
	}
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderIDwaitForApproval.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
		orderTimeWaitForApproval.setCellValueFactory(new PropertyValueFactory<Order, String>("timeOfOrder"));
		typeOfOrderWaitForApproval.setCellValueFactory(new PropertyValueFactory<Order, String>("typeOfOrder"));
		phoneWaitForApproval.setCellValueFactory(new PropertyValueFactory<Order, String>("userPhone"));
		addressWaitForApproval.setCellValueFactory(new PropertyValueFactory<Order, String>("deliveryAddress"));
		
		orderIDInTheKitchen.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
		orderTimeInTheKitchen.setCellValueFactory(new PropertyValueFactory<Order, String>("timeOfOrder"));
		typeOfOrderInTheKitchen.setCellValueFactory(new PropertyValueFactory<Order, String>("typeOfOrder"));
		phoneNumberInTheKitchen.setCellValueFactory(new PropertyValueFactory<Order, String>("userPhone"));
		addressInTheKitchen.setCellValueFactory(new PropertyValueFactory<Order, String>("deliveryAddress"));
		
		orderIDReady.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
		orderTimeReady.setCellValueFactory(new PropertyValueFactory<Order, String>("timeOfOrder"));
		typeOfOrderReady.setCellValueFactory(new PropertyValueFactory<Order, String>("typeOfOrder"));
		phoneNumberReady.setCellValueFactory(new PropertyValueFactory<Order, String>("userPhone"));
		addressReady.setCellValueFactory(new PropertyValueFactory<Order, String>("deliveryAddress"));
		
		orderIDCompleted.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
		orderTimeCompleted.setCellValueFactory(new PropertyValueFactory<Order, String>("timeOfOrder"));
		typeOfOrderCompleted.setCellValueFactory(new PropertyValueFactory<Order, String>("typeOfOrder"));
		phoneNumberCompleted.setCellValueFactory(new PropertyValueFactory<Order, String>("userPhone"));
		addressCompleted.setCellValueFactory(new PropertyValueFactory<Order, String>("deliveryAddress"));

		populateTable();
		
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
