package View;

import javafx.scene.control.Button;

public class EntranceMenuPane extends MenuPane {

	private Button register = new Button(WindowType.REGIRSTRATION.text);
	private Button signup = new Button(WindowType.LOGIN.text);
	
	public EntranceMenuPane() {
		super();
		super.addButtons(register);
		super.addButtons(signup);
//		super.setButtonOnPane();
	}
}
