package client.utility.wrappers;

import javafx.scene.control.Button;

public class MemoryButton extends Button{
	private int index;
	
	public MemoryButton(String text, int index) {
		super(text);
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
}
