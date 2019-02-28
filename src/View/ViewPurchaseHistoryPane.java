package View;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewPurchaseHistoryPane extends GridPane implements ViewInterface {

	private Stage window;
		
	private Label headerLabel;
	
	private UserType user = UserType.ADMIN;
	
	private DataTypeGenericForTable data;
		
	private ComboBox<String> productTypeComboBox = new ComboBox<String>();
	private ComboBox<String> groupingComboBox = new ComboBox<String>();
	
	private RadioButton purchaseRadioButton = new RadioButton("Purchases");
	private RadioButton productsRadioButton = new RadioButton("Products purchased");
	
	private RadioButton sortAscendingRadioButton = new RadioButton("sort ascending");
	private RadioButton sortDescendingRadioButton = new RadioButton("sort descending");
	
	private CheckBox mineCheckBox = new CheckBox("Mine");
	private CheckBox workersCheckBox = new CheckBox("Workers");
	private CheckBox customersCheckBox = new CheckBox("Customers");
	
	private Label startDate = new Label("Start date: ");
	private Label endDate = new Label("End date: ");
	private DatePicker startDatePicker = new DatePicker();
	private DatePicker endDatePicker = new DatePicker();
	
	private Button filterButton = new Button("Filter");
	
	private GenericTablePane tablePane;
	
	private HBox filtersPane = new HBox(2.0);
	private HBox selectTableTypePane = new HBox(8.0);
	private HBox dateFilterPane = new HBox(2.0);
	private Pane typeFilterPane = new Pane();
	
	
	
	public ViewPurchaseHistoryPane() {
		super(); 
        setHeader();//create the header of the pane
        
        createSelectTableTypePane();
        createDateFilterPane();
        createTypeFilterPane();
        filterButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					clearData();
					data = ControllerInstance.getInstance().getCont().viewPastPurchases
					(ControllerInstance.getInstance().getCont().getCurUser().getUserName(), getSelectedFocus(), getSetlectedTimeAggregation(), getSelectedGroupAggregation(),
							getSelectedFromDate(), getSelectedUntilDate(), getSelectedProductType(), getSelectedSortingType());
				getChildren().remove(tablePane);
				tablePane = new GenericTablePane(data);
				add(tablePane, 0, 3, 4, 1);
		        GridPane.setHalignment(tablePane, HPos.CENTER);
		        GridPane.setValignment(tablePane, VPos.TOP);
		        GridPane.setMargin(tablePane, new Insets(10, 0,0,0));
				} catch (Exception e) {
					ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, DataPane.getInstance().getScene().getWindow(), "Form Error!", e.getMessage());
				}
			}
		});
        filtersPane = new HBox(8.0, dateFilterPane, typeFilterPane, filterButton);//create the
        //table filter pane
             
        tablePane = new GenericTablePane(null);//no values have been inserted yet from DB

        setPadding(new Insets(20, 20, 20, 20));
        setHgap(10);
        setVgap(10);
        
        add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setValignment(headerLabel, VPos.TOP);
        GridPane.setMargin(headerLabel, new Insets(10, 0,0,0));
        
        add(selectTableTypePane, 0, 1, 5, 1);
        GridPane.setHalignment(selectTableTypePane, HPos.CENTER);
        GridPane.setValignment(selectTableTypePane, VPos.TOP);
        GridPane.setMargin(selectTableTypePane, new Insets(10, 0,0,0));
        
        add(filtersPane, 0, 2, 4, 1);
        GridPane.setHalignment(filtersPane, HPos.CENTER);
        GridPane.setValignment(filtersPane, VPos.TOP);
        GridPane.setMargin(filtersPane, new Insets(10, 0,0,0));
        
        add(tablePane, 0, 3, 4, 1);
        GridPane.setHalignment(tablePane, HPos.CENTER);
        GridPane.setValignment(tablePane, VPos.TOP);
        GridPane.setMargin(tablePane, new Insets(10, 0,0,0));
	}

	/*public static void main(String[] args) {
        launch(args);
    }
	
   @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle(WindowType.PURCHASE_HISTORY.getText());
        
        setHeader();//create the header of the pane
        
        createSelectTableTypePane();
        createDateFilterPane();
        createTypeFilterPane();
        filterButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					data = ControllerInstance.getInstance().getCont().viewPastPurchases
					("apachi", getSelectedFocus(), getSetlectedTimeAggregation(), getSelectedGroupAggregation(),
							getSelectedFromDate(), getSelectedUntilDate(), getSelectedProductType(), getSelectedSortingType());
				getChildren().remove(tablePane);
				tablePane = new GenericTablePane(data);
				add(tablePane, 0, 3, 4, 1);
		        GridPane.setHalignment(tablePane, HPos.CENTER);
		        GridPane.setValignment(tablePane, VPos.TOP);
		        GridPane.setMargin(tablePane, new Insets(10, 0,0,0));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		});
        filtersPane = new HBox(8.0, dateFilterPane, typeFilterPane, filterButton);//create the
        //table filter pane
             
        tablePane = new GenericTablePane(null);//no values have been inserted yet from DB

        setPadding(new Insets(20, 20, 20, 20));
        setHgap(10);
        setVgap(10);
        
        add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setValignment(headerLabel, VPos.TOP);
        GridPane.setMargin(headerLabel, new Insets(10, 0,0,0));
        
        add(selectTableTypePane, 0, 1, 5, 1);
        GridPane.setHalignment(selectTableTypePane, HPos.CENTER);
        GridPane.setValignment(selectTableTypePane, VPos.TOP);
        GridPane.setMargin(selectTableTypePane, new Insets(10, 0,0,0));
        
        add(filtersPane, 0, 2, 4, 1);
        GridPane.setHalignment(filtersPane, HPos.CENTER);
        GridPane.setValignment(filtersPane, VPos.TOP);
        GridPane.setMargin(filtersPane, new Insets(10, 0,0,0));
        
        add(tablePane, 0, 3, 4, 1);
        GridPane.setHalignment(tablePane, HPos.CENTER);
        GridPane.setValignment(tablePane, VPos.TOP);
        GridPane.setMargin(tablePane, new Insets(10, 0,0,0));
        
        Scene scene = new Scene(mainPane,1200,800);
        window.setScene(scene);
        window.show();
    }
*/
	private void createTypeFilterPane() {
   		ObservableList<String> types = getProductTypes();
   		productTypeComboBox.getItems().addAll(types);
   		productTypeComboBox.setValue(types.get(0));
   		
   		//TODO actual effect of comboBox selection
   		//productTypeComboBox.setOnAction(
   		  //    e -> System.out.println(productTypeComboBox.getValue()));
   		
   		typeFilterPane.getChildren().add(productTypeComboBox);
	}
   	
   	private ObservableList<String> getProductTypes(){
   		ArrayList<String> temp = new ArrayList<>();
   		temp.add("none");
   		//TODO changing to getting al product types from db
   		for (ProductType product: ProductType.values()) {
   			temp.add(product.getDescription());
   		}
   		return FXCollections.observableArrayList(temp);
   	}
	
	private void createDateFilterPane() {
		startDatePicker.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				LocalDate date = startDatePicker.getValue();
		        //System.err.println("Selected date: " + date);
			}
		});
		
		//TODO binding the dates so that only dates after start can be picked as end
		endDatePicker.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				LocalDate date = endDatePicker.getValue();
		        //System.err.println("Selected date: " + date);
			}
		});
		dateFilterPane.setPadding(new Insets(0, 5, 0, 5));
		dateFilterPane.getChildren().addAll(startDate, startDatePicker, endDate, endDatePicker);
	}
	
	private void createSelectTableTypePane() {
		ToggleGroup showModeGroup = new ToggleGroup();
		purchaseRadioButton.setToggleGroup(showModeGroup);
		purchaseRadioButton.setSelected(true);
		productsRadioButton.setToggleGroup(showModeGroup);
		
		purchaseRadioButton.setOnAction(e -> {
			if (purchaseRadioButton.isSelected()){
				//System.out.println("Purchase Button Selected");
				if (selectTableTypePane.getChildren().contains(sortAscendingRadioButton)) {
					selectTableTypePane.getChildren().remove(sortAscendingRadioButton);
					selectTableTypePane.getChildren().remove(sortDescendingRadioButton);
				}
			}
		});
		
		productsRadioButton.setOnAction(e -> {
			if (productsRadioButton.isSelected()) {
				//System.out.println("Products Button Selected");
				selectTableTypePane.getChildren().addAll(sortAscendingRadioButton, sortDescendingRadioButton);
				sortAscendingRadioButton.setSelected(true);
			}
		});
		ObservableList<String> groupingTypes = getGroupingTypes();
		groupingComboBox.getItems().addAll(groupingTypes);
		groupingComboBox.setValue(groupingTypes.get(0));
		groupingComboBox.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println(groupingComboBox.getValue());
				
			}
		});
		
		mineCheckBox.setSelected(true);
		
		EventHandler<ActionEvent> checkBoxHandler = new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				CheckBox temp = (CheckBox)(event.getSource());
				if (temp.isSelected()) {
					mineCheckBox.setSelected(false);
					customersCheckBox.setSelected(false);
					workersCheckBox.setSelected(false);
					temp.setSelected(true);
				}
				else
					temp.setSelected(false);
				System.out.println("mine is selected = " + mineCheckBox.isSelected());
				if (selectTableTypePane.getChildren().contains(customersCheckBox))
					System.out.println("customers is selected = " + customersCheckBox.isSelected());
				if (selectTableTypePane.getChildren().contains(workersCheckBox))
					System.out.println("workers is selected = " + workersCheckBox.isSelected());
				
			}
		};
		mineCheckBox.setOnAction(checkBoxHandler);
		customersCheckBox.setOnAction(checkBoxHandler);
		workersCheckBox.setOnAction(checkBoxHandler);
		
		ToggleGroup selectProductfiltering = new ToggleGroup();
		sortDescendingRadioButton.setToggleGroup(selectProductfiltering);
		sortAscendingRadioButton.setToggleGroup(selectProductfiltering);
		
		selectTableTypePane.getChildren().addAll(purchaseRadioButton, productsRadioButton, groupingComboBox);
		selectTableTypePane.getChildren().addAll(mineCheckBox, customersCheckBox, workersCheckBox);
	}
	
	private ObservableList<String> getGroupingTypes(){
   		ArrayList<String> temp = new ArrayList<>();
   		//TODO changing to getting al product types from db
   		temp.add("Seperately");
   		temp.add("Monthly");
   		temp.add("Yearly");
   		return FXCollections.observableArrayList(temp);
   	}
	
	private void setHeader() {
	   	// Add Header
	       headerLabel = new Label("View " + WindowType.PURCHASE_HISTORY.getText());
	       headerLabel.setFont(ViewEffects.getHeadersFont());
	       
	       headerLabel.setEffect(ViewEffects.getShadowEffect(5, 5));
   }

	@Override
	public void updateData(DataType data) {
		tablePane.updateData(data);

	}

	@Override
	public void clearData() {
		getChildren().remove(tablePane);
		tablePane = new GenericTablePane(null);
		add(tablePane, 0, 3, 4, 1);
        GridPane.setHalignment(tablePane, HPos.CENTER);
        GridPane.setValignment(tablePane, VPos.TOP);
        GridPane.setMargin(tablePane, new Insets(10, 0,0,0));
	}
	
	private String getSelectedFocus() {
		if (purchaseRadioButton.isSelected())
			return purchaseRadioButton.getText();
		else
			return productsRadioButton.getText();
	}
	
	private String getSetlectedTimeAggregation() {
		return groupingComboBox.getValue();
	}
	
	private String getSelectedGroupAggregation() throws Exception {
		if (mineCheckBox.isSelected())
			return mineCheckBox.getText();
		else if (workersCheckBox.isSelected())
			return workersCheckBox.getText();
		else if (customersCheckBox.isSelected())
			return customersCheckBox.getText();
		else throw new Exception("No checkbox selected");
	}
	
	private LocalDate getSelectedFromDate() {
		return startDatePicker.getValue();
	}
	
	private LocalDate getSelectedUntilDate() {
		return endDatePicker.getValue();
	}
	
	private String getSelectedProductType() {
		return productTypeComboBox.getValue();
	}
	
	private String getSelectedSortingType() {
		if (selectTableTypePane.getChildren().contains(sortAscendingRadioButton)) {
			if (sortAscendingRadioButton.isSelected())
				return "asc";
			return "desc";
		}
		return null;
	}

}
