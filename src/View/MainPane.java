package View;

import java.util.HashMap;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainPane extends BorderPane implements MainViewInterface {
	
	private MainViewInterface bottomPane;
	private UpperPane upper;
	
	public MainPane() {
		upper = new UpperPane();
		bottomPane = new BottomPane();
		setTop(upper);
		setCenter((BottomPane)bottomPane);
	}
	
	@Override
	public void clearData() {
		bottomPane.clearData();
	}

	@Override
	public void updateData(DataType data) {
		bottomPane.updateData(data);
		
	}

	@Override
	public void changeView(WindowType type) {
		bottomPane.changeView(type);
	}
	
	public void setUser(User user) {
		bottomPane.setUser(user);
	}

}
