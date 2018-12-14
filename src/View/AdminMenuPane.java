package View;

import javafx.scene.control.Button;

public class AdminMenuPane extends CustomerWorkerMenuPane {

	private Button addNewProduct = new Button(WindowType.ADD_PRODUCT.text);
	private Button discounts = new Button(WindowType.DISCOUNTS.text);
	private Button users = new Button(WindowType.USERS.text);
	
	public AdminMenuPane() {
		super();
		super.addButtons(addNewProduct);
		super.addButtons(discounts);
		super.addButtons(users);
	}
}
