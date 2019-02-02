package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class AdminMenuPane extends CustomerWorkerMenuPane {

	private Button addNewProduct = new Button(WindowType.ADD_PRODUCT.getText());
	private Button discounts = new Button(WindowType.ROLES.getText());
	private Button users = new Button(WindowType.USERS.getText());
	
	public AdminMenuPane() {
		super();
		
		users.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DataPane.getInstance().changeView(WindowType.USERS);
				
			}
		});
		
		discounts.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DataPane.getInstance().changeView(WindowType.ROLES);
				
			}
		});
		
		addNewProduct.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DataPane.getInstance().changeView(WindowType.ADD_PRODUCT);
				
			}
		});
		
		super.addButtons(addNewProduct);
		super.addButtons(discounts);
		super.addButtons(users);
//		super.setButtonOnPane();
	}
}
