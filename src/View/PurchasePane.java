package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PurchasePane extends Application {

	private Stage window;
	private DataTypeGenericForTable data;
	
	private Label headerLabel;
	private Label addToCartProductIDLabel = new Label("Product ID: ");
	private Label addToCartProductAmountLabel = new Label("Amount: ");
	private Label removeFromCartProductIDLabel = new Label ("Product ID: ");
	private Label paymentLeftLabel = new Label ("Payment left: ");
	private Label thePaymentLabel = new Label ();
	
	private RadioButton cartButton = new RadioButton("Cart");
	private RadioButton productsButton = new RadioButton("Products");
	
	private Button addToCartButton = new Button("Add to cart");
	private Button removeFromCartButton = new Button ("Remove");
	private Button purchaseCartButton = new Button("Purchase Cart");
	
	private TextField addToCartProductIDText = new TextField();
	private TextField addToCartProductAmountText = new TextField();
	private TextField removeFromCartProductIDText = new TextField();
	
	private GridPane mainPane = new GridPane();
	private HBox tableChoicePane = new HBox(8.0);
	private HBox addToCartPane = new HBox(8.0);
	private HBox removeFromCartPane = new HBox(8.0);
	private HBox purchasePane = new HBox(8.0);
	private GenericTablePane tablePane = null;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle(WindowType.PRODUCTS.getText());
        
        mainPane.setPadding(new Insets(20, 20, 20, 20));
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        
        setHeader();//create the header of the pane
        createTableChoicePane();
        createRemoveFromCartPane();
        mainPane.getChildren().remove(removeFromCartPane);
        createAddToCartPane();
        createProductsTablePane();
        createPurchasePane();
        mainPane.getChildren().remove(purchasePane);
        
        Scene scene = new Scene(mainPane,1200,800);
        window.setScene(scene);
        window.show();

	}

	private void setHeader() {
       headerLabel = new Label(WindowType.PRODUCTS.getText());
       headerLabel.setFont(ViewEffects.getHeadersFont());	       
       headerLabel.setEffect(ViewEffects.getShadowEffect(5, 5));
       mainPane.add(headerLabel, 0, 0, 2, 1);
       GridPane.setHalignment(headerLabel, HPos.CENTER);
       GridPane.setValignment(headerLabel, VPos.TOP);
       GridPane.setMargin(headerLabel, new Insets(10, 0,0,0));		
	}

	public static void main(String[] args) {
        launch(args);
    }
	
	private void createTableChoicePane() {
		ToggleGroup tableChoiceGroup = new ToggleGroup();
		cartButton.setToggleGroup(tableChoiceGroup);
		productsButton.setSelected(true);
		productsButton.setToggleGroup(tableChoiceGroup);
		
		cartButton.setOnAction(e -> {
			if (cartButton.isSelected()){
				System.out.println("Cart Button Selected");
				if (mainPane.getChildren().contains(addToCartPane))
					mainPane.getChildren().remove(addToCartPane);
				mainPane.getChildren().add(removeFromCartPane);
				createCartTablePane();
				mainPane.getChildren().add(purchasePane);
				setPaymentLeft();
			}
		});
		
		productsButton.setOnAction(e -> {
			if (productsButton.isSelected()) {
				System.out.println("Products Button Selected");
				if (mainPane.getChildren().contains(removeFromCartPane)) {
					mainPane.getChildren().remove(removeFromCartPane);
					mainPane.getChildren().remove(purchasePane);
				}
				mainPane.getChildren().add(addToCartPane);
				createProductsTablePane();
			}
		});
		tableChoicePane.getChildren().addAll(cartButton, productsButton);
		mainPane.add(tableChoicePane, 0, 1, 2, 1);
		GridPane.setHalignment(tableChoicePane, HPos.CENTER);
		GridPane.setValignment(tableChoicePane, VPos.TOP);
		GridPane.setMargin(tableChoicePane, new Insets(10,0,0,0));			
	}
	
	private void createAddToCartPane() {
		addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				ControllerInstance.getInstance().getCont().addProductToCart(Integer.parseInt(addToCartProductIDText.getText()), Integer.parseInt(addToCartProductAmountText.getText()));
				System.out.println("Product added");
			}
		});
		addToCartPane.getChildren().addAll(addToCartProductIDLabel, addToCartProductIDText, addToCartProductAmountLabel, addToCartProductAmountText, addToCartButton);
		mainPane.add(addToCartPane, 0, 2, 5, 1);
		GridPane.setHalignment(addToCartPane, HPos.CENTER);
		GridPane.setValignment(addToCartPane, VPos.TOP);
		GridPane.setMargin(addToCartPane, new Insets(10,0,0,0));
	}
	
	private void createRemoveFromCartPane(){
		removeFromCartButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				ControllerInstance.getInstance().getCont().removeProductFromCart(Integer.parseInt(removeFromCartProductIDText.getText()));
				System.out.println("Product removed");
				mainPane.getChildren().remove(tablePane);
				createCartTablePane();
				setPaymentLeft();
			}
		});
		
		removeFromCartPane.getChildren().addAll(removeFromCartProductIDLabel, removeFromCartProductIDText, removeFromCartButton);
		mainPane.add(removeFromCartPane, 0, 2, 3, 1);
		GridPane.setHalignment(removeFromCartPane, HPos.CENTER);
		GridPane.setValignment(removeFromCartPane, VPos.TOP);
		GridPane.setMargin(removeFromCartPane, new Insets(10,0,0,0));

	}
	
	private void createCartTablePane() {
		if (mainPane.getChildren().contains(tablePane))
			mainPane.getChildren().remove(tablePane);
		data = ControllerInstance.getInstance().getCont().getCartDetails();
		tablePane = new GenericTablePane(data);
		mainPane.add(tablePane, 0, 3, 5, 1);
		GridPane.setHalignment(tablePane, HPos.CENTER);
		GridPane.setValignment(tablePane, VPos.TOP);
		GridPane.setMargin(tablePane, new Insets(10,0,0,0));		
	}
	
	private void createProductsTablePane() {
		if (mainPane.getChildren().contains(tablePane))
			mainPane.getChildren().remove(tablePane);
		data = ControllerInstance.getInstance().getCont().get_all_produces();
		tablePane = new GenericTablePane(data);
		mainPane.add(tablePane, 0, 3, 5, 1);
		GridPane.setHalignment(tablePane, HPos.CENTER);
		GridPane.setValignment(tablePane, VPos.TOP);
		GridPane.setMargin(tablePane, new Insets(10,0,0,0));		
	}
	
	private void createPurchasePane() {
		purchaseCartButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				ControllerInstance.getInstance().getCont().purchase();
				mainPane.getChildren().remove(tablePane);
				createCartTablePane();
				setPaymentLeft();
			}
		});
		setPaymentLeft();
		purchasePane.getChildren().addAll(paymentLeftLabel, thePaymentLabel, purchaseCartButton);
		mainPane.add(purchasePane, 0, 4, 3, 1);
		GridPane.setHalignment(purchasePane, HPos.CENTER);
		GridPane.setValignment(purchasePane, VPos.TOP);
		GridPane.setMargin(purchasePane, new Insets(10,0,0,0));			
	}
	
	private void setPaymentLeft() {
		thePaymentLabel.setText(ControllerInstance.getInstance().getCont().getPaymentLeft()+"");
	}

}
