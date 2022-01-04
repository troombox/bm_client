package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import client.utility.wrappers.MemoryButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.entity.BusinessClientData;
import utility.entity.ClientRefundsData;
import utility.entity.Dish;
import utility.entity.Order;
import utility.entity.User;
import utility.enums.DataType;
import utility.enums.OrderType;
import utility.enums.RequestType;
import utility.enums.UserType;

// TODO: Auto-generated Javadoc
/**
 * The Class ControllerFX_ClientCheckoutScreen.
 */
public class ControllerFX_ClientCheckoutScreen implements IClientFxController, Initializable {
	
	/** The calendar. */
	private Calendar calendar = Calendar.getInstance();
	
	/** The delivery type. */
	private OrderType deliveryType = OrderType.DELIVERY_REGULAR;
	
	/** The delivery price. */
	private final int deliveryPrice = 25;
	
	/** The price. */
	private int price;
	
	/** The final price. */
	private int finalPrice;
	
	/** The total refund value. */
	private int totalRefundValue = 0;
	
	/** The crf. */
	private ClientRefundsData crf;
	
	/** The bcd. */
	private BusinessClientData bcd = null;
	
    /** The Warning msg. */
    @FXML
    private Label WarningMsg;
	
    /** The pick early delivery. */
    @FXML
    private RadioButton pickEarlyDelivery;

    /** The pick regualr delivery. */
    @FXML
    private RadioButton pickRegualrDelivery;

    /** The pick pickup delivery. */
    @FXML
    private RadioButton pickPickupDelivery;

    /** The date picker. */
    @FXML
    private DatePicker datePicker;

    /** The dish grid pane. */
    @FXML
    private GridPane dishGridPane;

    /** The time picker. */
    @FXML
    private ComboBox<String> timePicker;

    /** The type of delivery. */
    @FXML
    private ComboBox<String> typeOfDelivery;

    /** The txt address. */
    @FXML
    private TextField txtAddress;

    /** The txt name. */
    @FXML
    private TextField txtName;

    /** The txt phone. */
    @FXML
    private TextField txtPhone;

    /** The label delivery. */
    @FXML
    private Label labelDelivery;

    /** The label final price. */
    @FXML
    private Label labelFinalPrice;
    
    /** The txt price pre discount. */
    @FXML
    private Label txtPricePreDiscount;

    /** The button pay. */
    @FXML
    private Button buttonPay;
    
    /** The txt final price. */
    @FXML
    private Label txtFinalPrice;
    
    /** The txt delivery price. */
    @FXML
    private Label txtDeliveryPrice;
    
    /** The confirmation pane. */
    @FXML
    private Pane confirmationPane;
    
    /** The business pane. */
    @FXML
    private Pane businessPane;
    
    /** The business choice. */
    @FXML
    private Pane businessChoice;
    
    /** The button business no. */
    @FXML
    private Button buttonBusinessNo;
    
    /** The button business yes. */
    @FXML
    private Button buttonBusinessYes;
    
    /** The button cancel. */
    @FXML
    private Button buttonCancel;
    
    /** The txt confirm delivery. */
    @FXML
    private Label txtConfirmDelivery;

    /** The txt confirm time. */
    @FXML
    private Label txtConfirmTime;

    /** The txt confirm date. */
    @FXML
    private Label txtConfirmDate;

    /** The txt confirm price. */
    @FXML
    private Label txtConfirmPrice;

    /** The button confirm pay. */
    @FXML
    private Button buttonConfirmPay;
    
    /** The button confirm pay business. */
    @FXML
    private Button buttonConfirmPayBusiness;

    /** The button cancel business. */
    @FXML
    private Button buttonCancelBusiness;
    
    /** The label B cfinal price. */
    @FXML
    private Label labelBCfinalPrice;

    /** The label B ccurrent budget. */
    @FXML
    private Label labelBCcurrentBudget;

    /** The vbox field. */
    @FXML
    private VBox vboxField;
    
    /** The sucessfully sent pane. */
    @FXML
    private Pane sucessfullySentPane;

    /** The button success go back. */
    @FXML
    private Button buttonSuccessGoBack;
    
    /** The label refunds value. */
    @FXML
    private Label labelRefundsValue;
    
    /** The pick shared delivery. */
    @FXML
    private RadioButton pickSharedDelivery;
    
    /** The Error msg. */
    @FXML
    private Label ErrorMsg;

    /**
     * Do payment.
     *
     * @param event the event
     */
    @FXML
    void doPayment(ActionEvent event) {
    	//validate the inputs:
    	if(deliveryType == OrderType.UNKNOWN) {
    		showError(true, "Choose delivery type!");
    		return;
    	}
    	if(datePicker.getValue() == null) {
    		showError(true, "pick a date!");
    		return;
    	}
    	if(timePicker.getValue() == null) {
    		showError(true, "pick time!");
    		return;
    	}
    	if(!(deliveryType == OrderType.PICKUP) && typeOfDelivery.getValue() == null) {
    		showError(true, "Choose kind of delivery!");
    		return;
    	}
    	if(!(deliveryType == OrderType.PICKUP) && txtAddress.getText().equals("")) {
    		showError(true, "Choose address!");
    		return;
    	}
    	if(txtName.getText().equals("")) {
    		showError(true, "Type in your name");
    		return;
    	}
    	if(txtPhone.getText().equals("")) {
    		showError(true, "Type in your phone");
    		return;
    	}
    	confirmationPane.setVisible(true);
    	vboxField.setDisable(true);
    	txtConfirmDelivery.setText(deliveryType.toString());
    	txtConfirmDate.setText(datePicker.getValue().toString());
    	txtConfirmTime.setText(timePicker.getValue());
    	int priceToConfirm = price;
    	if(!(deliveryType == OrderType.PICKUP)) {
    		priceToConfirm+=deliveryPrice;
    	}
    	if(deliveryType == OrderType.DELIVERY_EARLY) {
    		priceToConfirm = (int) Math.round(priceToConfirm * 0.9);
    	}
    	txtConfirmPrice.setText(String.valueOf(priceToConfirm));
    	showError(false, null);
    }
    
    /**
     * Do cancel.
     *
     * @param event the event
     */
    @FXML
    void doCancel(ActionEvent event) {
    	confirmationPane.setVisible(false);
    	vboxField.setDisable(false);
    }
    
    /**
     * Do confirm payment.
     *
     * @param event the event
     */
    @FXML
    void doConfirmPayment(ActionEvent event) {
    	if(ClientUI.clientLogic.getLoggedUser().getUserType() == UserType.CLIENT_BUSINESS) {
    		confirmationPane.setVisible(false);
    		businessChoice.setVisible(true);
//    		sucessfullySentPane.setVisible(true);
    	}else {
    		doSendOrderToServer(true); 
    	}
    }
    
    /**
     * Do bno.
     *
     * @param event the event
     */
    @FXML
    void doBno(ActionEvent event) {
    	businessChoice.setVisible(false);
    	doSendOrderToServer(true);
    }

    /**
     * Do byes.
     *
     * @param event the event
     */
    @FXML
    void doByes(ActionEvent event) {
    	ClientUI.clientLogic.sendMessageToServer(String.valueOf(ClientUI.clientLogic.getLoggedUser().getBuisnessCode()), 
    			DataType.SINGLE_TEXT_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_BUSINESS_CLIENT_DATA);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO error handling
			e.printStackTrace();
		}
    	if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE) {
    		showError(true,ClientUI.clientLogic.getTypeOfLastDataRecieved().toString());
    	}
    	businessChoice.setVisible(false);
    	businessPane.setVisible(true);
    	if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.BUSINESS_CLIENT_DATA) {
    		System.out.println("ControllerFX_ClientCheckout: doByes() - last data is not BCD");
    		//something went *very* wrong!
    		return;
    	}
    	bcd = (BusinessClientData)ClientUI.clientLogic.getLastDataRecieved();
    	labelBCcurrentBudget.setText(String.valueOf(bcd.getBudget()));
    	labelBCfinalPrice.setText(String.valueOf(finalPrice));
    }
    

    /**
     * Do cancel business.
     *
     * @param event the event
     */
    @FXML
    void doCancelBusiness(ActionEvent event) {
    	businessPane.setVisible(false);
    	confirmationPane.setVisible(true);
    }

    /**
     * Do confirm payment business.
     *
     * @param event the event
     */
    @FXML
    void doConfirmPaymentBusiness(ActionEvent event) {
    	businessPane.setVisible(false);
    	//do payment
    	doSendOrderToServer(false);
    }
    
    /**
     * Do success go back.
     *
     * @param event the event
     */
    @FXML
    void doSuccessGoBack(ActionEvent event) {
    	IClientFxController ctrl = ClientUI.historyStack.getBaseController();
    	ClientUI.historyStack.clearControllerHistory();
    	ClientUI.clientLogic.clearCurrentOrder();
    	ctrl.start(ClientUI.parentWindow);
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
	
    /**
     * Do go back.
     *
     * @param event the event
     */
    @FXML
    void doGoBack(ActionEvent event) {
    	ClientUI.historyStack.popFxController().start(ClientUI.parentWindow);
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
     * Do pick early delivery.
     *
     * @param event the event
     */
    @FXML
    void doPickEarlyDelivery(ActionEvent event) {
		if(pickEarlyDelivery.isSelected()) {
    		deliveryType = OrderType.DELIVERY_EARLY;
    		pickRegualrDelivery.setSelected(false);
    		pickPickupDelivery.setSelected(false);
      		pickSharedDelivery.setSelected(false);
    		typeOfDelivery.setDisable(false);
    		txtAddress.setDisable(false);
    		//checking system time
    		setTimeComboBox(calendar.get(Calendar.HOUR_OF_DAY) + 2, calendar.get(Calendar.MINUTE));
		}else {
			deliveryType = OrderType.UNKNOWN;
			typeOfDelivery.setDisable(true);
			setTimeComboBox(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
		}
		setPrices();
    }

    /**
     * Do pick pickup delivery.
     *
     * @param event the event
     */
    @FXML
    void doPickPickupDelivery(ActionEvent event) {
    	setTimeComboBox(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
		if(pickPickupDelivery.isSelected()) {
    		deliveryType = OrderType.PICKUP;
    		pickRegualrDelivery.setSelected(false);
    		pickEarlyDelivery.setSelected(false);
      		pickSharedDelivery.setSelected(false);
    		typeOfDelivery.setDisable(true);
    		txtAddress.setDisable(true);
		}else {
			deliveryType = OrderType.UNKNOWN;
			txtAddress.setDisable(false);
			typeOfDelivery.setDisable(false);
		}
		setPrices();
    }

    /**
     * Do pick regualr delivery.
     *
     * @param event the event
     */
    @FXML
    void doPickRegualrDelivery(ActionEvent event) {
    	setTimeComboBox(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
		if(pickRegualrDelivery.isSelected()) {
    		deliveryType = OrderType.DELIVERY_REGULAR;
    		pickPickupDelivery.setSelected(false);
    		pickEarlyDelivery.setSelected(false);
      		pickSharedDelivery.setSelected(false);
    		typeOfDelivery.setDisable(false);
    		txtAddress.setDisable(false);
  
		}else {
			deliveryType = OrderType.UNKNOWN;
			typeOfDelivery.setDisable(true);
		}
		setPrices();
    }

    /**
     * Do shared delivery.
     *
     * @param event the event
     */
    @FXML
    void doSharedDelivery(ActionEvent event) {
    	setTimeComboBox(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
		if(pickSharedDelivery.isSelected()) {
    		deliveryType = OrderType.SHARED;
    		pickPickupDelivery.setSelected(false);
    		pickEarlyDelivery.setSelected(false);
    		pickRegualrDelivery.setSelected(false);
    		typeOfDelivery.setDisable(false);
    		txtAddress.setDisable(false);
		}else {
			deliveryType = OrderType.UNKNOWN;
			pickSharedDelivery.setSelected(false);
		}
		setPrices();
    }

	
	/**
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(ClientUI.clientLogic.getLoggedUser().getUserType() == UserType.CLIENT_BUSINESS) {
			pickSharedDelivery.setVisible(true);
		}
		setRefundsValue();
		fillInCart();
		setTimeComboBox(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
		setDeliveryTypeComboBox();
		datePicker.setValue(LocalDate.now());
		User user = ClientUI.clientLogic.getLoggedUser();
		txtName.setText(user.getFirstName() + " " + user.getLastName());
		txtPhone.setText(user.getPhone());
		
	}
	
 	/**
	  * Sets the delivery type combo box.
	  */
	 private void setDeliveryTypeComboBox() {
 		ArrayList<String> deliveryType = new ArrayList<String>();
 		deliveryType.add("Regular");
 		deliveryType.add("Robotic");
 		ObservableList<String> list = FXCollections.observableArrayList(deliveryType);
 		typeOfDelivery.getItems().addAll(list);
 	}
	
 	/**
	  * Sets the time combo box.
	  *
	  * @param startingHour the starting hour
	  * @param startingMinute the starting minute
	  */
	 private void setTimeComboBox(int startingHour, int startingMinute) {
 		timePicker.getItems().clear();
 		ArrayList<String> time = new ArrayList<String>();
 		if(startingMinute < 30) {
 			time.add(String.format("%02d",startingHour%24) + ":30");
 		}
 		for(int i = ((startingHour + 1)%25); i < 24; i++) {
 			String hour = String.format("%02d", i);
 			time.add(hour + ":00");
 			time.add(hour + ":30");
 		}
 		ObservableList<String> list = FXCollections.observableArrayList(time);
 		timePicker.getItems().addAll(list);
 		timePicker.setValue(timePicker.getItems().get(0));
 	}
 	
 	/**
	  * Sets the refunds value.
	  */
	 public void setRefundsValue(){
 		ArrayList<String> requestForRefunds = new ArrayList<String>();
 		requestForRefunds.add(String.valueOf(ClientUI.clientLogic.getLoggedUser().getUser_ID()));
 		requestForRefunds.addAll(ClientUI.clientLogic.getOrder().restaurantsInOrderList());
 		ClientUI.clientLogic.sendMessageToServer(requestForRefunds,DataType.ARRAYLIST_STRING,RequestType.CLIENT_REQUEST_TO_SERVER_GET_CLIENT_REFUNDS_DATA);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO error handling
			e.printStackTrace();
		}
    	if(ClientUI.clientLogic.getTypeOfLastDataRecieved() != DataType.CLIENT_REFUNDS_DATA) {
    		System.out.println("ControllerFX_ClientCheckout: setRefundsValue() - last data is not CRF " 
    				+ ClientUI.clientLogic.getTypeOfLastDataRecieved().toString());
    		//something went *very* wrong!
    		return;
    	}
    	crf = (ClientRefundsData)ClientUI.clientLogic.getLastDataRecieved();
    	labelRefundsValue.setText(String.valueOf(crf.totalRefundsValue()));
    	totalRefundValue = crf.totalRefundsValue();
    	
    	
 	}
	
	/**
	 * Fill in cart.
	 */
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
                    //if this was last order:
                    if(ClientUI.clientLogic.getOrderDishes().size() == 0) {
                        ClientUI.clientLogic.clearCurrentOrder();
                        ClientUI.historyStack.clearControllerHistory();
                        ClientUI.historyStack.getBaseController().start(ClientUI.parentWindow);
                    }
                    fillInCart();
                }
            });
            dishGridPane.add(newButton, 2, i);
            cartPrice += Integer.parseInt(dish.getPrice());
        }
        price = cartPrice;
        setPrices();
        //show warning if needed:
        WarningMsg.setVisible(false);
        if(ClientUI.clientLogic.getOrder().numberOfRestaurantsInOrder() != 1) {
            WarningMsg.setText("You are ordering from " +ClientUI.clientLogic.getOrder().numberOfRestaurantsInOrder()+ " restaurants!");
            WarningMsg.setVisible(true);
        }
        if(ClientUI.clientLogic.getOrderDishes().size() == 1 ) {
            WarningMsg.setVisible(true);
            WarningMsg.setText("Removing last dish will cancel the order!");
        }
    }
	
	
	/**
	 * Sets the prices.
	 */
	private void setPrices() {
		int numberOfRes = ClientUI.clientLogic.getOrder().numberOfRestaurantsInOrder();
		txtPricePreDiscount.setText(String.valueOf(price));
		if (deliveryType == OrderType.UNKNOWN) {
			labelFinalPrice.setText(String.valueOf(price - totalRefundValue));
			labelDelivery.setText("0");
			finalPrice = price;
		}else if(deliveryType == OrderType.DELIVERY_EARLY){
			labelDelivery.setText(String.valueOf(deliveryPrice * numberOfRes));
			labelFinalPrice.setText(String.valueOf(Math.round((price * numberOfRes)*0.9 + deliveryPrice - totalRefundValue)));
			finalPrice = (int) Math.round((price * numberOfRes) *0.9  + deliveryPrice - totalRefundValue);
		}else if(deliveryType == OrderType.DELIVERY_REGULAR || deliveryType == OrderType.SHARED){
			labelDelivery.setText(String.valueOf(deliveryPrice * numberOfRes));
			labelFinalPrice.setText(String.valueOf(price + deliveryPrice * numberOfRes - totalRefundValue));
			finalPrice = price + deliveryPrice * numberOfRes - totalRefundValue;
		}else{
			labelDelivery.setText("0");
			labelFinalPrice.setText(String.valueOf(price - totalRefundValue));
			finalPrice = price - totalRefundValue;
		}
	}
	
	/**
	 * Do send order to server.
	 *
	 * @param isPrivate the is private
	 */
	private void doSendOrderToServer(boolean isPrivate) {
    	//if payment is confirmed we send the orders to the server
    	ArrayList<Dish> currentOrder = ClientUI.clientLogic.getOrderDishes();
    	ArrayList<Order> ordersToSend = new ArrayList<Order>();
    	//check for each dish if there's an order from restaurant that dish belongs to.
    	for(Dish dish : currentOrder) {
    		boolean flagDishAdded = false;
    		for(Order order : ordersToSend) {
    			if(flagDishAdded == false && order.getRestaurantID() == Integer.valueOf(dish.getRes_ID())) {
    				//if found, add the dish
    				order.addDish(dish);
    				order.setTotalPrice(order.getTotalPrice() + Integer.valueOf(dish.getPrice()));
    				order.setOrderPrice(order.getOrderPrice() + Integer.parseInt(dish.getPrice()));
    				flagDishAdded = true;
    			}
    		}
			//if we get here - the dish was not added to any order:
    		if(flagDishAdded == false) {
    			//fix to the case of Name being one string without a space
    			String[] split = txtName.getText().split(" ", 2);
    			String[] name = new String[2];
    			name[0] = split[0];
    			if(split[1] != null) {
    				name[1] = split[1];
    			}else {
    				name[1] = split[0];
    			}
    			int deliveryPriceForOrder = (deliveryType != OrderType.PICKUP) ? deliveryPrice : 0;
    			//creating order
    			String addressText = (txtAddress.getText() == null) ? "" : txtAddress.getText();
    			Order newOrder = new Order(name[0], name[1], txtPhone.getText(), addressText, Integer.valueOf(dish.getRes_ID()), deliveryType);
    			newOrder.addDish(dish);
    			newOrder.setTotalPrice(Integer.parseInt(dish.getPrice()));
    			newOrder.setOrderPrice(Integer.parseInt(dish.getPrice()));
    			newOrder.setDeliveryPrice(deliveryPriceForOrder);
    			newOrder.setPrivateOrder(isPrivate);
    			newOrder.setOrderingUserID(ClientUI.clientLogic.getLoggedUser().getUser_ID());
    			String orderTime = datePicker.getValue().toString() + " " + timePicker.getValue() + ":00";
    			newOrder.setTimeOfOrder(orderTime);
    			ordersToSend.add(newOrder);
    		}
    	}
    	ClientRefundsData updated = new ClientRefundsData(ClientUI.clientLogic.getLoggedUser().getUser_ID());
    	for(Order order : ordersToSend) {
    		//applying discount if needed:
    		if(deliveryType == OrderType.DELIVERY_EARLY) {
    			order.setTotalPrice((int)(order.getTotalPrice() * 0.9));
    		}
    		
    		//doing the refunds math
    		int refundValueLeft = 0;
    		if((order.getTotalPrice() - crf.getRefundByResID(order.getRestaurantID())) >= 0 ) {
    			order.setTotalPrice(order.getTotalPrice() - crf.getRefundByResID(order.getRestaurantID()));
    		}
    		else {
    			order.setTotalPrice(0);
    			refundValueLeft = crf.getRefundByResID(order.getRestaurantID()) - order.getOrderPrice();	
    		}
    		//if there was a refund value for that ResID:
    		if(crf.getRefundByResID(order.getRestaurantID()) > 0) {
    			updated.addResRefundPair(order.getRestaurantID(), refundValueLeft);
    		}
    		//take care of the budget of business client
    		if(!isPrivate && bcd != null) {
    			int newBudget = 0;
    			if(bcd.getBudget() >= order.getOrderPrice()) {
    				newBudget = bcd.getBudget() - order.getOrderPrice();
    				order.setTotalPrice(0);
    			} else {
    				order.setTotalPrice(order.getTotalPrice()- bcd.getBudget());
    			}
				ArrayList<String> updateMessage = new ArrayList<>();
				updateMessage.add(String.valueOf(ClientUI.clientLogic.getLoggedUser().getUser_ID()));
				updateMessage.add(String.valueOf(newBudget));
				ClientUI.clientLogic.sendMessageToServer(updateMessage, DataType.ARRAYLIST_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_BUSINESS_CLIENT_BUDGED_UPDATE);
    		}
    		ClientUI.clientLogic.sendMessageToServer(order, DataType.ORDER, RequestType.CLIENT_REQUEST_TO_SERVER_WRITE_NEW_TO_DB);
    	}
    	//send aggregated refunds data for all the orders:
    	ClientUI.clientLogic.sendMessageToServer(updated, DataType.CLIENT_REFUNDS_DATA, RequestType.CLIENT_REQUEST_TO_SERVER_UPDATE_REFUND_AMOUNT_AFTER_REFUNDS_USED);
    	confirmationPane.setVisible(false);
    	vboxField.setDisable(false);
    	//Successfully sent, open confirmation pane
    	sucessfullySentPane.setVisible(true);
	}
	
	/**
	 * Show error.
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
