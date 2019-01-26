package View;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CartView extends BasicView{

    Stage window;
    TableView<CartItem> table;
    GridPane gridPane;
    VBox vBox;
    GridPane bottomPane;
    private Button deletebtn;
    private Button updateRolebtn;;
    private Label totalPrice;

    
    public CartView() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Cart Page");

        gridPane = createFormPane();
        
        setHeader();
        table = createTable();
        
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
        Label headerLabel = new Label("View Cart");
        headerLabel.setFont(ViewEffects.getHeadersFont());
        
        headerLabel.setEffect(ViewEffects.getShadowEffect(5, 5));
        gridPane.add(headerLabel,0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setValignment(headerLabel, VPos.TOP);
        GridPane.setMargin(headerLabel, new Insets(10, 0,0,0));
    }

    private TableView<CartItem> createTable()
    {
        //Prod ID column
        TableColumn<CartItem, Integer> prudctIDColumn = new TableColumn<>("Product ID");
        prudctIDColumn.setMinWidth(100);
        prudctIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        
        // Prod Name column
        TableColumn<CartItem, String> prodNameColumn = new TableColumn<>("Product Name");
        prodNameColumn .setMinWidth(100);
        prodNameColumn .setCellValueFactory(new PropertyValueFactory<>("ProductName"));

        //ammount column
        TableColumn<CartItem, Integer> ammountColumn = new TableColumn<>("Ammount");
        ammountColumn.setMinWidth(100);
        ammountColumn.setCellValueFactory(new PropertyValueFactory<>("Ammount"));
        

        //price column
        TableColumn<CartItem, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        TableView<CartItem> t = new TableView<CartItem>();
        t.setMaxHeight(300);
        t.setItems(getCart());
        t.getColumns().addAll(prudctIDColumn,prodNameColumn ,ammountColumn, priceColumn);
        
        
        return t;

    }

	private GridPane createFormPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        //gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(20, 20, 20, 20));

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

	private void createBootomPane() {
        
     // Add delete Button
        deletebtn = new Button("Delete Item");
        deletebtn.setPrefHeight(40);
        deletebtn.setDefaultButton(true);
        deletebtn.setPrefWidth(100);
        gridPane.add(deletebtn, 0,3, 2, 1);
        GridPane.setHalignment(deletebtn, HPos.LEFT);
        GridPane.setMargin(deletebtn, new Insets(20, 0,20,0));

        deletebtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(table.getSelectionModel().getSelectedItem() == null) {
                	ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please select an Item to delete");
                }
                else
                	// Delete
                	System.out.println("deleted");
            }
        });
        
        Label lbl = new Label("Total Price:");
        lbl.setPrefHeight(40);
        lbl.setPrefWidth(100);
        gridPane.add(lbl, 0,2, 1, 1);
        GridPane.setHalignment(lbl, HPos.LEFT);
        GridPane.setMargin(lbl, new Insets(20, 0,20,0));
        
        Integer count = 0;
        for (CartItem item : table.getItems()) {
            count = (int) (count + item.getAmmount()*item.getPrice());
        }
        
        totalPrice = new Label(count.toString());
        totalPrice.setPrefHeight(40);
        totalPrice.setPrefWidth(100);
        gridPane.add(totalPrice, 1,2, 1, 1);
        GridPane.setHalignment(totalPrice, HPos.LEFT);
        GridPane.setMargin(totalPrice, new Insets(20, 0,20,0));

	}
    
    //Get all of the products
    public ObservableList<CartItem> getCart(){
        ObservableList<CartItem> cartItems = FXCollections.observableArrayList();
        cartItems.add(new CartItem(1,"item 1",5,100));
        cartItems.add(new CartItem(2,"item 2",5,200));
        cartItems.add(new CartItem(3,"item 3",5,300));
        cartItems.add(new CartItem(4,"item 4",5,400));
        cartItems.add(new CartItem(5,"item 5",5,500));
        return cartItems;
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
