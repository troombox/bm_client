package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utility.entity.Dish;
import utility.entity.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;

/**
 * The Class ControllerFX_ChooseRestaurantscreen.
 * this scren is presented to client, is shows a list of restaurants in biteme, according to chosen category
 */
public class ControllerFX_ChooseRestaurantscreen implements IClientFxController, Initializable{

    /** The split pane. */
    @FXML
    private SplitPane splitPane;

    /** The Head line label. */
    @FXML
    private Label HeadLineLabel;

    /** The cart button. */
    @FXML
    private Button cartButton;

    /** The Error msg. */
    @FXML
    private Label ErrorMsg;

    /** The vbox restaurants. */
    @FXML
    private VBox vboxRestaurants;

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
    
    /** The category. */
    public static String category;

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
     * Show cart.
     *
     * @param event the event
     */
    @FXML
    void showCart(ActionEvent event) {
    	splitPane.setDividerPosition(0, 0.7);
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
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/restaurants.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
        stage.setTitle("Restaurants");
        stage.setScene(scene);
        stage.show();
        
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
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vboxRestaurants.getChildren().clear();
    	vboxRestaurants.setSpacing(20);
    	ArrayList<Restaurant> restaurantsToShow = (ArrayList<Restaurant>) ClientUI.clientLogic.getLastDataRecieved();
    	for (Restaurant r: restaurantsToShow) {
			HBox hboxRestaurant = new HBox();
			hboxRestaurant.setSpacing(20);
			vboxRestaurants.getChildren().add(hboxRestaurant);
			hboxRestaurant.getChildren().add(new Label(r.getResName()));
			Button b = new Button("menu");
			ControllerFX_ChooseRestaurantscreen controller = this;
			b.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	IClientFxController nextScreen = new ControllerFX_MenuScreen();
	            	ControllerFX_MenuScreen.res = r;
	            	//ClientUI.historyStack.pushFxController(controller);
	            	nextScreen.start(ClientUI.parentWindow);
	            }
	        });
			b.setEffect(new DropShadow());
			b.setStyle("-fx-background-color: #ffca28; -fx-background-radius: 100px;");
			hboxRestaurant.getChildren().add(b);
    	}
    	HeadLineLabel.setText(category);
    	
    	updateCart();
	}
	
	/**
	 * Update cart.
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
	

}

