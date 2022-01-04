package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.animation.PauseTransition;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import utility.entity.Dish;

/**
 * The Class ControllerFX_ChooseDishScreen.
 * this screen is shown to client, it shows the details of the chosen dish. he can add the dish to his cart from this screen 
 */
public class ControllerFX_ChooseDishScreen implements IClientFxController, Initializable {

    /** The dish. */
    public static Dish dish;
    
    /** The index of size in sizes array. */
    private int index;
    
    /** The price array. */
    private String[] priceArray;
    
    /** The price. */
    private String price;

	/** The split pane. */
	@FXML
    private SplitPane splitPane;

    /** The dish name label. */
    @FXML
    private Label dishNameLabel;

    /** The dish description label. */
    @FXML
    private Label dishDescriptionLabel;

    /** The size combo. */
    @FXML
    private ComboBox<String> sizeCombo;

    /** The level of cooking combo. */
    @FXML
    private ComboBox<String> levelOfCookingCombo;

    /** The instructions text box. */
    @FXML
    private TextField instructionsTextBox;

    /** The price label. */
    @FXML
    private Label priceLabel;

    /** The add to cart button. */
    @FXML
    private Button addToCartButton;

    /** The exit cart button 1. */
    @FXML
    private Button exitCartButton1;

    /** The empty cart label 1. */
    @FXML
    private Label emptyCartLabel1;
    
    /** The cart V box. */
    @FXML
    private VBox cartVBox;

    /** The cart dishes grid. */
    @FXML
    private GridPane cartDishesGrid;
    
    /** The label total price. */
    @FXML
    private Label labelTotalPrice;
    
	/** The button checkout. */
	@FXML
	private Button buttonCheckout;
	
    /** The Error msg. */
    @FXML
    private Label ErrorMsg;
    
    /** The dish added popup. */
    @FXML
    private Pane dishAddedPopup;


    /**
     * Exit cart.
     *
     * @param event the event
     */
    @FXML
    void exitCart(ActionEvent event) {
    	splitPane.setDividerPosition(0, 1);
    }
    
    /**
     * Show cart in the same screen. 
     *
     * @param event the event
     */
    @FXML
    void showCart(ActionEvent event) {
    	splitPane.setDividerPosition(0, 0.7);
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
     * Adds the dish to cart.
     *
     * @param event the event
     */
    @FXML
    void addToCart(ActionEvent event) {
    	String dishSize = "";
    	String dishLevelOfCooking = "";
    	//check the fields:
    	if(!sizeCombo.isDisabled()) {
    		if(sizeCombo.getValue() == null) {
    			//TODO: Error
    			showError(true, "dish size not selected");
    			return;
    		}
    		dishSize = sizeCombo.getValue();
    	}
    	if(!levelOfCookingCombo.isDisabled()) {
    		if(levelOfCookingCombo.getValue() == null) {
    			showError(true, "cooking level not selected");
    			return;
    		}
    		dishLevelOfCooking = levelOfCookingCombo.getValue();
    	}
    	
    	Dish tempDish =Dish.copyOfDish(dish);
    	tempDish.setPrice(tempDish.getPriceBySize(dishSize));
    	tempDish.setSize(dishSize);
    	tempDish.setCooking_level(dishLevelOfCooking);
    	ClientUI.clientLogic.addToOrder(tempDish);
    	showError(false,null);
    	dishAddedPopup.setVisible(true);
    	PauseTransition delay = new PauseTransition(Duration.seconds(2));
    	delay.setOnFinished(e -> dishAddedPopup.setVisible(false) );
    	delay.play();
    	//refresh cart
    	updateCart();
    }
    
    /**
     * Do check out. move to checkout screen 
     *
     * @param event the event
     */
    @FXML
    void doCheckOut(ActionEvent event) {
		IClientFxController nextScreen = new ControllerFX_ClientCheckoutScreen();
		ClientUI.historyStack.pushFxController(this);
		nextScreen.start(ClientUI.parentWindow);
    }
    
    /**
     * Choose size in combo. update the price label according to the chosen size of dish
     *
     * @param event the event
     */
    @FXML
    void chooseSizeInCombo(ActionEvent event) {
    	index = sizeCombo.getSelectionModel().getSelectedIndex();
    	if(index<priceArray.length)
    		price = priceArray[index];    		
    	priceLabel.setText("price: " + price + "$");
    }

	/**
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
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
		updateCart();
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
	
	/**
	 * Update cart according to DB
	 */
	private void updateCart() {
		if(ClientUI.clientLogic.isOrderListEmpty()) {
			return;
		}
		int cartPrice = 0;
		emptyCartLabel1.setVisible(false);
		cartVBox.setVisible(true);
		cartDishesGrid.getChildren().clear();
		//for each dish in order we update the cart to show it
		for(int i = 0; i < ClientUI.clientLogic.getOrderDishes().size(); i++) {
			Dish dish = ClientUI.clientLogic.getOrderDishes().get(i);
			Label dishName = new Label(dish.getName());
			Label dishPrice = new Label(dish.getPrice());
			cartDishesGrid.add(dishName, 0, i);
			cartDishesGrid.add(dishPrice, 1, i);
			cartPrice += Integer.parseInt(dish.getPrice());
		}
		labelTotalPrice.setText(String.valueOf(cartPrice));
	}
    
	/**
	 * Show error msg to screen 
	 *
	 * @param show the show
	 * @param message the message
	 */
	void showError(boolean show, String message) {
		if(message == null || message.equals("") || !show) {
			ErrorMsg.setVisible(false);
			return;
		}
		if(show) {
			ErrorMsg.setVisible(true);
			ErrorMsg.setText(message);
			return;
		}
		ErrorMsg.setVisible(false);
	}

}
