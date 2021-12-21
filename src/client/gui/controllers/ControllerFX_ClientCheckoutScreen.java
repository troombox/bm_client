package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import client.utility.wrappers.MemoryButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utility.entity.Dish;

public class ControllerFX_ClientCheckoutScreen implements IClientFxController, Initializable {
	
    @FXML
    private RadioButton pickEarlyDelivery;

    @FXML
    private RadioButton pickRegualrDelivery;

    @FXML
    private RadioButton pickPickupDelivery;

    @FXML
    private DatePicker datePicker;

    @FXML
    private GridPane dishGridPane;

    @FXML
    private ComboBox<?> timePicker;

    @FXML
    private ComboBox<?> typeOfDelivery;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private Label labelDelivery;

    @FXML
    private Label labelFinalPrice;

    @FXML
    private Button buttonPay;

    @FXML
    void doPayment(ActionEvent event) {
    	
    }

	@Override
	public void start(Stage stage) {
        Parent root = null;
        try {
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/ClientCheckoutScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
        stage.setTitle("Checkout");
        stage.setScene(scene);
        stage.show();
	}
	
    @FXML
    void doGoBack(ActionEvent event) {
    	ClientUI.historyStack.popFxController().start(ClientUI.parentWindow);
    }
    
    @FXML
    void doSignOut(ActionEvent event) {
    	ClientUI.clientLogic.logOutUser();
    	ClientUI.loginScreen.start(ClientUI.parentWindow);
    }
	
    @FXML
    void doRadioButtonChoice(ActionEvent event) {
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillInCart();
	}
	
	private void fillInCart() {
		if(ClientUI.clientLogic.isOrderListEmpty()) {
			return;
		}
		int cartPrice = 0;

		dishGridPane.getChildren().clear();
		//for each dish in order we update the cart to show it
		for(int i = 0; i < ClientUI.clientLogic.getOrderDishes().size(); i++) {
			Dish dish = ClientUI.clientLogic.getOrderDishes().get(i);
			Label dishName = new Label(dish.getName());
			Label dishPrice = new Label(dish.getPrice());
			dishGridPane.add(dishName, 0, i);
			dishGridPane.add(dishPrice, 1, i);
			MemoryButton newButton = new MemoryButton("-",i);
			newButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	Dish toRemove = ClientUI.clientLogic.getOrderDishes().get(newButton.getIndex());
	            	ClientUI.clientLogic.removeFromOrder(toRemove);
	            	fillInCart();
	            }
	        });
			if(ClientUI.clientLogic.getOrderDishes().size() > 1) {
				dishGridPane.add(newButton, 2, i);
			}
			cartPrice += Integer.parseInt(dish.getPrice());
		}
		labelFinalPrice.setText(String.valueOf(cartPrice));
	}

}
