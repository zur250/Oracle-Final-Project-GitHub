package View;

import javafx.scene.control.Button;

public class CustomerWorkerMenuPane extends MenuPane {

	private Button purchasesHistory = new Button(WindowType.PURCHASE_HISTORY.getText());
	private Button products = new Button(WindowType.PRODUCTS.getText());
	private Button cart = new Button(WindowType.CART.text);
	private Button disconnect = new Button("Disconnect");
	
	public CustomerWorkerMenuPane() {
		super();
		super.addButtons(disconnect);
		super.addButtons(purchasesHistory);
		super.addButtons(products);
		super.addButtons(cart);
//		super.setButtonOnPane();
	}
}
