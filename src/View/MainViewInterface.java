package View;

import java.util.HashMap;

import javafx.scene.layout.Pane;

public interface MainViewInterface extends ViewInterface {

	void changeView(WindowType type);
	void setUser(User user);
}
