package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class HomepageView extends Application implements ViewInterface {

	private User cur_User;
	
	private Label headerLabel;
	private Label userNameLabel;
	private TextField userNameField;

	private Label roleNameLabel;
	private TextField roleNameField;
	private Label phoneLabel;
	private TextField phoneField;
	private Label balanceLabel;
	private TextField balanceField;

	private Button updatePasswrdbtn;
	private Button updateBalancebtn;
	
	private Label updatePasslbl;
	private PasswordField updatePassfield;
	
	private Label updateBlanacelbl;
	private TextField updateBlanacefield;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Home Page");

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
	
	private GridPane createRegistrationFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        //gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return gridPane;
    }
	
    private void addUIControls(GridPane gridPane) {
        // Add Header
        headerLabel = new Label("Home page");
        headerLabel.setFont(ViewEffects.getHeadersFont());
        
        headerLabel.setEffect(ViewEffects.getShadowEffect(5, 5));
        gridPane.add(headerLabel,0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        // UserName Label
        userNameLabel = new Label("User Name : ");
        gridPane.add(userNameLabel, 0,1);

        // UserName Text Field
        userNameField = new TextField();
        userNameField.setPrefHeight(40);
        userNameField.setMaxWidth(100);
        userNameField.setDisable(true);
        gridPane.add(userNameField, 1,1);
        
        // RoleName Label
        roleNameLabel = new Label("Role Name : ");
        gridPane.add(roleNameLabel , 0,2);

        // roleName Text Field
        roleNameField = new TextField();
        roleNameField.setPrefHeight(40);
        roleNameField.setMaxWidth(100);
        roleNameField.setDisable(true);
        gridPane.add(roleNameField, 1,2);


        //  PhoneLabel
        phoneLabel = new Label("Phone Number: ");
        gridPane.add(phoneLabel,0, 3);

        // Phone Text Field
        phoneField = new TextField();
        phoneField.setPrefHeight(40);
        phoneField.setMaxWidth(100);
        phoneField.setDisable(true);
        gridPane.add(phoneField, 1, 3);

        //  Balance Label
        balanceLabel = new Label("Balance : ");
        gridPane.add(balanceLabel, 0,4);

        // Balance Text Field
        balanceField = new TextField();
        balanceField.setPrefHeight(40);
        balanceField.setMaxWidth(100);
        balanceField.setDisable(true);
        gridPane.add(balanceField, 1, 4);
        
        // New Password Label
        updatePasslbl = new Label("New Password : ");
        gridPane.add(updatePasslbl, 0, 5);

        // New Password Field
        updatePassfield = new PasswordField();
        updatePassfield.setPrefHeight(40);
        updatePassfield.setMaxWidth(100);
        gridPane.add(updatePassfield, 1, 5);
        
        // Add Submit Button
        updatePasswrdbtn = new Button("Change Password");
        updatePasswrdbtn.setPrefHeight(40);
        updatePasswrdbtn.setMaxWidth(200);
        updatePasswrdbtn.setDefaultButton(true);
        gridPane.add(updatePasswrdbtn, 2,5);

        updatePasswrdbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(updatePassfield.getText().isEmpty())
            	{
            		ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a new password");
            		return;
            	}
            }
        });
        
        // Update Balance Label
        updateBlanacelbl = new Label("Deposit : ");
        gridPane.add(updateBlanacelbl, 0, 6);

        // Update Balance Field
        updateBlanacefield = new PasswordField();
        updateBlanacefield.setPrefHeight(40);
        updateBlanacefield.setMaxWidth(100);
        gridPane.add(updateBlanacefield, 1, 6);
        
        // Add Submit Button
        updateBalancebtn = new Button("Deposit");
        updateBalancebtn.setPrefHeight(40);
        updateBalancebtn.setMaxWidth(200);
        updateBalancebtn.setDefaultButton(true);
        gridPane.add(updateBalancebtn, 2,6);

        updateBalancebtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(updateBlanacefield.getText().isEmpty()) {
            		ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a deposit value");
            		return;
            	}
            }
        });


    }

    public static void main(String[] args) {
        launch(args);
    }

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
