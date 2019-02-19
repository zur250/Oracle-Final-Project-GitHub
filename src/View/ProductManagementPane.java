package View;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProductManagementPane extends GridPane implements ViewInterface {

	private Stage window;
	private DataTypeGenericForTable data;
	private ComboBox<String> newProductTypeComboBox = new ComboBox<String>();
	
	private Label headerLabel;
	private Label newProductPriceLabel = new Label("Pice: ");
	private Label newProductNameLabel = new Label("Name: ");
	private Label newProductTypeLabel = new Label("Type: ");
	private Label newProductAmountLabel = new Label("Amount: ");
	private Label updateProductPriceLabel = new Label("Product ID: ");
	private Label newPriceLabel = new Label("New Price: ");
	private Label updatePricesLabel = new Label("Percentage: ");
	private Label updateProductAmountLabel = new Label("Product ID: ");
	private Label addedAmuntLabel = new Label("Added amount: ");
	private Label deleteProductLabel = new Label("Product ID: ");
	
	private TextField newProductNameText = new TextField();
	private TextField newProductPriceText = new TextField();
	private TextField newProductAmountText = new TextField();
	private TextField updateProductPriceText = new TextField();
	private TextField newPriceText = new TextField();
	private TextField updatePricesText = new TextField();
	private TextField updateProductAmountText = new TextField();
	private TextField addedAmuntText = new TextField();
	private TextField deleteProductText = new TextField();
	
	private Button addNewProductButton = new Button("Add");
	private Button updateProductPriceButton = new Button("Update");
	private Button updateProductPricesButton = new Button("Update");
	private Button updateProductAmountButton = new Button("Update");
	private Button deleteProductButton = new Button("Delete");
	
	private HBox addNewProductPane = new HBox(8.0);
	private HBox updateProductPricePane = new HBox(8.0);
	private HBox updateProductPricesPane = new HBox(8.0);
	private HBox updateProductAmountPane = new HBox(8.0);
	private HBox deleteProductPane = new HBox(8.0);
	private GenericTablePane tablePane = null;

	
	
	public ProductManagementPane() {
		super();
		
        setPadding(new Insets(20, 20, 20, 20));
        setHgap(10);
        setVgap(10);
        
        setHeader();//create the header of the pane
        createAddNewProductPane();
        createUpdateProductPricePane();
        createUpdateProductPricesPane();
        createUpdateProductAmountPane();
        createDeleteProductPane();
        createUpdateTablePane();

	}

	/*public static void main(String[] args) {
        launch(args);
    }
	
	@Override
	public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle(WindowType.MANAGE_PRODUCTS.getText());
        
        setPadding(new Insets(20, 20, 20, 20));
        setHgap(10);
        setVgap(10);
        
        setHeader();//create the header of the pane
        createAddNewProductPane();
        createUpdateProductPricePane();
        createUpdateProductPricesPane();
        createUpdateProductAmountPane();
        createDeleteProductPane();
        createUpdateTablePane();
        

        Scene scene = new Scene(mainPane,1200,800);
        window.setScene(scene);
        window.show();
	}
*/
	private void createAddNewProductPane() {
		addNewProductButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				ControllerInstance.getInstance().getCont().addProductToStore(newProductNameText.getText(), newProductTypeComboBox.getValue(),
						Double.parseDouble(newProductPriceText.getText()), Integer.parseInt(newProductAmountText.getText()));
				createUpdateTablePane();
			}
		});
		
		ObservableList<String> types = getProductTypes();
   		newProductTypeComboBox.getItems().addAll(types);
   		newProductTypeComboBox.setValue(types.get(0));
		
		addNewProductPane.setPadding(new Insets(0, 5, 0, 5));
		addNewProductPane.getChildren().addAll(newProductNameLabel, newProductNameText, newProductTypeLabel, newProductTypeComboBox,
				newProductPriceLabel, newProductPriceText, newProductAmountLabel, newProductAmountText, addNewProductButton);
		
		add(addNewProductPane, 0, 1, 9, 1);
		GridPane.setHalignment(addNewProductPane, HPos.CENTER);
		GridPane.setValignment(addNewProductPane, VPos.TOP);
		GridPane.setMargin(addNewProductPane, new Insets(10,0,0,0));
	}
	
   	private ObservableList<String> getProductTypes(){
   		ArrayList<String> temp = new ArrayList<>();
   		for (ProductType product: ProductType.values()) {
   			temp.add(product.getDescription());
   		}
   		return FXCollections.observableArrayList(temp);
   	}
	
	private void createUpdateTablePane() {
		if (getChildren().contains(tablePane))
			getChildren().remove(tablePane);
		data = ControllerInstance.getInstance().getCont().get_all_produces();
		tablePane = new GenericTablePane(data);
		add(tablePane, 0, 6, 16, 1);
		GridPane.setHalignment(tablePane, HPos.CENTER);
		GridPane.setValignment(tablePane, VPos.TOP);
		GridPane.setMargin(tablePane, new Insets(10,0,0,0));
	}

	private void createDeleteProductPane() {
		deleteProductButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				ControllerInstance.getInstance().getCont().deleteProductFromStore(Integer.parseInt(deleteProductText.getText()));
				createUpdateTablePane();
			}
		});
		
		deleteProductPane.setPadding(new Insets(0, 5, 0, 5));
		deleteProductPane.getChildren().addAll(deleteProductLabel, deleteProductText, deleteProductButton);
		add(deleteProductPane, 0, 5, 3, 1);
		GridPane.setHalignment(deleteProductPane, HPos.CENTER);
		GridPane.setValignment(deleteProductPane, VPos.TOP);
		GridPane.setMargin(deleteProductPane, new Insets(10,0,0,0));
		
	}

	private void createUpdateProductPricesPane() {
		updateProductPricesButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				ControllerInstance.getInstance().getCont().updateAllPrices(Double.parseDouble(updatePricesText.getText()));
				createUpdateTablePane();
			}
		});
		
		updateProductPricesPane.setPadding(new Insets(0, 5, 0, 5));
		updateProductPricesPane.getChildren().addAll(updatePricesLabel, updatePricesText, updateProductPricesButton);
		add(updateProductPricesPane, 0, 3, 3, 1);
		GridPane.setHalignment(updateProductPricesPane, HPos.CENTER);
		GridPane.setValignment(updateProductPricesPane, VPos.TOP);
		GridPane.setMargin(updateProductPricesPane, new Insets(10,0,0,0));
	}

	private void createUpdateProductAmountPane() {
		updateProductAmountButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				ControllerInstance.getInstance().getCont().updateAmount(Integer.parseInt(updateProductAmountText.getText()), Integer.parseInt(addedAmuntText.getText()));
				createUpdateTablePane();
			}
		});
		
		updateProductAmountPane.setPadding(new Insets(0, 5, 0, 5));
		updateProductAmountPane.getChildren().addAll(updateProductAmountLabel, updateProductAmountText, addedAmuntLabel, addedAmuntText, updateProductAmountButton);
		add(updateProductAmountPane, 0, 4, 5, 1);
		GridPane.setHalignment(updateProductAmountPane, HPos.CENTER);
		GridPane.setValignment(updateProductAmountPane, VPos.TOP);
		GridPane.setMargin(updateProductAmountPane, new Insets(10,0,0,0));	
	}

	private void createUpdateProductPricePane() {
		updateProductPriceButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				ControllerInstance.getInstance().getCont().updatePrice(Integer.parseInt(updateProductPriceText.getText()), Double.parseDouble(newPriceText.getText()));
				createUpdateTablePane();
			}
		});
		
		updateProductPricePane.setPadding(new Insets(0, 5, 0, 5));
		updateProductPricePane.getChildren().addAll(updateProductPriceLabel, updateProductPriceText, newPriceLabel, newPriceText, updateProductPriceButton);
		add(updateProductPricePane, 0, 2, 5, 1);
		GridPane.setHalignment(updateProductPricePane, HPos.CENTER);
		GridPane.setValignment(updateProductPricePane, VPos.TOP);
		GridPane.setMargin(updateProductPricePane, new Insets(10,0,0,0));
	}
	
	

	private void setHeader() {
       headerLabel = new Label(WindowType.MANAGE_PRODUCTS.getText());
       headerLabel.setFont(ViewEffects.getHeadersFont());	       
       headerLabel.setEffect(ViewEffects.getShadowEffect(5, 5));
       add(headerLabel, 0, 0, 2, 1);
       GridPane.setHalignment(headerLabel, HPos.CENTER);
       GridPane.setValignment(headerLabel, VPos.TOP);
       GridPane.setMargin(headerLabel, new Insets(10, 0,0,0));
	}

	
	
	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateData(DataType data) {
		// TODO Auto-generated method stub
		
	}
}
