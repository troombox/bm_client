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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.entity.Dish;
import utility.entity.Restaurant;
import utility.enums.DataType;
import utility.enums.RequestType;

/*
 * The Class ControllerFX_AddDishScreen.
 * this screen presented to supplier in edit menu - after pressing the button "add dish"
 * supplier can add a new dish to his menu
 */

public class ControllerFX_AddDishScreen implements IClientFxController, Initializable {
	
	/** The dish name text box. */
	@FXML
    private TextField dishNameTextBox;

    /** The dish description text box. */
    @FXML
    private TextArea dishDescriptionTextBox;

    /** The size text box. */
    @FXML
    private TextField sizeTextBox;
    
    /** The price text box. */
    @FXML
    private TextField priceTextBox;

    /** The dish type combo box. */
    @FXML
    private ComboBox<String> dishTypeComboBox;

    /** The level of cooking text box. */
    @FXML
    private TextField levelOfCookingTextBox;

    /** The size list. */
    @FXML
    private ListView<String> sizeList;

    /** The level of cooking list. */
    @FXML
    private ListView<String> levelOfCookingList;
    
    /** The price list. */
    @FXML
    private ListView<String> priceList;

    
    /** The restaurant */
    public static Restaurant res;
    
    /** The Error massage. */
    @FXML
    private Label ErrorMsg;
    
    /** The cooking level V box. */
    @FXML
    private VBox cookingLevelVBox;
    
    /** The menu. */
    public static ArrayList<Dish> menu;
    
    /** The price list tooltip. */
    @FXML
    private Tooltip priceTT;
    
    /** The size list tooltip. */
    @FXML
    private Tooltip sizeTT;
    
    /** The coocking level list tooltip. */
    @FXML
    private Tooltip coockingLevelTT;


    /**
     * Removes the selected level of cooking.
     *
     * @param event the event
     */
    @FXML
    void removeSelectedLevelOfCooking(ActionEvent event) {
    	String levelOfCooking = levelOfCookingList.getSelectionModel().getSelectedItem();
    	if(levelOfCooking != null) {
    		levelOfCookingList.getItems().remove(levelOfCooking);
    	}
    }
    
    /**
     * Check selected type of dish value. update disability of cooking level according to type
     *
     * @param event the event
     */
    @FXML
    void checkSelectedTypeValue(ActionEvent event) {
    	if(dishTypeComboBox.getValue() == "drink") {
    		cookingLevelVBox.setDisable(true);
    	}
    	else cookingLevelVBox.setDisable(false);
    }
    

    /**
     * Removes the selected size off the list
     *
     * @param event the event
     */
    @FXML
    void removeSelectedSize(ActionEvent event) {
    	String size = sizeList.getSelectionModel().getSelectedItem();
    	String price = priceList.getSelectionModel().getSelectedItem();
    	if(size==null || price == null) {
    		ErrorMsg.setVisible(true);
    		ErrorMsg.setText("please select size and price to delete");
	    	return;
    	}
    	
    	sizeList.getItems().remove(size);
    	priceList.getItems().remove(price);
    }

    
    /**
     * Adds the new level of cooking to the list
     *
     * @param event the event
     */
    @FXML
    void AddNewLevelOfCooking(ActionEvent event) {
    	String levelOfCooking = levelOfCookingTextBox.getText();
    	if(levelOfCooking != null) {
    		levelOfCookingList.getItems().add(levelOfCooking);
    	}
    }

    /**
     * Adds the new size to the list
     *
     * @param event the event
     */
    @FXML
    void addNewSize(ActionEvent event) {
    	String size = sizeTextBox.getText();
    	String price = priceTextBox.getText();
    	if(size.isEmpty()) {
    		ErrorMsg.setVisible(true);
    		ErrorMsg.setText("please fill size to add");
	    	return;
    	}
    	if(price.isEmpty()) {
    		ErrorMsg.setVisible(true);
    		ErrorMsg.setText("please fill price to add");
	    	return;
    	}
    	try {
    		Float.parseFloat(price);
    	} catch (Exception e) {
    		ErrorMsg.setVisible(true);
    		ErrorMsg.setText("The price must be a number");
	    	return;
		}   	
    	
    	sizeList.getItems().add(size);
    	priceList.getItems().add(price);
    }

    /**
     * Adds the dish to menu.
     *
     * @param event the event
     */
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
    	
    	String levelOfCooking = "";
    	for(int i=0; i<levelOfCookingList.getItems().size(); i++) {
    		levelOfCooking += levelOfCookingList.getItems().get(i);
    		if(i!= levelOfCookingList.getItems().size()-1) {
    			levelOfCooking += ",";
    		}
    	}
    	
    	if(levelOfCooking == "") levelOfCooking = null;
    	
    	if(dishName.isEmpty() ) {
    		ErrorMsg.setVisible(true);
    		ErrorMsg.setText("please insert dish name");
    		return;
    	}
    	if(dishType == null ) {
        	ErrorMsg.setVisible(true);
        	ErrorMsg.setText("please insert dish type");
        	return;
        }
        if(description.isEmpty() ) {
            ErrorMsg.setVisible(true);
            ErrorMsg.setText("please insert dish description");
            return;
        }
    	if(price == "") {
    		ErrorMsg.setVisible(true);
            ErrorMsg.setText("please enter size and price");
            return;
    	}
    	
    	for(Dish dish: menu) {
    		if(dish.getName().equals(dishName)) {
    			ErrorMsg.setVisible(true);
                ErrorMsg.setText(dishName + " already exist in your menu! please select different name to your dish.");
                return;
    		}
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
			ErrorMsg.setVisible(true);
            ErrorMsg.setText((String)ClientUI.clientLogic.getLastDataRecieved());
			return;
		}
		if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.DISH) {
			System.out.println("Houston, we got a problem!");
			return;
		}
		
		dish = (Dish)ClientUI.clientLogic.getLastDataRecieved();
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("successful update");
    	alert.setHeaderText(null);
    	alert.setContentText(dish.getName() + " added to your menu");
    	alert.showAndWait();

    }

    /**
     * Go back.
     *
     * @param event the event
     */
    @FXML
    void goBack(ActionEvent event) {
    	ClientUI.historyStack.popFxController().start(ClientUI.parentWindow);
    }

    /**
     * Sign out.
     *
     * @param event the event
     */
    @FXML
    void signOut(ActionEvent event) {
    	ClientUI.clientLogic.logOutUser();
    	ClientUI.loginScreen.start(ClientUI.parentWindow);
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

	/**
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dishTypeComboBox.getItems().add("first");
		dishTypeComboBox.getItems().add("main");
		dishTypeComboBox.getItems().add("dessert");
		dishTypeComboBox.getItems().add("drink");
		
		priceList.setTooltip(priceTT);
		sizeList.setTooltip(sizeTT);
		levelOfCookingList.setTooltip(coockingLevelTT);
		
		sizeList.setPlaceholder(new Label("No sizes"));
		priceList.setPlaceholder(new Label("No prices"));
		levelOfCookingList.setPlaceholder(new Label("No cooking level"));


		
	}

}
