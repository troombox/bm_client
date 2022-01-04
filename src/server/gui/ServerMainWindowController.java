package server.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * The Class ServerMainWindowController.
 */
public class ServerMainWindowController {

	/** The indicator server status. */
	@FXML
	private Circle indicatorServerStatus;

	/** The text server status. */
	@FXML
	private TextField txtServerStatus;

	/** The text clients connected. */
	@FXML
	private TextField txtClientsConnected;

	/** The input text schema name. */
	@FXML
	private TextField inputTxtSchemaName;

	/** The input text user name. */
	@FXML
	private TextField inputTxtUserName;

	/** The button start server. */
	@FXML
	private Button buttonStartServer;

    /** The button server listening. */
    @FXML
    private Button buttonServerListening;

	/** The button stop server. */
	@FXML
	private Button buttonStopServer;

	/** The button open log. */
	@FXML
	private Button buttonOpenLog;

	/** The button quit. */
	@FXML
	private Button buttonQuit;
	
    /** The input text server port. */
    @FXML
    private TextField inputTxtServerPort;

    /** The checkBox default values. */
    @FXML
    private CheckBox chkboxDefaultValues;

    /** The input text password. */
    @FXML
    private PasswordField inputTxtPassword;
    
    /** The label show message. */
    @FXML
    private Label labelShowMessage;
	

    /** The import button. */
    @FXML
    private Button importBtn;
    
	/** The log text. */
	@FXML
	TextArea logText = new TextArea();
	
	
	/** The server UI. */
	//Used to gain access to functions stored in ServeUI class
	private ServerUI serverUI; //not in use as the server instance is static
	
	/** The flag log window open. */
	//Flags
	private boolean flagLogWindowOpen = false;
	
	/** The flag server running. */
	private boolean flagServerRunning = false;
	
	/** The flag server listening. */
	private boolean flagServerListening = false;
	
	/** The flag server U iset. */
	private boolean flagServerUIset = false; //not in use as the server instance is static
	

	/**
	 * open the log that shows everything that happens in the server.
	 *
	 * @param event the event
	 */
	@FXML
	void doOpenLog(ActionEvent event) {
		if(flagLogWindowOpen)
			return;
    	Pane root = new Pane();
    	root.getChildren().add(logText);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Server Log");
        stage.setResizable(false);
        stage.setOnCloseRequest(
        	      e -> flagLogWindowOpen = false
        	  );
        stage.show();
        flagLogWindowOpen = true;
	}

	/**
	 * Do quit.
	 *
	 * @param event the event
	 */
	@FXML
	void doQuit(ActionEvent event) {
		doStopServer(null);
		System.exit(0);
	}

    /**
     * Do server listening to clients
     *
     * @param event the event
     */
    @FXML
    void doServerListening(ActionEvent event) {
    	if(!flagServerRunning) {
    		setLabelShowMessageErrorMessage("Server not running, can't stop listening");
    		return;
    	}
    	if(flagServerListening) {
    		ServerUI.stopServerListening();
    		setLabelShowMessageSucceessMessage("Server stoped listening");
    		flagServerListening = false;
    		buttonServerListening.setText("Start Listening");
    		return;
    	}
		try {
			ServerUI.startServerListening();
		} catch (Exception e) {
			setLabelShowMessageErrorMessage("Error Starting Listening, Exception was caught");
			e.printStackTrace();
		}
		setLabelShowMessageSucceessMessage("Server started listening");
		flagServerListening = true;
		buttonServerListening.setText("Stop Listening");
		return;
    	
    }

    
    /**
     * creates instance of the server and connects to the DB
     * @param event the event
     */
    //This function starts the server
	@FXML
	void doStartServer(ActionEvent event) {
		if(flagServerRunning) {
			setLabelShowMessageErrorMessage("Server already running, can't start it again");
			return;
		}
	
		//input parsing
		String schemaName = inputTxtSchemaName.getText().trim();
		String userName = inputTxtUserName.getText().trim();
		String password = inputTxtPassword.getText().trim();
		String port = inputTxtServerPort.getText().trim();
		if(schemaName.isEmpty() || userName.isEmpty() || password.isEmpty() || port.isEmpty()) {
			setLabelShowMessageErrorMessage("Fill in all the details to Start Server");
			return;
		}
		if (!port.matches("[0-9]+")) {
			setLabelShowMessageErrorMessage("Server Port should be in range of 0 to 65535");
			return;
		}
		int portNum = Integer.valueOf(port);
		if(portNum < 0 || portNum > 65535) {
			setLabelShowMessageErrorMessage("Server Port should be in range of 0 to 65535");
			return;
		}
		//STARTING SERVER
		try {
			ServerUI.startServer(portNum, schemaName, userName, password, this);
		} catch (Exception e) {
			setLabelShowMessageErrorMessage("Error Starting Server, Exception was caught");
			e.printStackTrace();
			return;
		}
		//GUI UPDATE
		//importBtn.setDisable(false);
		setLabelShowMessageSucceessMessage("Server was started");
		indicatorServerStatus.setFill(Color.LIME);
		txtServerStatus.setText("RUNNING");
		buttonServerListening.setText("Stop Listening");
		buttonServerListening.setDisable(false);
		inputTxtSchemaName.setDisable(true);
		inputTxtUserName.setDisable(true);
		inputTxtPassword.setDisable(true);
		inputTxtServerPort.setDisable(true);
		chkboxDefaultValues.setDisable(true);
		flagServerRunning = true;
		flagServerListening = true;
	}

	/**
	 * Do stop server.
	 *
	 * @param event the event
	 */
	@FXML
	void doStopServer(ActionEvent event) {
		//STOP SERVER
		try {
			if(!ServerUI.stopServer()) {
				setLabelShowMessageErrorMessage("Server is not running");
				return;
			}
		} catch (Exception e) {
			setLabelShowMessageErrorMessage("Error Stopping Server, Exception was caught");
			e.printStackTrace();
		}
		//importBtn.setDisable(true);
		//GUI update
		indicatorServerStatus.setFill(Color.RED);
		txtServerStatus.setText("STOPPED");
		txtClientsConnected.setText("0");
		setLabelShowMessageSucceessMessage("Server was stopped");
		buttonServerListening.setText("Stop Listening");
		buttonServerListening.setDisable(true);
		inputTxtSchemaName.setDisable(false);
		inputTxtUserName.setDisable(false);
		inputTxtPassword.setDisable(false);
		inputTxtServerPort.setDisable(false);
		chkboxDefaultValues.setDisable(false);
		//
		flagServerRunning = false;
		flagServerListening = false;
	}
	
    /**
     * Do fill default values in the server connections
     *
     * @param event the event
     */
    @FXML
    void doFillDefault(ActionEvent event) {
    	if(chkboxDefaultValues.isSelected()) {
    		inputTxtSchemaName.setText(ServerUI.DB_NAME);
    		inputTxtUserName.setText(ServerUI.DB_USER);
    		inputTxtPassword.setText(ServerUI.DB_PASS);
    		inputTxtServerPort.setText(String.valueOf(ServerUI.DEFAULT_PORT));
    		setLabelShowMessageSucceessMessage("Default values are set");
    	}else {
    		inputTxtSchemaName.setText("");
    		inputTxtUserName.setText("");
    		inputTxtPassword.setText("");
    		inputTxtServerPort.setText("");
    		setLabelShowMessageSucceessMessage("Default values are cleared");
    	}
    }
	
    
    /**
     * Update data in the log.
     *
     * @param dataToShow the data to show
     */
    public void updateDataLog(String dataToShow){
		Platform.runLater(new Runnable() {
		    @Override public void run() {
		    	logText.appendText(dataToShow+"\n");
		    }
		});
    }
    
    /**
     * Update number of clients that are connected to this server.
     *
     * @param numberOfClients the number of clients
     */
    public void updateNumberOfClientsConnected(int numberOfClients){
		Platform.runLater(new Runnable() {
		    @Override public void run() {
		    	txtClientsConnected.setText(String.valueOf(numberOfClients));
		    }
		});
    }
    
    
    /**
     * Start.
     *
     * @param primaryStage the primary stage
     * @throws Exception the exception
     */
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/server/gui/ServerMainWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Server Control");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(e -> {
			try {
				ServerUI.stopServer();
			} catch (Exception ex) {
				ex.printStackTrace();
				System.exit(1);
			}
			System.exit(0);
		});
        primaryStage.show();
    }

	/**
	 * Sets the server UI.
	 *
	 * @param serverUI the new server UI
	 */
	public void setServerUI(ServerUI serverUI) {
		this.serverUI = serverUI;
		flagServerUIset = true;
	}
	
	/**
	 * Sets the label show message error message.
	 *
	 * @param msg the new label show message error message
	 */
	private void setLabelShowMessageErrorMessage(String msg) {
		labelShowMessage.setText(msg);
		labelShowMessage.setStyle("-fx-text-fill: red;");
	}
	
	/**
	 * Sets the label show message success message.
	 *
	 * @param msg the new label show message success message
	 */
	private void setLabelShowMessageSucceessMessage(String msg) {
		labelShowMessage.setText(msg);
		labelShowMessage.setStyle("-fx-text-fill: green;");
	}
	
	/**
	 * Import users from outside system and check if the 
	 * import was successful or not.
	 *
	 * @param event the event
	 */
	@FXML
    void importUsers(ActionEvent event) {
        if(ServerUI.importUsers()) {
        	Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("import succsses");
	    	alert.setHeaderText(null);
	    	alert.setContentText("impoert users succssesfuly");
	    	alert.showAndWait();
	    	return;
        }else {
        	Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("import failed");
	    	alert.setHeaderText(null);
	    	alert.setContentText("problem import users");
	    	alert.showAndWait();
	    	return;
        }
    }
	
}
