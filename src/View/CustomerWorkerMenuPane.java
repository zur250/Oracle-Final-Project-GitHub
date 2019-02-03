package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class CustomerWorkerMenuPane extends MenuPane {

	private Button homePage = new Button(WindowType.HOMEPAGE.getText());
	private Button purchasesHistory = new Button(WindowType.PURCHASE_HISTORY.getText());
	private Button products = new Button(WindowType.PRODUCTS.getText());
	private Button cart = new Button(WindowType.CART.text);
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
		
		cart.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DataPane.getInstance().changeView(WindowType.CART);
				
			}
		});
		
		disconnect.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				ControllerInstance.getInstance().getCont().disconnect();
				DataPane.getInstance().changeView(WindowType.LOGIN);
				
			}
		});
		
		super.addButtons(homePage);
		super.addButtons(disconnect);
		super.addButtons(purchasesHistory);
		super.addButtons(products);
		super.addButtons(cart);
//		super.setButtonOnPane();
	}
}
