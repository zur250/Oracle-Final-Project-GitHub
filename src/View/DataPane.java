package View;

import java.util.HashMap;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class DataPane extends StackPane implements MainViewInterface {

	private HashMap<WindowType, ViewInterface> views = new HashMap<WindowType, ViewInterface>();

	private UserType userRole;
	
	private LoginView login;
	private RegisterView register;
  
	private CartView cart;
	private HomepageView profile;
	private EditRolesPane editRoles;
	private DiscountView discount;
	private AddProductPane addProduct;
	private ViewPurchaseHistoryPane historyPurchases;
	private ViewProductsPane products;
	private UsersView users;
	
	private ViewInterface currentPane;
	
	public DataPane() {
		super();
		generateGeneralWindows();
		currentPane = views.get(WindowType.LOGIN);
		//getChildren().add(currentPane);
	}
	
	private void generateGeneralWindows() {
		login = new LoginView();
		register = new RegisterView();
		views.put(WindowType.LOGIN, login);
		views.put(WindowType.REGIRSTRATION, register);
	}
	
	private void generateUserWindows() {
		cart = new CartPane(userRole);
		profile = new MainProfilePane(userRole);
		editRoles = new EditRolesPane();
		addProduct = new AddProductPane();
		historyPurchases = new ViewPurchaseHistoryPane(userRole);
		products = new ViewProductsPane(userRole);
		users = new UsersView();
		views.put(WindowType.CART, cart);
		views.put(WindowType.PROFILE, profile);
		views.put(WindowType.ROLES, editRoles);
		views.put(WindowType.ADD_PRODUCT, addProduct);
		views.put(WindowType.PURCHASE_HISTORY, historyPurchases);
		views.put(WindowType.PRODUCTS, products);
		views.put(WindowType.USERS, users);
	}
	
	
	@Override
	public void clearData() {
		this.currentPane.clearData();
		
	}
	
/*	@Override
	public void showError(ErrorMessage msg) {
		this.currentPane.showError(msg);
		
	}*/
	
	@Override
	public void updateData(DataType data) {
		this.currentPane.updateData(data);
	}

	@Override
	public void changeView(WindowType type) {
		this.currentPane=this.views.get(type);
	}

	@Override
	public void setUser(UserType userRole) {
		this.userRole = userRole;
		generateUserWindows();
	}
}
