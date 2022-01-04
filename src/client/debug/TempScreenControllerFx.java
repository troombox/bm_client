package client.debug;

import java.io.IOException;

import client.interfaces.IClientFxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Class TempScreenControllerFx is a temporary screen used for debugging, 
 * implements IClientFxController and can be used in any place IClientFxController is expected
 * to see the normal run of the program
 */
public class TempScreenControllerFx implements IClientFxController{

	/**
	 * Starting the screen
	 *
	 * @param stage main stage of the application 
	 */
	@Override
	public void start(Stage stage) {
		Stage primaryStage  = stage;
		if(primaryStage == null) {
			primaryStage = new Stage();
		}
        Parent root = null;
        try {
			root = FXMLLoader.load(getClass().getResource("/client/debug/TempScreenFxml.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        Scene scene = new Scene(root);
//      scene.getStylesheets().add(getClass().getResource("/client/client_gui/LoginFxml.css").toExternalForm());
        primaryStage.setTitle("TempScreen");
        primaryStage.setScene(scene);
        primaryStage.show();
	}

}
