package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class EntranceMenuPane extends MenuPane {

	private Button register = new Button(WindowType.REGIRSTRATION.text);
	private Button signup = new Button(WindowType.LOGIN.text);
	
	public EntranceMenuPane() {
		super();
		
		register.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DataPane.getInstance().changeView(WindowType.REGIRSTRATION);
				
				
			}
		});
		
		signup.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DataPane.getInstance().changeView(WindowType.LOGIN);
				
			}
		});
		
		super.addButtons(register);
		super.addButtons(signup);
//		super.setButtonOnPane();
	}
}
