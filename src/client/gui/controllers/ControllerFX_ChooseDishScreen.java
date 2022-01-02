package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utility.entity.Dish;

public class ControllerFX_ChooseDishScreen implements IClientFxController, Initializable {

    public static Dish dish;
    
    private int index;
    
    private String[] priceArray;
    
    private String price;

	@FXML
    private SplitPane splitPane;

    @FXML
    private Label dishNameLabel;

    @FXML
    private Label dishDescriptionLabel;

    @FXML
    private ComboBox<String> sizeCombo;

    @FXML
    private ComboBox<String> levelOfCookingCombo;

    @FXML
    private TextField instructionsTextBox;

    @FXML
    private Label priceLabel;

    @FXML
    private Button addToCartButton;

    @FXML
    private Button exitCartButton1;

    @FXML
    private Label emptyCartLabel1;

    @FXML
    void exitCart(ActionEvent event) {
    	splitPane.setDividerPosition(0, 1);
    }
    
    @FXML
    void showCart(ActionEvent event) {
    	splitPane.setDividerPosition(0, 0.7);
    }


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
    void addToCart(ActionEvent event) {
    	

    }
    
    @FXML
    void chooseSizeInCombo(ActionEvent event) {
    	index = sizeCombo.getSelectionModel().getSelectedIndex();
    	if(index<priceArray.length)
    		price = priceArray[index];    		
    	priceLabel.setText("price: " + price + "$");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dishNameLabel.setText(dish.getName());
		dishDescriptionLabel.setText(dish.getDescription());
		priceArray = dish.getPrice().split(",");
		price = "";

		if(dish.getSize() != null) {
			String[] sizes = dish.getSize().split(",");
			sizeCombo.getItems().addAll(sizes);
		}
		else {
			sizeCombo.setDisable(true);
			price = priceArray[0];
		}
		if(dish.getCooking_level() != null) {
			String[] cookingLevel = dish.getCooking_level().split(",");
			levelOfCookingCombo.getItems().addAll(cookingLevel);
		}
		else levelOfCookingCombo.setDisable(true);
		
		priceLabel.setText("price: " + price + "$");
		
		
	}

	@Override
	public void start(Stage stage) {
		Parent root = null;
        try {
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/chooseDish.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
        stage.setTitle("Choose Dish");
        stage.setScene(scene);
        stage.show();
		
	}
    
    

}
