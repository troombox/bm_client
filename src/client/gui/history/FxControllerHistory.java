package client.gui.history;

import java.util.Stack;

import client.interfaces.IClientFxController;

/**
 * The Class FxControllerHistory is a LIFO stack used to keep history of the pages visited by user.
 * This is done by pushing / popping controllers, each of them implementing IClientFxController interface,
 * and calling on the start() method of them.
 * There's also a "base" controller, that can be called at any time, that one is used for setting the current "home page"
 * for the application.
 */
public class FxControllerHistory {
	
	/** The base controller, used as a home page */
	private static IClientFxController base;
	
	/** The history stack. */
	private static Stack<IClientFxController> history;
	
	/**
	 * Instantiates a new FX controller history.
	 *
	 * @param baseController - the base controller used 
	 * for setting the current home page of the application
	 */
	public FxControllerHistory(IClientFxController baseController) {
		base = baseController;
		history = new Stack<IClientFxController>();
	}
	
	/**
	 * Push fx controller.
	 *
	 * @param fxController - the FX controller to push
	 */
	public void pushFxController(IClientFxController fxController) {
		history.push(fxController);
	}
	
	/**
	 * Pop fx controller.
	 *
	 * @return last fx controller pushed in. if empty - base controller is returned
	 */
	public IClientFxController popFxController() {
		if(history.empty()) {
			return base;
		}
		return history.pop();
	}
	
	/**
	 * Clear controller history.
	 */
	public void clearControllerHistory() {
		history.clear();
	}
	
	/**
	 * Sets the base controller.
	 *
	 * @param baseController the new base controller
	 */
	public void setBaseController(IClientFxController baseController) {
		base = baseController;
	}
	
	/**
	 * Gets the base controller.
	 *
	 * @return the base controller
	 */
	public IClientFxController getBaseController() {
		return base;
	}
}
