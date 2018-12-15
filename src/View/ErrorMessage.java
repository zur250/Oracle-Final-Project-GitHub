package View;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class ErrorMessage {
	
	// static variable single_instance of type Singleton 
    private static ErrorMessage single_instance = null; 
	
	private ErrorMessage() {
		
	}
	
    // static method to create instance of Singleton class 
    public static ErrorMessage getInstance() 
    { 
        if (single_instance == null) 
            single_instance = new ErrorMessage(); 
  
        return single_instance; 
    } 
	
	public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
	
}
