package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import utility.entity.Dish;
import utility.entity.Restaurant;
import utility.enums.DataType;
import utility.enums.RequestType;

/**
 * The Class ControllerFX_CategoriesScreen.
 * this screen is shown to the client, it shows all categories of restaurants in biteme
 */
public class ControllerFX_CategoriesScreen implements IClientFxController, Initializable {
	
	
    /** The search button. */
@FXML
    private Button searchButton;

    /** The cart button. */
    @FXML
    private Button cartButton;

    /** The exit cart button. */
    @FXML
    private Button exitCartButton;

    /** The cart V box. */
    @FXML
    private VBox cartVBox;

    /** The cart dishes grid. */
    @FXML
    private GridPane cartDishesGrid;

    /** The label total price. */
    @FXML
    private Label labelTotalPrice;

    /** The empty cart label 1. */
    @FXML
    private Label emptyCartLabel1;
    
    /** The main pane. */
    @FXML
    private Pane mainPane;

    /** The split pane. */
    @FXML
    private SplitPane splitPane;
    
    /** The label hello text. */
    @FXML
    private Label labelHelloText;
    
    /** The approve order pane. */
    @FXML
    private Pane approveOrderPane;

    /** The order number text filed. */
    @FXML
    private TextField orderNumberTextFiled;
    
    /** The Error msg. */
    @FXML
    private Label ErrorMsg;


	/**
	 * Start.
	 *
	 * @param stage the stage
	 */
	@Override
	public void start(Stage stage) {
    	ClientUI.historyStack.pushFxController(this);
        Parent root = null;
        try {
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/CategoriesScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
//      scene.getStylesheets().add(getClass().getResource("/client/client_gui/LoginFxml.css").toExternalForm());
        stage.setTitle("Categories");
        stage.setScene(scene);
        ClientUI.historyStack.setBaseController(this);
        stage.show();
	}
	
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
	 * View cart. shows the cart in the same screen
	 *
	 * @param event the event
	 */
	@FXML
    void viewCart(ActionEvent event) {
		splitPane.setDividerPosition(0, 0.7);
    }
	
	 /**
 	 * move to search restaurant screen 
 	 *
 	 * @param event the event
 	 */
 	@FXML
	void searchGui(ActionEvent event) {
		IClientFxController nextScreen = new ControllerFX_ClientSearchscreen();
		ClientUI.historyStack.pushFxController(this);
	    nextScreen.start(ClientUI.parentWindow);

	}
	 
	 /**
 	 * Gets the restaurants from DB, according to branch of the user, and category
 	 *
 	 * @param category the category
 	 * @return the restaurants
 	 */
 	private void getRestaurants(String category) {
		 ArrayList<String> CategoryBranch = new ArrayList<>();
		 CategoryBranch.add(category);
		 CategoryBranch.add(ClientUI.clientLogic.getLoggedUser().getPersonalBranch());
		 ClientUI.clientLogic.sendMessageToServer(CategoryBranch,
					DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_CATEGORY_RESTAURANT_REQUEST);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("No restaurants");
		    	alert.setHeaderText(null);
		    	alert.setContentText("we dont have " + category + " restaurants in your area. sorry.");
		    	alert.showAndWait();
		    	return;
			}
			if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.RESTAURANTS_LIST) {
				return;
			}
			
			ArrayList<Restaurant> RestaurantsToShow = (ArrayList<Restaurant>)ClientUI.clientLogic.getLastDataRecieved();
	    	
			if(RestaurantsToShow.size() == 0)
			{
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("No restaurants");
		    	alert.setHeaderText(null);
		    	alert.setContentText("we dont have " + category + " restaurants in your area. sorry.");
		    	alert.showAndWait();
		    	return;
			}
			IClientFxController nextScreen = new ControllerFX_ChooseRestaurantscreen();
			ControllerFX_ChooseRestaurantscreen.category = category;
			ClientUI.historyStack.pushFxController(this);
			nextScreen.start(ClientUI.parentWindow);
			
		}
	 
	 /**
 	 * Asian category.
 	 *
 	 * @param event the event
 	 */
 	@FXML
	 void AsianCategory(ActionEvent event) {
		 getRestaurants("asian");
	 }

	    /**
    	 * Coffee category.
    	 *
    	 * @param event the event
    	 */
    	@FXML
	    void CoffeeCategory(ActionEvent event) {
	    	getRestaurants("coffee");
	    }

	    /**
    	 * Desserts category.
    	 *
    	 * @param event the event
    	 */
    	@FXML
	    void DessertsCategory(ActionEvent event) {
	    	getRestaurants("desserts");
	    }

	    /**
    	 * Fast food category.
    	 *
    	 * @param event the event
    	 */
    	@FXML
	    void FastFoodCategory(ActionEvent event) {
	    	getRestaurants("fast food");
	    }

	    /**
    	 * Hummus category.
    	 *
    	 * @param event the event
    	 */
    	@FXML
	    void HummusCategory(ActionEvent event) {
	    	getRestaurants("hummus");
	    }

	    /**
    	 * Indian category.
    	 *
    	 * @param event the event
    	 */
    	@FXML
	    void IndianCategory(ActionEvent event) {
	    	getRestaurants("indian");
	    }

	    /**
    	 * Italian category.
    	 *
    	 * @param event the event
    	 */
    	@FXML
	    void ItalianCategory(ActionEvent event) {
	    	getRestaurants("italian");
	    }

	    /**
    	 * Meat category.
    	 *
    	 * @param event the event
    	 */
    	@FXML
	    void MeatCategory(ActionEvent event) {
	    	getRestaurants("meat");
	    } 
	    
	    /**
    	 * Do sign out.
    	 *
    	 * @param event the event
    	 */
    	@FXML
	    void doSignOut(ActionEvent event) {
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
	    	updateCart();
	    	labelHelloText.setText("Hello " +  ClientUI.clientLogic.getLoggedUser().getFirstName() + " " 
	    	+ ClientUI.clientLogic.getLoggedUser().getLastName() + "!");
	    }
	    
		/**
		 * Update cart according to user's choice current order
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
    	 * Do checkout. move to checkout screen
    	 *
    	 * @param event the event
    	 */
    	@FXML
	    void doCheckout(ActionEvent event) {
			IClientFxController nextScreen = new ControllerFX_ClientCheckoutScreen();
			ClientUI.historyStack.pushFxController(this);
			nextScreen.start(ClientUI.parentWindow);
	    }
	    
	    /**
    	 * Do approve order action. according to user's input - order ID , the status is updates to complete
    	 *
    	 * @param event the event
    	 */
    	@FXML
	    void doApproveOrderAction(ActionEvent event) {
	    	String orderNumber = orderNumberTextFiled.getText();
	    	int inputedOrderNumber;
	    	if(orderNumber.trim().equals("")) {
	    		showErrorForApprovePane("enter order number");
	    		return;
	    	}
			try {
				inputedOrderNumber = Integer.parseInt(orderNumber);
			} catch (NumberFormatException e) {
				showErrorForApprovePane("order number not numerical");
				return;
			}
	    	ArrayList<String> sendData = new ArrayList<>();
	    	sendData.add(String.valueOf(String.valueOf(inputedOrderNumber)));
	    	sendData.add("done");
	    	orderNumberTextFiled.setText("");
			ClientUI.clientLogic.sendMessageToServer(sendData,
					DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_SUPPLIER_UPDATE_ORDER);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE) {
				showErrorForApprovePane(ClientUI.clientLogic.getLastDataRecieved().toString());
			}else {
				approveOrderPane.setVisible(false);
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Success!");
		    	alert.setHeaderText(null);
		    	alert.setContentText("order was approved! :D");
		    	alert.showAndWait();
				return;
			}		
	    }

	    /**
    	 * Do approve order open pane. open popup of approve order by client
    	 *
    	 * @param event the event
    	 */
    	@FXML
	    void doApproveOrderOpenPane(ActionEvent event) {
	    	approveOrderPane.setVisible(true);
	    }

	    /**
    	 * Do cancel order open pane. close popup of approve order
    	 *
    	 * @param event the event
    	 */
    	@FXML
	    void doCancelOrderOpenPane(ActionEvent event) {
	    	approveOrderPane.setVisible(false);
	    }
	    
	    /**
    	 * Show error for approve pane. present givent text in an error message to 3 seconds
    	 *
    	 * @param text the text
    	 */
    	private void showErrorForApprovePane(String text) {
	    	ErrorMsg.setVisible(true);
	    	ErrorMsg.setText(text);
	    	PauseTransition delay = new PauseTransition(Duration.seconds(3));
	    	delay.setOnFinished(e -> ErrorMsg.setVisible(false) );
	    	delay.play();
	    }

}
