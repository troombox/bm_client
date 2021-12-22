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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utility.entity.BusinessClient;
import utility.entity.Dish;
import utility.entity.Restaurant;
import utility.enums.DataType;
import utility.enums.RequestType;

public class ControllerFX_AddDishScreen implements IClientFxController, Initializable {
	@FXML
    private TextField dishNameTextBox;

    @FXML
    private TextArea dishDescriptionTextBox;

    @FXML
    private TextField sizeTextBox;
    
    @FXML
    private TextField priceTextBox;

    @FXML
    private ComboBox<String> dishTypeComboBox;

    @FXML
    private TextField levelOfCookingTextBox;

    @FXML
    private ListView<String> sizeList;

    @FXML
    private ListView<String> levelOfCookingList;
    
    @FXML
    private ListView<String> priceList;

    
    public static Restaurant res;


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
    void addDishToMenu(ActionEvent event) {
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
    	
    	Dish dish = new Dish(null, res.getRes_ID(),dishType , dishName, description, sizes, levelOfCooking, price);
    	ClientUI.clientLogic.sendMessageToServer(dish,
				DataType.DISH, RequestType.CLIENT_REQUEST_TO_SERVER_ADD_DISH_TO_MENU_REQUEST);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE) {
			System.out.println(ClientUI.clientLogic.getLastDataRecieved());
			return;
		}
		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.DISH) {
			System.out.println("Houston, we got a problem!");
			return;
		}
		
		dish = (Dish)ClientUI.clientLogic.getLastDataRecieved();
		System.out.println(dish.getName() + "added to your menu");

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

	@Override
	public void start(Stage stage) {
		Parent root = null;
        try {
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/addDish.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
        stage.setTitle("Add new dish");
        stage.setScene(scene);
        stage.show();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dishTypeComboBox.getItems().add("first");
		dishTypeComboBox.getItems().add("main");
		dishTypeComboBox.getItems().add("dessert");
		dishTypeComboBox.getItems().add("drink");

		
	}

}
