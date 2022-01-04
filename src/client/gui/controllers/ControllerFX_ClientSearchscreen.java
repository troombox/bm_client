package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.entity.Dish;
import utility.entity.Restaurant;
import utility.enums.DataType;
import utility.enums.ErrorType;
import utility.enums.RequestType;

/**
 * The Class ControllerFX_ClientSearchscreen.
 * this screen is presented to client, he can search by a restaurant in his branch according to it's name. 
 * suitable restaurants will show in the screen (restarants that contains the given txt)
 */
public class ControllerFX_ClientSearchscreen implements IClientFxController, Initializable{
	
	    /** The back button. */
    	@FXML
	    private Button backButton;

	    /** The main pane. */
    	@FXML
	    private Pane mainPane;

	    /** The split pane. */
    	@FXML
	    private SplitPane splitPane;

	    /** The search text box. */
    	@FXML
	    private TextField searchTextBox;

	    /** The search button. */
    	@FXML
	    private Button searchButton;

	    /** The show cart button. */
    	@FXML
	    private Button showCartButton;

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
	    
	    /** The Error msg. */
    	@FXML
	    private Label ErrorMsg;
	    
	    /** The vbox restaurants. */
    	@FXML
	    private VBox vboxRestaurants;
	    

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
    	 * Search. present list of suitable restaurants in his branch according to given search txt
    	 *
    	 * @param event the event
    	 */
    	@FXML
	    void search(ActionEvent event) {
	    	String searchText = searchTextBox.getText();
	    	
	    	 ArrayList<String> SearchBranch = new ArrayList<>();
	    	 SearchBranch.add(searchText);
	    	 SearchBranch.add(ClientUI.clientLogic.getLoggedUser().getPersonalBranch());
			 
	    	if((searchText.trim().isEmpty())){
				ErrorMsg.setVisible(true);
				ErrorMsg.setText("Please enter a restaurant to search");
				return;
			}
			ClientUI.clientLogic.sendMessageToServer(SearchBranch,
					DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_SEARCH_RESTAURANT_REQUEST);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
			{
				vboxRestaurants.getChildren().clear();
				ErrorMsg.setVisible(true);
				String errorString = ClientUI.clientLogic.getLastDataRecieved().toString();
				switch(errorString) {
	    			case "INVALID_CREDENTIALS_RESTAURANT_NOT_FOUND":
	    				errorString = "We didnt find any restaurants. Please try again";
	    				break;
	    			default:
	    				errorString = ErrorType.UNKNOWN.toString();
	    		}
	    		ErrorMsg.setText(errorString);
	    		return;
			}
			if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.RESTAURANTS_LIST) {
				System.out.println("Houston, we got a problem!");
				return;
			}
			
			ArrayList<Restaurant> RestaurantsToShow = (ArrayList<Restaurant>)ClientUI.clientLogic.getLastDataRecieved();
	    	
			if(RestaurantsToShow.size() == 0)
			{
		    	vboxRestaurants.getChildren().clear();
				ErrorMsg.setVisible(true);
	    		ErrorMsg.setText("We didnt find any restaurants. Please try again");
	    		return;
			}
			
			ErrorMsg.setVisible(false);
	    	vboxRestaurants.getChildren().clear();
	    	vboxRestaurants.setSpacing(20);
	    	for (Restaurant r: RestaurantsToShow) {
				HBox hboxRestaurant = new HBox();
				hboxRestaurant.setSpacing(20);
				vboxRestaurants.getChildren().add(hboxRestaurant);
				hboxRestaurant.getChildren().add(new Label(r.getResName()));
				Button b = new Button("menu");
				ControllerFX_ClientSearchscreen searchscreen = this;
				b.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	IClientFxController nextScreen = new ControllerFX_MenuScreen();
		            	ControllerFX_MenuScreen.res = r;
		            	ClientUI.historyStack.pushFxController(searchscreen);
		            	nextScreen.start(ClientUI.parentWindow);
		            }
		        });
				b.setEffect(new DropShadow());
				b.setStyle("-fx-background-color: #ffca28; -fx-background-radius: 100px;");
				hboxRestaurant.getChildren().add(b);
	    	}
			return;
			
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
    	 * Show cart in the same screen 
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
				root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/search_gui.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
	        Scene scene = new Scene(root);
	        stage.setTitle("Search");
	        stage.setScene(scene);
	        stage.show();
			
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

		/**
		 * Initialize.
		 *
		 * @param location the location
		 * @param resources the resources
		 */
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			updateCart();
			
		}

	}


