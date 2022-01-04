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
import javafx.stage.Stage;

/**
 * The Class ControllerFX_CEOScreen.
 * this screen is shown to CEO, it shows the possible action the CEO can do in biteme 
 */
public class ControllerFX_CEOScreen implements IClientFxController{

    /** The signout btn. */
    @FXML
    private Button signoutBtn;

    /** The back btn. */
    @FXML
    private Button backBtn;

    /** The view quarterly report. */
    @FXML
    private Button viewQuarterlyReport;

    /** The view branches report. */
    @FXML
    private Button viewBranchesReport;
    
    /**
     * Do go back.
     *
     * @param event the event
     */
    @FXML
    void doGoBack(ActionEvent event) {
    	ClientUI.clientLogic.logOutUser();
    	ClientUI.historyStack.clearControllerHistory();
    	ClientUI.loginScreen.start(ClientUI.parentWindow);
    }
  
    /**
     * Do sign out.
     *
     * @param event the event
     */
    @FXML
    void doSignOut(ActionEvent event) {
    	ClientUI.clientLogic.logOutUser();
    	ClientUI.historyStack.clearControllerHistory();
    	ClientUI.loginScreen.start(ClientUI.parentWindow);
    }
    
    /**
     * Move to view branches report screen.
     *
     * @param event the event
     */
    @FXML
    void moveToViewBranchesReport(ActionEvent event) {
    	IClientFxController nextScreen = new ControllerFX_ReportFromBranchesCEO();
    	ClientUI.historyStack.pushFxController(this);
    	nextScreen.start(ClientUI.parentWindow);
    }

    /**
     * Move to view quarterly report screen.
     *
     * @param event the event
     */
    @FXML
    void moveToViewQuarterlyReport(ActionEvent event) {
    	IClientFxController nextScreen = new ControllerFX_ViewQuarterlyReport();
    	ClientUI.historyStack.pushFxController(this);
    	nextScreen.start(ClientUI.parentWindow);
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
        	
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/CeoScreen.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
//      scene.getStylesheets().add(getClass().getResource("/client/client_gui/LoginFxml.css").toExternalForm());
        stage.setTitle("CEO");
        stage.setScene(scene);
        stage.show();
		
	}

}