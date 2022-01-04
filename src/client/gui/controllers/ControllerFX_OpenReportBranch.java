package client.gui.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.ResourceBundle;

import client.gui.logic.ClientUI;
import client.interfaces.IClientFxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import utility.enums.DataType;
import utility.enums.RequestType;


/**
 * The Class ControllerFX_OpenReportBranch.
 */
public class ControllerFX_OpenReportBranch implements IClientFxController,Initializable{

    /** The title txt. */
    @FXML
    private Label titleTxt;

    /** The signout btn. */
    @FXML
    private Button signoutBtn;

    /** The back btn. */
    @FXML
    private Button backBtn;

    /** The combo type. */
    @FXML
    private ComboBox<String> comboType;

    /** The combo year. */
    @FXML
    private ComboBox<String> comboYear;

    /** The combo month. */
    @FXML
    private ComboBox<String> comboMonth;

    /** The open report btn. */
    @FXML
    private Button openBtn;

    /** The result label. */
    @FXML
    private Label resultLabel;

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
    	ClientUI.historyStack.clearControllerHistory();
    	ClientUI.loginScreen.start(ClientUI.parentWindow);
    }

    /**
     * Open report request according to given month, year and report type
     *
     * @param event the event
     */
    @FXML
    void openReportRequest(ActionEvent event) {
    	String personalBranch = ClientUI.clientLogic.getLoggedUser().getPersonalBranch();
    	if(!checkValidInputForBusiness(comboType.getValue(),comboYear.getValue(),comboMonth.getValue())) {
    		return;
    	}
    	DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));
    	File selectedDirectory = directoryChooser.showDialog(null);
    	String path = selectedDirectory.getAbsolutePath(); 
    	ArrayList<String> reportRequest = new ArrayList<String>();
    	reportRequest.add(RequestType.CLIENT_REQUEST_TO_SERVER_OPEN_REPORT.toString());
    	reportRequest.add(DataType.REPORT.toString());
    	reportRequest.add(comboType.getValue());
    	reportRequest.add(comboYear.getValue());
    	reportRequest.add(comboMonth.getValue());
    	reportRequest.add(path);
    	reportRequest.add(personalBranch);
    	ClientUI.clientLogic.sendMessageToServer(reportRequest,DataType.REPORT,RequestType.CLIENT_REQUEST_TO_SERVER_OPEN_REPORT);
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO error handling
			e.printStackTrace();
		}
    	if(ClientUI.clientLogic.getTypeOfLastDataRecieved() == DataType.ERROR_MESSAGE) {
    		resultLabel.setText(ClientUI.clientLogic.getLastDataRecieved().toString());
    		return;
    	}
    	else {
    		String result = (String)ClientUI.clientLogic.getLastDataRecieved();
    		  byte[] decode = Base64.getDecoder().decode(result);
    		  String date = comboYear.getValue() + "-" + comboMonth.getValue();
    		  String fileName = "\\" + comboType.getValue() + "(" + date + ").pdf";
      		  File report = new File(path + fileName);
      		  int b;
      		  try {
				FileOutputStream output = new FileOutputStream(report);
				 output.write(decode, 0, decode.length);
				 output.close();
				 if(report.exists()) {
					Desktop desktop = Desktop.getDesktop();
		            desktop.open(report);
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                 
              
    	}
    }

	/**
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String[] types = {"Income","Order","Performance"};
		comboType.getItems().addAll(types);
		Calendar cal = Calendar.getInstance(); 
		int year = cal.get(Calendar.YEAR);
		String[] years = {String.valueOf(year),String.valueOf(year-1),String.valueOf(year-2)};
		comboYear.getItems().addAll(years);
		String[] months = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		comboMonth.getItems().addAll(months);
		
	}
	
	  /**
  	 * Check valid input for business. all fields must be full 
  	 *
  	 * @param type the type
  	 * @param year the year
  	 * @param month the month
  	 * @return true, if successful
  	 */
  	private boolean checkValidInputForBusiness(String type,String year, String month) {
			if (type == null) {
				resultLabel.setText("You must choose type of report");
				return false;
	   	 	}
			if(year == null) {
				resultLabel.setText("You must choose year");
				return false;
			}
			if(month == null){
				resultLabel.setText("You must choose month");
				return false;
			}
			
			return true;
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
        	
			root = FXMLLoader.load(getClass().getResource("/client/gui/fxml/openReportBranchManager.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
//      scene.getStylesheets().add(getClass().getResource("/client/client_gui/LoginFxml.css").toExternalForm());
        stage.setTitle("Open Report");
        stage.setScene(scene);
        stage.show();
	}

}
