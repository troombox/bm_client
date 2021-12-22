package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import utility.entity.Dish;
import utility.entity.Restaurant;
import utility.enums.DataType;
import utility.enums.RequestType;

public class ControllerFX_EditDishScreen implements IClientFxController, Initializable {

    @FXML
    private TextField dishNameTextBox;

    @FXML
    private ComboBox<String> dishTypeComboBox;

    @FXML
    private TextArea dishDescriptionTextBox;

    @FXML
    private ListView<String> sizeList;

    @FXML
    private ListView<String> priceList;

    @FXML
    private TextField sizeTextBox;

    @FXML
    private TextField priceTextBox;

    @FXML
    private ListView<String> levelOfCookingList;

    @FXML
    private TextField levelOfCookingTextBox;
    
    public static Dish dish;
    
    @FXML
    void removeSelectedLevelOfCooking(ActionEvent event) {
    	String levelOfCooking = levelOfCookingList.getSelectionModel().getSelectedItem();
    	if(levelOfCooking != null) {
    		levelOfCookingList.getItems().remove(levelOfCooking);
    	}
    }

    @FXML
    void removeSelectedSize(ActionEvent event) {
    	String size = sizeList.getSelectionModel().getSelectedItem();
    	String price = priceList.getSelectionModel().getSelectedItem();
    	if(size==null || price == null) {
    		Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("empty field");
	    	alert.setHeaderText(null);
	    	alert.setContentText("please select size and price to delete");
	    	alert.showAndWait();
	    	return;
    	}
    	
    	sizeList.getItems().remove(size);
    	priceList.getItems().remove(price);
    }

    
    @FXML
    void AddNewLevelOfCooking(ActionEvent event) {
    	String levelOfCooking = levelOfCookingTextBox.getText();
    	if(levelOfCooking != null) {
    		levelOfCookingList.getItems().add(levelOfCooking);
    	}
    }

    @FXML
    void addNewSize(ActionEvent event) {
    	String size = sizeTextBox.getText();
    	String price = priceTextBox.getText();
    	if(size.isEmpty()) {
    		Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("empty field");
	    	alert.setHeaderText(null);
	    	alert.setContentText("please fill size to add");
	    	alert.showAndWait();
	    	return;
    	}
    	if(price.isEmpty()) {
    		Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("empty field");
	    	alert.setHeaderText(null);
	    	alert.setContentText("please fill price to add");
	    	alert.showAndWait();
	    	return;
    	}
    	try {
    		Integer.parseInt(price);
    	} catch (Exception e) {
    		Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("wrong input");
	    	alert.setHeaderText(null);
	    	alert.setContentText("The price must be a number");
	    	alert.showAndWait();
	    	return;
		}   	
    	
    	sizeList.getItems().add(size);
    	priceList.getItems().add(price);
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
    void updateDish(ActionEvent event) {
    	String dishType = dishTypeComboBox.getValue();
    	String dishName = dishNameTextBox.getText();
    	String description = dishDescriptionTextBox.getText();
    	String sizes = "";
    	for(int i=0; i<sizeList.getItems().size(); i++) {
    		sizes += sizeList.getItems().get(i);
    		if(i!= sizeList.getItems().size()-1) {
    			sizes += ",";
    		}
    	}
    	if(sizes == "") sizes = null;
    	String price = "";
    	for(int i=0; i<priceList.getItems().size(); i++) {
    		price += priceList.getItems().get(i);
    		if(i!= priceList.getItems().size()-1) {
    			price += ",";
    		}
    	}
    	if(price == "") {
    		System.out.println("please enter a price");
    		return;
    	}
    	String levelOfCooking = "";
    	for(int i=0; i<levelOfCookingList.getItems().size(); i++) {
    		levelOfCooking += levelOfCookingList.getItems().get(i);
    		if(i!= levelOfCookingList.getItems().size()-1) {
    			levelOfCooking += ",";
    		}
    	}
    	if(levelOfCooking == "") levelOfCooking = null;
    	if(dishType == null || dishName == null || description == null) {
    		System.out.println("please fill all fields");
    		return;
    	}
    	
    	Dish updatedDish = new Dish(dish.getDish_ID(), dish.getRes_ID(),dishType , dishName, description, sizes, levelOfCooking, price);
    	ClientUI.clientLogic.sendMessageToServer(updatedDish,
				DataType.DISH, RequestType.CLIENT_REQUEST_TO_SERVER_UPDATE_DISH_IN_MENU_REQUEST);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE) {
			System.out.println(ClientUI.clientLogic.getLastDataRecieved());
			return;
		}
		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.SINGLE_TEXT_STRING) {
			System.out.println("Houston, we got a problem!");
			return;
		}
		
		System.out.println(dish.getName() + " updated in your menu");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dishNameTextBox.setText(dish.getName());
		dishDescriptionTextBox.setText(dish.getDescription());
		
		dishTypeComboBox.getItems().add("first");
		dishTypeComboBox.getItems().add("main");
		dishTypeComboBox.getItems().add("dessert");
		dishTypeComboBox.getItems().add("drink");
		
		dishTypeComboBox.setValue(dish.getType());
		if(dish.getSize()!=null) {
		String[] size = dish.getSize().split(",");
		sizeList.getItems().addAll(size);
		}
		if(dish.getPrice()!=null) {
			String[] price = dish.getPrice().split(",");
		priceList.getItems().addAll(price);
		}
		if(dish.getCooking_level()!=null) {
			String[] cookingLevel = dish.getCooking_level().split(",");
		levelOfCookingList.getItems().addAll(cookingLevel);
		}
		
	}

	@Override
	public void start(Stage stage) {
		Parent root = null;
        try {
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/editDish.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
        stage.setTitle("Edit your dish");
        stage.setScene(scene);
        stage.show();
		
	}

}
