package client.utility.wrappers;

import javafx.scene.control.Button;

/**
 * The Class MemoryButton extends javafx Button class.
 *	Memory button acts just like a regular javafx button, 
 *with an addition of remembering one index given in constructor
 */
public class MemoryButton extends Button{
	
	/** The index saved. */
	private int index;
	
	/**
	 * Instantiates a new memory button.
	 *
	 * @param text - the text to be shown on button
	 * @param index - the index to remember
	 */
	public MemoryButton(String text, int index) {
		super(text);
		this.index = index;
	}
	
	/**
	 * Gets the index saved
	 *
	 * @return the index - variable given in constructor
	 */
	public int getIndex() {
		return index;
	}
}
