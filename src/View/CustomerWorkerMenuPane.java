package View;

import javafx.scene.control.Button;

public class CustomerWorkerMenuPane extends MenuPane {

	private Button purchases = new Button(WindowType.ALL_PURCHASES.text);
	private Button purchase = new Button(WindowType.PURCHASE.text);
	private Button cart = new Button(WindowType.CART.text);
	private Button disconnect = new Button("Disconnect");
	
	public CustomerWorkerMenuPane() {
		super();
		super.addButtons(disconnect);
		super.addButtons(purchases);
		super.addButtons(purchases);
		super.addButtons(cart);
	}
}
