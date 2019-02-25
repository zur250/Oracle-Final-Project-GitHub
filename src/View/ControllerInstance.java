package View;

import Controller.*;
import javafx.scene.control.Alert.AlertType;

public class ControllerInstance {

	private ControllerInterface cont;
	
	private static ControllerInstance instance;
	
	public static ControllerInstance getInstance() {
		if (instance == null) {
			instance = new ControllerInstance();
		}
		return instance;
	}
	
	private ControllerInstance() {
		super();
		try {
			cont = new ControllerImpl();
		} catch (Exception e) {
			ErrorMessage.getInstance().showAlert(AlertType.ERROR, DataPane.getInstance().getScene().getWindow(), "Form Error!", e.getMessage());
		}
		//System.out.println("new cont");
	}
	
	public ControllerInterface getCont() {
		return cont;
	}
	
	
}
