package client.utility.qr;

import java.util.concurrent.atomic.AtomicReference;

import client.interfaces.IQRReader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Class QRReader - implements IQRReader interface, 
 * used as stand-in for a QR reader library .
 */
public class QRReader implements IQRReader{
	
	/** stage used to show the window */
	Stage stage;

	/**
	 * Gets the QR code from the method
	 *
	 * @return the QR code
	 */
	@Override
	public String getQRCode() {
		AtomicReference<String> data = new AtomicReference<>();
    	VBox root = new VBox();
    	root.setSpacing(10);
    	root.setAlignment(Pos.CENTER);
        Label label1 = new Label("W4C:");
        TextField textField = new TextField ();
        HBox hb = new HBox();
        root.setPadding(new Insets(10));
        hb.getChildren().addAll(label1, textField);
        hb.setSpacing(10);
        root.getChildren().add(hb);
        Button b = new Button("Scan");
        b.setOnAction(e -> {
        	data.set(textField.getText());
        	stage.close();
        });
        root.getChildren().add(b);
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("QR READER");
        stage.setResizable(false);
        stage.showAndWait();
		return data.get();
	}
	
}
