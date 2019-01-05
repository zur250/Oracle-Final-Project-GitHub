package View;

import javafx.scene.control.Button;

public class AdminMenuPane extends CustomerWorkerMenuPane {

	private Button addNewProduct = new Button(WindowType.ADD_PRODUCT.getText());
	private Button discounts = new Button(WindowType.ROLES.getText());
	private Button users = new Button(WindowType.USERS.getText());
	
	public AdminMenuPane() {
		super();
		super.addButtons(addNewProduct);
		super.addButtons(discounts);
		super.addButtons(users);
//		super.setButtonOnPane();
	}
}
