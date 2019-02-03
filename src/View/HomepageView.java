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

public class HomepageView extends GridPane implements ViewInterface {

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
	
	private Label curPasslbl;
	private PasswordField curPassfield;
	
	private Label updatePasslbl;
	private PasswordField updatePassfield;
	
	private Label updateBlanacelbl;
	private TextField updateBlanacefield;
	
	
	
	public HomepageView() {
		super();
		createRegistrationFormPane();
		addUIControls();
	}

	/*@Override
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
		
	}*/
	
	private void createRegistrationFormPane() {
        // Position the pane at the center of the screen, both vertically and horizontally
        //gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        setHgap(10);

        // Set the vertical gap between rows
        setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(120, 120, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

    }
	
    public User getCur_User() {
		return cur_User;
	}

	public void setCur_User(User cur_User) {
		this.cur_User = cur_User;
		this.userNameField.setText(cur_User.getUserName());
		this.roleNameField.setText(cur_User.getRoleName());
		this.phoneField.setText(cur_User.getPhone());
		this.balanceField.setText(String.valueOf(cur_User.getBalance()));
	}

	private void addUIControls() {
        // Add Header
        headerLabel = new Label("Home page");
        headerLabel.setFont(ViewEffects.getHeadersFont());
        
        headerLabel.setEffect(ViewEffects.getShadowEffect(5, 5));
        add(headerLabel,0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        // UserName Label
        userNameLabel = new Label("User Name : ");
        add(userNameLabel, 0,1);

        // UserName Text Field
        userNameField = new TextField();
        userNameField.setPrefHeight(40);
        userNameField.setMaxWidth(100);
        userNameField.setDisable(true);
        add(userNameField, 1,1);
        
        // RoleName Label
        roleNameLabel = new Label("Role Name : ");
        add(roleNameLabel , 0,2);

        // roleName Text Field
        roleNameField = new TextField();
        roleNameField.setPrefHeight(40);
        roleNameField.setMaxWidth(100);
        roleNameField.setDisable(true);
        add(roleNameField, 1,2);


        //  PhoneLabel
        phoneLabel = new Label("Phone Number: ");
        add(phoneLabel,0, 3);

        // Phone Text Field
        phoneField = new TextField();
        phoneField.setPrefHeight(40);
        phoneField.setMaxWidth(100);
        phoneField.setDisable(true);
        add(phoneField, 1, 3);

        //  Balance Label
        balanceLabel = new Label("Balance : ");
        add(balanceLabel, 0,4);

        // Balance Text Field
        balanceField = new TextField();
        balanceField.setPrefHeight(40);
        balanceField.setMaxWidth(100);
        balanceField.setDisable(true);
        add(balanceField, 1, 4);
        
        // cur Password Label
        curPasslbl = new Label("Current Password : ");
        add(curPasslbl, 0, 5);

        // cur Password Field
        curPassfield = new PasswordField();
        curPassfield.setPrefHeight(40);
        curPassfield.setMaxWidth(100);
        add(curPassfield, 1, 5);

        // New Password Label
        updatePasslbl = new Label("New Password : ");
        add(updatePasslbl, 0, 6);

        // New Password Field
        updatePassfield = new PasswordField();
        updatePassfield.setPrefHeight(40);
        updatePassfield.setMaxWidth(100);
        add(updatePassfield, 1, 6);
        
        // Add Submit Button
        updatePasswrdbtn = new Button("Change Password");
        updatePasswrdbtn.setPrefHeight(40);
        updatePasswrdbtn.setMaxWidth(200);
        updatePasswrdbtn.setDefaultButton(true);
        add(updatePasswrdbtn, 2,6);

        updatePasswrdbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(updatePassfield.getText().isEmpty())
            	{
            		ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, DataPane.getInstance().getScene().getWindow(), "Form Error!", "Please enter a new password");
            		return;
            	}
            	ControllerInstance.getInstance().getCont().updateUserPassword(curPassfield.getText(), updatePassfield.getText());
            	curPassfield.clear();
            	updatePassfield.clear();
            }
        });
        
        // Update Balance Label
        updateBlanacelbl = new Label("Deposit : ");
        add(updateBlanacelbl, 0, 7);

        // Update Balance Field
        updateBlanacefield = new PasswordField();
        updateBlanacefield.setPrefHeight(40);
        updateBlanacefield.setMaxWidth(100);
        add(updateBlanacefield, 1, 7);
        
        // Add Submit Button
        updateBalancebtn = new Button("Deposit");
        updateBalancebtn.setPrefHeight(40);
        updateBalancebtn.setMaxWidth(200);
        updateBalancebtn.setDefaultButton(true);
        add(updateBalancebtn, 2,7);

        updateBalancebtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(updateBlanacefield.getText().isEmpty()) {
            		ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, DataPane.getInstance().getScene().getWindow(), "Form Error!", "Please enter a deposit value");
            		return;
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
		updatePassfield.clear();
		balanceField.clear();
		curPassfield.clear();
		
	}

}
