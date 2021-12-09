package client_gui;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client_logic.BMClientLogic;
import entity.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.DataType;
import utility.RequestType;

public class OrderFrameController implements Initializable{
	
	private static BMClientLogic ClientLogic;
	
    @FXML
    private TextField orderNumTxt;

    @FXML
    private Button showBtn;

    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private TextField addressTxt;

    @FXML
    private Button updateBtn;
    
    @FXML
    private TextField upOrderNumTxt;
    
    @FXML
    private Label errorMsgShowLbl;

    @FXML
    private Label errorMsgUpdateLbl;
    
    @FXML
    private Button buttonQuit;
    
    @FXML
    private Button buttonSetIp = new Button("Start");
    
    @FXML
    private TextField txtSetIP = new TextField();
    
    //method to set the values for the orderType ComboBox
 	private void setTypeComboBox() {
 		ArrayList<String> al = new ArrayList<String>();
 		al.add("Well Done");
 		al.add("Medium Well");
 		al.add("Medium");
 		al.add("Medium Rare");
 		al.add("Still Alive");
 		ObservableList<String> list = FXCollections.observableArrayList(al);
 		cmbType.getItems().addAll(list);
 	}
 	
 	//method that pulls order data from server, opens a new "window" and presents it.
    @FXML
    private void Show(ActionEvent event) throws IOException {
    	String orderNumber;
		FXMLLoader loader = new FXMLLoader();
		orderNumber = orderNumTxt.getText();
		if (orderNumber.trim().isEmpty()) {
			errorMsgShowLbl.setText("You must enter an id number");
		} else {
			//send message to server
			Order order = new Order(orderNumber);
			ClientLogic.sendMessageToServer(order, DataType.ORDER, RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO error handling
				e.printStackTrace();
			}
			if (ClientLogic.getLastDataTypeRecieved() == DataType.ERROR_MESSAGE) {
				// TODO: TYPE OF ERROR
				errorMsgShowLbl.setText((String) ClientLogic.getLastDataRecieved());
				} else {
					//on successful data delivery - open new window
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
					Stage primaryStage = new Stage();
					Pane root = loader.load(getClass().getResource("/client_gui/OrderDetailsFrame.fxml").openStream());
					OrderDetailsFrameController orderDetailsFrameController = loader.getController();
					orderDetailsFrameController.loadOrder((Order) ClientLogic.getLastDataRecieved());
					Scene scene = new Scene(root);
//					scene.getStylesheets().add(getClass().getResource("/gui_/OrderDetailsFrame.css").toExternalForm());
					primaryStage.setTitle("Show Order");
					primaryStage.setScene(scene);
					primaryStage.show();
				}
		}
	}

    //method that sends data to server with and update request
    @FXML
    private void Update(ActionEvent event) {
    	//input check
    	String orderNumber = upOrderNumTxt.getText();
    	String newType = cmbType.getValue();
    	String newAddress = addressTxt.getText();
    	
    	if(orderNumber.trim().isEmpty()) {
    		errorMsgUpdateLbl.setText("You must enter an id number");
    		return;
    	}
    	if(newType == null && newAddress.trim().isEmpty()) {
    		errorMsgUpdateLbl.setText("You must enter data to update");
    		return;
    	}
    	if(newType == null) {
    		newType = "";
    	}
    	//sending message
    	Order order = new Order(orderNumber,"","","",newType,newAddress);
    	ClientLogic.sendMessageToServer(order, DataType.ORDER, RequestType.CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ClientLogic.getLastDataTypeRecieved() == DataType.ERROR_MESSAGE) {
			// TODO: TYPE OF ERROR
			errorMsgUpdateLbl.setText((String) ClientLogic.getLastDataRecieved());
		}
		
    }
    
    public void start(Stage primaryStage) throws Exception {
    	getIPandStartClientLogic();
        Parent root = FXMLLoader.load(getClass().getResource("/client_gui/OrderFrame.fxml"));
        Scene scene = new Scene(root);
//      scene.getStylesheets().add(getClass().getResource("/client_gui/OrderFrame.css").toExternalForm());
        primaryStage.setTitle("Order Frame");
        primaryStage.setScene(scene);
        //setting client shutdown on window close
		primaryStage.setOnCloseRequest(e -> {
			try {
				ClientLogic.closeConnectionWithMessageToServer();
			} catch (Exception ex) {
				System.exit(1);
			}
			System.exit(0);
		});
        primaryStage.show();
    }

	public void setClientLogic(BMClientLogic clientLogic) {
		ClientLogic = clientLogic;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setTypeComboBox();		
	}
	
    @FXML
    private void doQuit(ActionEvent event) {
    	try {
    		ClientLogic.closeConnectionWithMessageToServer();
		} catch (IOException e) {
			System.exit(1);
		}
    	System.exit(0);
    }
    
    //used to display the "Server IP" window and to call init on client logic
    public void getIPandStartClientLogic() {
    	txtSetIP.setPromptText("Enter Server IP");
    	VBox root = new VBox();
    	root.getChildren().add(txtSetIP);
    	root.getChildren().add(buttonSetIp);
    	Scene scene = new Scene(root);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.show();
    	buttonSetIp.setOnAction(e ->{
    		String ip = txtSetIP.getText();
    		System.out.println(ip);
    		try {
				ClientUI.connectWithHostIP(ip, this);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    		stage.close();
    	});
    	}

}

