package client.interfaces;

/**
 * The Interface IQRReader - interface used to generalize QR readers, 
 */
public interface IQRReader {
	
	/**
	 * Gets the QR code from scanner, whatever it'll be.
	 *
	 * @return the QR code
	 */
	public String getQRCode();
}
