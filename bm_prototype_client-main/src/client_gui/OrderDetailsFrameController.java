package client_gui;

import java.io.IOException;

import entity.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OrderDetailsFrameController{
	
	private Order order;

    @FXML
    private TextField orderNUmTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField timeTxt;

    @FXML
    private TextField resTxt;

    @FXML
    private TextField addressTxt;
    
    @FXML
    private Button returnButton;

    @FXML
    void doReturnAction(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/client_gui/OrderFrame.fxml"));
        Scene scene = new Scene(root);
//      scene.getStylesheets().add(getClass().getResource("/client_gui/OrderFrame.css").toExternalForm());
        primaryStage.setTitle("Order Frame");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //called on window open to load Order data
	public void loadOrder(Order o) {
		this.order = o;
		this.orderNUmTxt.setText(order.getOrderNumber());
		this.timeTxt.setText(order.getOrderTime());
		this.typeTxt.setText(order.getTypeOfOrder());
		this.phoneTxt.setText(order.getPhoneNumber());
		this.resTxt.setText(order.getRestaurantName());
		this.addressTxt.setText(order.getOrderAddress());
	}

}
