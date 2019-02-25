package View;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

public class CustomerWorkerMenuPane extends MenuPane {

	private Button homePage = new Button(WindowType.HOMEPAGE.getText());
	private Button purchasesHistory = new Button(WindowType.PURCHASE_HISTORY.getText());
	private Button products = new Button(WindowType.PRODUCTS.getText());
	private Button disconnect = new Button("Disconnect");
	
	public CustomerWorkerMenuPane() {
		super();
		
		homePage.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DataPane.getInstance().changeView(WindowType.HOMEPAGE);
				
			}
		});
		
		purchasesHistory.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DataPane.getInstance().changeView(WindowType.PURCHASE_HISTORY);
				
			}
		});
		
		products.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DataPane.getInstance().changeView(WindowType.PRODUCTS);
				
			}
		});
		
		disconnect.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					ControllerInstance.getInstance().getCont().disconnect();
				} catch (Exception e) {
					ErrorMessage.getInstance().showAlert(AlertType.ERROR, DataPane.getInstance().getScene().getWindow(), "Form Error!", e.getMessage());
				}
				DataPane.getInstance().changeView(WindowType.LOGIN);
				
			}
		});
		
		super.addButtons(homePage);
		super.addButtons(disconnect);
		super.addButtons(purchasesHistory);
		super.addButtons(products);
	}
}
