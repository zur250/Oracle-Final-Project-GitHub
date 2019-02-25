package View;

import java.sql.SQLException;
import java.util.Date;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UsersView extends GridPane implements ViewInterface{

    Stage window;
    TableView<User> table;
    VBox vBox;
    GridPane bottomPane;
    private Button deletebtn;
    private Button updateRolebtn;;
  
    
    public UsersView() {
		super();
		createFormPane();
        
        setHeader();
        table = createUsersTable();
        
        vBox = new VBox();   
        vBox.getChildren().addAll(table);
        add(vBox, 0, 1,4,1);
        GridPane.setHalignment(vBox, HPos.CENTER);
        GridPane.setValignment(vBox, VPos.CENTER);
        GridPane.setMargin(vBox, new Insets(10, 0,0,0));

        createBootomPane();

	}

	/*public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("User Page");

        gridPane = createFormPane();
        
        setHeader();
        table = createUsersTable();
        
        vBox = new VBox();   
        vBox.getChildren().addAll(table);
        gridPane.add(vBox, 0, 1,4,1);
        GridPane.setHalignment(vBox, HPos.CENTER);
        GridPane.setValignment(vBox, VPos.CENTER);
        GridPane.setMargin(vBox, new Insets(10, 0,0,0));

        createBootomPane();
        
        Scene scene = new Scene(gridPane,800,500);
        window.setScene(scene);
        window.show();
    }
*/    
    private void setHeader() {
    	// Add Header
        Label headerLabel = new Label("Users Managment Form");
        headerLabel.setFont(ViewEffects.getHeadersFont());
        
        headerLabel.setEffect(ViewEffects.getShadowEffect(5, 5));
        add(headerLabel,0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setValignment(headerLabel, VPos.TOP);
        GridPane.setMargin(headerLabel, new Insets(10, 0,0,0));
    }

    private TableView<User> createUsersTable()
    {
        //User Name column
        TableColumn<User, String> usernameColumn = new TableColumn<>("User Name");
        usernameColumn.setMinWidth(100);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        
        // Role Name column
        TableColumn<User, String> roleNameColumn = new TableColumn<>("Role Name");
        roleNameColumn.setMinWidth(100);
        roleNameColumn.setCellValueFactory(new PropertyValueFactory<>("RoleName"));

        //Balance column
        TableColumn<User, Double> balanceColumn = new TableColumn<>("Balance");
        balanceColumn.setMinWidth(100);
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("Balance"));

        //Start Date column
        TableColumn<User, Date> hireDateColumn = new TableColumn<>("Hire Date");
        hireDateColumn.setMinWidth(100);
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("HireDate"));
        
      //Seniority column
        TableColumn<User, Double> seniorityColumn = new TableColumn<>("Seniority");
        seniorityColumn.setMinWidth(100);
        seniorityColumn.setCellValueFactory(new PropertyValueFactory<>("Seniority"));
        
        TableView<User> t = new TableView<User>();
        t.setMaxHeight(300);
        t.setItems(getUsers());
        t.getColumns().addAll(usernameColumn,roleNameColumn,balanceColumn, hireDateColumn,seniorityColumn);
        
        
        return t;

    }

	private void createFormPane() {

        // Position the pane at the center of the screen, both vertically and horizontally
        //gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        setPadding(new Insets(20, 20, 20, 20));

        // Set the horizontal gap between columns
        setHgap(10);

        // Set the vertical gap between rows
        setVgap(10);

        // Add Column Constraints

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
    }

	private void createBootomPane() {
        
     // Add delete Button
        deletebtn = new Button("Delete User");
        deletebtn.setPrefHeight(40);
        deletebtn.setDefaultButton(true);
        deletebtn.setPrefWidth(100);
        add(deletebtn, 0,2, 2, 1);
        GridPane.setHalignment(deletebtn, HPos.LEFT);
        GridPane.setMargin(deletebtn, new Insets(20, 0,20,0));

        deletebtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
					try {
						ControllerInstance.getInstance().getCont().deleteUser(table.getSelectionModel().getSelectedItem().getUserName());
						refreshTable();
	                	ErrorMessage.getInstance().showAlert(Alert.AlertType.CONFIRMATION, DataPane.getInstance().getScene().getWindow(), "Form Error!", "User have been deleted!");
					} catch (Exception e) {
						ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, DataPane.getInstance().getScene().getWindow(), "Form Error!", e.toString());
					}
            }
        });

     // Add update role Button
        updateRolebtn = new Button("Update User Role");
        updateRolebtn.setPrefHeight(40);
        updateRolebtn.setDefaultButton(true);
        updateRolebtn.setPrefWidth(110);
        add(updateRolebtn, 1,2, 2, 1);
        GridPane.setHalignment(updateRolebtn, HPos.LEFT);
        GridPane.setMargin(updateRolebtn, new Insets(20, 0,20,0));
        
    	ObservableList<String> options = 
    		    FXCollections.observableArrayList(
    		        "Admin",
    		        "Employee",
    		        "Customer"
    		    );
    		final ComboBox comboBox = new ComboBox(options);
    		comboBox.getSelectionModel().selectFirst();
    		
    		comboBox.setPrefHeight(40);    		
    		comboBox.setPrefWidth(100);
            add(comboBox, 2,2,2,1); 
            GridPane.setHalignment(comboBox, HPos.LEFT);
            GridPane.setMargin(comboBox, new Insets(20, 0,20,0));
           

        updateRolebtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	User currentSelected = table.getSelectionModel().getSelectedItem();
                	try {
						ControllerInstance.getInstance().getCont().updateUserRole(comboBox.getSelectionModel().getSelectedItem().toString(), table.getSelectionModel().getSelectedItem().getUserName());
						refreshTable();
	                	ErrorMessage.getInstance().showAlert(Alert.AlertType.CONFIRMATION, DataPane.getInstance().getScene().getWindow(), "Form Error!", "Role Updated Sucssesfully");
					} catch (Exception e) {
						ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, DataPane.getInstance().getScene().getWindow(), "Form Error!", e.toString());
					}
                }
        });

	}
	
	public void refreshTable() {
		table.setItems(null);
    	table.setItems(getUsers());
    	table.refresh();
	}
    
    //Get all of the products
    public ObservableList<User> getUsers(){
        /*ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new User("Zur","0548070390","Customer",456.56,2.5,new Date(1993,11,24)));
        users.add(new User("Guy","0548070391","Admin",123.56,2.5,new Date(1990,11,24)));
        users.add(new User("Oren","0548070392","Woker",147.56,2.5,new Date(1989,11,24)));
        users.add(new User("Oren","0548070392","Woker",147.56,2.5,new Date(1989,11,24)));
        users.add(new User("Oren","0548070392","Woker",147.56,2.5,new Date(1989,11,24)));
        users.add(new User("Oren","0548070392","Woker",147.56,2.5,new Date(1989,11,24)));
        users.add(new User("Oren","0548070392","Woker",147.56,2.5,new Date(1989,11,24)));*/
    	ObservableList<User> users = FXCollections.observableArrayList();
    			try {
					for (User user : ControllerInstance.getInstance().getCont().get_users()) {
						users.add(user);
						return users;
					}
				} catch (SQLException e) {
					ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, DataPane.getInstance().getScene().getWindow(), "Form Error!", e.getMessage());
				}
        return null;
    }

	@Override
	public void updateData(DataType data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		
	}


}