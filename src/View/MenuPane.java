package View;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MenuPane extends VBox {

	private ArrayList<Button> buttons = new ArrayList<Button>();
	
	public MenuPane() {
	}

	public ArrayList<Button> getButtons() {
		return (ArrayList<Button>) buttons.clone();
	}

	protected void addButtons(Button button) {
		buttons.add(button);
		this.getChildren().add(button);
	}
	
}
