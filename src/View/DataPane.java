package View;

import java.util.HashMap;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class DataPane extends StackPane implements MainViewInterface {

	private HashMap<WindowType, ViewInterface> views = new HashMap<WindowType, ViewInterface>();

	private User user;
	
	private SignUpPane signup;
	private RegistrationPane register;
	private CartPane cart;
	private MainProfilePane profile;
	private EditDiscountPane discount;
	private AddProductPane addProduct;
	private PurchasesPane purchases;
	private NewPurchasePane purchase;
	private UsersPane users;
	
	private ViewInterface currentPane;
	
	public DataPane() {
		super();
		generateGeneralWindows();
		currentPane = views.get(WindowType.SIGNUP);
	}
	
	private void generateGeneralWindows() {
		signup = new SignUpPane();
		register = new RegistrationPane();
		views.put(WindowType.SIGNUP, signup);
		views.put(WindowType.REGIRSTRATION, register);
	}
	
	private void generateUserWindows() {
		cart = new CartPane(user);
		profile = new MainProfilePane(user);
		discount = new EditDiscountPane();
		addProduct = new AddProductPane();
		purchases = new PurchasesPane(user);
		purchase = new NewPurchasePane(user);
		users = new UsersPane();
		views.put(WindowType.CART, cart);
		views.put(WindowType.MAIN, profile);
		views.put(WindowType.DISCOUNTS, discount);
		views.put(WindowType.ADD_PRODUCT, addProduct);
		views.put(WindowType.ALL_PURCHASES, purchases);
		views.put(WindowType.PURCHASE, purchase);
		views.put(WindowType.USERS, users);
	}
	
	
	@Override
	public void clearData() {
		this.currentPane.clearData();
		
	}
	
	@Override
	public void showError(ErrorMessage msg) {
		this.currentPane.showError(msg);
		
	}
	
	@Override
	public void updateData(DataType data) {
		this.currentPane.updateData(data);
	}

	@Override
	public void changeView(WindowType type) {
		this.currentPane=this.views.get(type);
	}

	@Override
	public void setUser(User user) {
		this.user = user;
		generateUserWindows();
	}
}
