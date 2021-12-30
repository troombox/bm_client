package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.interfaces.IClientFxController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.entity.Dish;
import utility.entity.Order;

public class ControllerFX_ActiveOrdersShowDishesInOrderScreen implements IClientFxController, Initializable{

    @FXML
    private Label headline;

    @FXML
    private GridPane dishesGrid;
    
    public static Order order;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		headline.setText("order number " + order.getOrderID());
		ArrayList<Dish> dishes = order.getDishesInOrder();
		if(dishes.isEmpty()) return; //order has no dishes - empty
		int i=1;
		for(Dish dish: dishes) {
			Label name = new Label(dish.getName());
			name.setStyle("-fx-font-size: 14;");
			if(name.getText() == null ) name.setText("N/A");
			dishesGrid.add(name, 0, i);
			
			Label type = new Label(dish.getType());
			type.setStyle("-fx-font-size: 14;");
			if(type.getText() == null ) type.setText("N/A");
			dishesGrid.add(type, 1, i);
			
			Label size = new Label(dish.getSize());
			size.setStyle("-fx-font-size: 14;");
			if(size.getText() == null ) size.setText("N/A");
			dishesGrid.add(size, 2, i);
			
			Label cookingLevel = new Label(dish.getCooking_level());
			cookingLevel.setStyle("-fx-font-size: 14;");
			if(cookingLevel.getText() == null )cookingLevel.setText("N/A");
			dishesGrid.add(cookingLevel, 3, i);
			
			Label exceptions = new Label(dish.getExceptions());
			exceptions.setStyle("-fx-font-size: 14;");
			if(exceptions.getText() == null ) exceptions.setText("N/A");
			dishesGrid.add(exceptions, 4, i);
			
			i++;
		}
		
	}

	@Override
	public void start(Stage stage) {
		Parent root = null;
        try {
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/ActiveOrdersShowDishesInOrderScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
        stage.setTitle("show dishes in order");
        stage.setScene(scene);
        stage.show();
		
	}

}
