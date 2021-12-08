package client.client_gui;

import java.io.IOException;

import client.interfaces.IClientFxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginControllerFx implements IClientFxController {

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtUserPass;

    @FXML
    private Button buttonLogin;

    @FXML
    void doAppLogin(ActionEvent event) {

    }

	@Override
	public void start(Stage stage) {
        Parent root = null;
        try {
			root = FXMLLoader.load(getClass().getResource("/client/client_gui/LoginFxml.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
//      scene.getStylesheets().add(getClass().getResource("/client_gui/OrderFrame.css").toExternalForm());
        stage.setTitle("User Login");
        stage.setScene(scene);
        stage.show();
	}

}
