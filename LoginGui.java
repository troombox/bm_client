package client_gui;

import java.util.ArrayList;

import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import utility.DataType;
import utility.RequestType;

public class LoginGui {

    @FXML
    private TextField userName;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    void loginButtonPressed(ActionEvent event) {
    	String username = userName.getText();
    	String Passwod = password.getText();
    	//ArrayList<String> msg = new ArrayList<>();
    	//msg: {username, password}
    	//msg.add(username);
    	//msg.add(Passwod);
    	User user = new User(username, Passwod);
    	ClientUI.clientLogic.sendMessageToServer(user, DataType.USER, RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA);
    	

    }

}

