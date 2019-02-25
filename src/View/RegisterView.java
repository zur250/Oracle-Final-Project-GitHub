package View;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RegisterView extends GridPane implements ViewInterface{

	private Label headerLabel;
	private Label userNameLabel;
	private TextField userNameField;
	private Label phoneLabel;
	private TextField phoneField;
	private Label passwordLabel;
	private PasswordField passwordField;
	private Label balanceLabel;
	private TextField balanceField;
	private Button submitButton;
	
	/*@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Registration Page");

        // Create the registration form grid pane
        GridPane gridPane = createRegistrationFormPane();
        // Add UI controls to the registration form grid pane
        addUIControls(gridPane);
        // Create a scene with registration form grid pane as the root node
        Scene scene = new Scene(gridPane, 800, 500);
        // Set the scene in primary stage	
        primaryStage.setScene(scene);
        
        primaryStage.show();
		
	}
*/	
	
	public RegisterView() {
		super();
		createRegistrationFormPane();
		addUIControls();
	}
	
	private void createRegistrationFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        this.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        this.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        this.setHgap(10);

        // Set the vertical gap between rows
        this.setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
    }
	
    

	private void addUIControls() {
        // Add Header
        headerLabel = new Label("Registration Form");
        headerLabel.setFont(ViewEffects.getHeadersFont());
        
        headerLabel.setEffect(ViewEffects.getShadowEffect(5, 5));
        this.add(headerLabel,0,0,2,1);
        this.setHalignment(headerLabel, HPos.CENTER);
        this.setMargin(headerLabel, new Insets(20, 0,20,0));

        // Add UserName Label
        userNameLabel = new Label("User Name : ");
        this.add(userNameLabel, 0,1);

        // Add UserName Text Field
        userNameField = new TextField();
        userNameField.setPrefHeight(40);
        this.add(userNameField, 1,1);


        // Add PhoneLabel
        phoneLabel = new Label("Phone Number: ");
        this.add(phoneLabel,0, 2);

        // Add Phone Text Field
        phoneField = new TextField();
        phoneField.setPrefHeight(40);
        this.add(phoneField, 1, 2);

        // Add Password Label
        passwordLabel = new Label("Password : ");
        this.add(passwordLabel, 0, 3);

        // Add Password Field
        passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        this.add(passwordField, 1, 3);
        
        // Add Balance Label
        balanceLabel = new Label("Balance : ");
        this.add(balanceLabel, 0,4);

        // Add Balance Text Field
        balanceField = new TextField();
        balanceField.setPrefHeight(40);
        this.add(balanceField, 1, 4);

        // Add Submit Button
        submitButton = new Button("Submit");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        this.add(submitButton, 0,5, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
					ControllerInstance.getInstance().getCont().register(userNameField.getText(), passwordField.getText(), Double.valueOf(balanceField.getText()), Long.valueOf(phoneField.getText()));
					ErrorMessage.getInstance().showAlert(Alert.AlertType.CONFIRMATION, DataPane.getInstance().getScene().getWindow(), "Registration Successful!", "Welcome " + userNameField.getText());
	                clearData();
				} catch (NumberFormatException | SQLException e) {
					ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, DataPane.getInstance().getScene().getWindow(), "Form Error!", e.getMessage());
				}

                
            }
        });
    }

    /*public static void main(String[] args) {
        launch(args);
    }*/

	@Override
	public void updateData(DataType data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearData() {
		userNameField.clear();
		phoneField.clear();
		passwordField.clear();
		balanceField.clear();
		
	}
}
