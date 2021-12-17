package client.gui.controllers;

import java.io.IOException;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControllerFX_CategoriesScreen implements IClientFxController {
	
    @FXML
    private Button searchButton;

    @FXML
    private Button cartButton;

    @FXML
    private Button exitCartButton;

    @FXML
    private Label emptyCartLabel;
    
    @FXML
    private Pane mainPane;

    @FXML
    private SplitPane splitPane;
    
    @FXML
    private Button buttonSignOut;

    @FXML
    void doSignOut(ActionEvent event) {
    	ClientUI.clientLogic.logOutUser();
    	ClientUI.historyStack.clearControllerHistory();
    	ClientUI.loginScreen.start(ClientUI.parentWindow);
    }

	@Override
	public void start(Stage stage) {
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
        stage.show();
	}
	
	@FXML
    void exitCart(ActionEvent event) {
		splitPane.setDividerPosition(0, 1);

    }
	
	@FXML
    void viewCart(ActionEvent event) {
		splitPane.setDividerPosition(0, 0.7);
    }
	
	 @FXML
	void searchGui(ActionEvent event) {
		IClientFxController nextScreen = new ControllerFX_ClientSearchscreen();
		ClientUI.historyStack.pushFxController(this);
	    nextScreen.start(ClientUI.parentWindow);

	}
	

}
