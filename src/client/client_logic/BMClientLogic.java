package client.client_logic;

import java.io.IOException;

import ocsf.client.AbstractClient;
import utility.enums.DataType;
import utility.enums.RequestType;

public class BMClientLogic extends AbstractClient{
	
	public BMClientLogic(String host, int port) throws IOException {
	    super(host, port); //Call the superclass constructor
	    openConnection();
	    System.out.println("connection opened");
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		// TODO Auto-generated method stub
	}
	
	public void sendMessageToServer(Object dataToSendToServer, DataType dataType, RequestType requestType) {
		
	}
	
	private void handleMessageToServer(Object msg) {
		try {
			sendToServer(msg);
		} catch (IOException e) {
			System.out.println("Could not send message to server.  Terminating client.");
			System.exit(1);
		}
	}
	
}
