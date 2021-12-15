package client.gui.history;

import java.util.Stack;

import client.interfaces.IClientFxController;

public class FxControllerHistory {
	
	private static IClientFxController base;
	private static Stack<IClientFxController> history;
	
	public FxControllerHistory(IClientFxController baseController) {
		base = baseController;
		history = new Stack<IClientFxController>();
	}
	
	public void pushFxController(IClientFxController fxController) {
		history.push(fxController);
	}
	
	public IClientFxController popFxController() {
		if(history.empty()) {
			return base;
		}
		return history.pop();
	}
}
