package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		MainViewInterface mainPanel = new MainPane();
		Scene scene = new Scene((MainPane)mainPanel);
	    primaryStage.setTitle("ShowBorderPane"); // Set the stage title
	    primaryStage.setScene(scene); // Place the scene in the stage
	    primaryStage.show(); // Display the stage
	    primaryStage.setAlwaysOnTop(true);
	}
	
	public static void main(String[] args)
	 { launch(args);
	 }

}
