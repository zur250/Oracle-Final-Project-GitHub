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
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DiscountView extends GridPane implements ViewInterface{
    Stage window;
    TableView<ViewRole> table;
    VBox vBox;
    GridPane bottomPane;
    private Button updatebtn;
    private TextField percentage;

    public DiscountView() {
		super();
        createFormPane();
        
        setHeader();
        table = createTable();
        
        vBox = new VBox();   
        vBox.getChildren().addAll(table);
        add(vBox, 0, 1,4,1);
        GridPane.setHalignment(vBox, HPos.CENTER);
        GridPane.setValignment(vBox, VPos.CENTER);
        GridPane.setMargin(vBox, new Insets(10, 0,0,0));

        createBootomPane();
	}
    
    private void setHeader() {
    	// Add Header
        Label headerLabel = new Label("Discount Managment Page");
        headerLabel.setFont(ViewEffects.getHeadersFont());
        
        headerLabel.setEffect(ViewEffects.getShadowEffect(5, 5));
        add(headerLabel,0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setValignment(headerLabel, VPos.TOP);
        GridPane.setMargin(headerLabel, new Insets(10, 0,0,0));
    }

    private TableView<ViewRole> createTable()
    {      
        // Role Name column
        TableColumn<ViewRole, String> roleNameColumn = new TableColumn<>("Role Name");
        roleNameColumn.setMinWidth(100);
        roleNameColumn.setCellValueFactory(new PropertyValueFactory<>("RoleName"));

        //Percentage column
        TableColumn<ViewRole, Double> balanceColumn = new TableColumn<>("Percentage");
        balanceColumn.setMinWidth(100);
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("Percentage"));

        TableView<ViewRole> t = new TableView<ViewRole>();
        t.setMaxHeight(300);
        t.setItems(getRoles());
        t.getColumns().addAll(roleNameColumn,balanceColumn);
        
        
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
        // Add  Label
        Label prcLabel = new Label("Percentage : ");
        add(prcLabel, 0,2,2,1);
        GridPane.setHalignment(prcLabel, HPos.LEFT);
        GridPane.setMargin(prcLabel, new Insets(20, 0,20,0));
        
        // Add percentage Text Field
        percentage= new TextField();
        //percentage.setPrefHeight(40);
        percentage.setMaxWidth(50);
        add(percentage, 1,2,1,1);
        GridPane.setHalignment(percentage, HPos.LEFT);
        GridPane.setMargin(percentage, new Insets(20, 0,20,0));
		
     // Add delete Button
		updatebtn = new Button("Update Discount");
		updatebtn.setPrefHeight(40);
		updatebtn.setDefaultButton(true);
		updatebtn.setPrefWidth(100);
		updatebtn.setMinWidth(300);
        add(updatebtn, 2,2, 1, 1);
        GridPane.setHalignment(updatebtn, HPos.LEFT);
        GridPane.setMargin(updatebtn, new Insets(20, 0,20,0));

        updatebtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	ViewRole cur_selected = table.getSelectionModel().getSelectedItem();
            	try {
					ControllerInstance.getInstance().getCont().updateRoleDiscount(cur_selected.getRoleName(), Integer.parseInt(percentage.getText()));
					table.getSelectionModel().getSelectedItem().setPercentage(Integer.parseInt(percentage.getText()));
					table.refresh();
					ErrorMessage.getInstance().showAlert(Alert.AlertType.CONFIRMATION, DataPane.getInstance().getScene().getWindow(), "Done!", "Discount have been updated");
				} catch (Exception e) {
					ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, DataPane.getInstance().getScene().getWindow(), "Form Error!", e.getMessage());
				}
            	clearData();
            }
        });
	}
    
    
    public ObservableList<ViewRole> getRoles(){
        ObservableList<ViewRole> roles = FXCollections.observableArrayList();
        try {
			for (ViewRole viewRole : ControllerInstance.getInstance().getCont().get_roles()) {
				roles.add(viewRole);
			}
		} catch (SQLException e) {
			ErrorMessage.getInstance().showAlert(AlertType.ERROR, DataPane.getInstance().getScene().getWindow(), "Form Error!", e.getMessage());
		}
        
        return roles;
    }

	@Override
	public void updateData(DataType data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearData() {
		percentage.clear();
	}
}
