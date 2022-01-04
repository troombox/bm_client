package client.interfaces;

import javafx.stage.Stage;

/**
 * The Interface IClientFxController is interface implemented by all the windows in our application
 * 
 */
public interface IClientFxController {
	
	/**
	 *  this method starts the FX window controller that implements the IClientFxController interface 
	 *
	 * @param stage - main stage of the application
	 */
	public void start(Stage stage);
}
