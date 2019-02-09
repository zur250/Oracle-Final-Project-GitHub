package View;

import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class DataPane extends StackPane implements MainViewInterface {

	private HashMap<WindowType, ViewInterface> views = new HashMap<WindowType, ViewInterface>();

	private static DataPane instance;
	
	private UserType userRole;
	
	private LoginView login;
	private RegisterView register;
  
	private CartView cart;
	private HomepageView profile;
	//private EditRolesPane editRoles;
	private DiscountView discount;
	//private AddProductPane addProduct;
	private ViewPurchaseHistoryPane historyPurchases;
	//private ViewProductsPane products;
	private UsersView users;
	
	private ViewInterface currentPane;
	
	public static DataPane getInstance() {
		if(instance == null) {
			instance = new DataPane();
		}
		return instance;
	}
	
	private DataPane() {
		super();
		generateGeneralWindows();
		generateUserWindows();
		currentPane = views.get(WindowType.LOGIN);
		this.getChildren().add((Node) currentPane);
	}
	
		
	public ViewInterface getCurrentPane() {
		return currentPane;
	}

	private void generateGeneralWindows() {
		login = new LoginView();
		register = new RegisterView();
		views.put(WindowType.LOGIN, login);
		views.put(WindowType.REGIRSTRATION, register);
	}
	
	private void generateUserWindows() {
		//cart = new CartPane(userRole);
		profile = new HomepageView();
		discount = new DiscountView();
		//addProduct = new AddProductPane();
		//historyPurchases = new ViewPurchaseHistoryPane(userRole);
		//products = new ViewProductsPane(userRole);
		users = new UsersView();
		//views.put(WindowType.CART, cart);
		views.put(WindowType.HOMEPAGE, profile);
		views.put(WindowType.DISCOUNT, discount);
		//views.put(WindowType.ADD_PRODUCT, addProduct);
		views.put(WindowType.PURCHASE_HISTORY, historyPurchases);
		//views.put(WindowType.PRODUCTS, products);
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
		this.getChildren().remove(currentPane);
		this.currentPane=this.views.get(type);
		if(type == WindowType.HOMEPAGE)
		{
			HomepageView home = (HomepageView) this.currentPane;
			home.setCur_User(ControllerInstance.getInstance().getCont().getCurUser());
		}
		else if(type == WindowType.USERS) {
			UsersView UsersPane = (UsersView) this.currentPane;
			UsersPane.refreshTable();
		}
		this.getChildren().add((Node) currentPane);
	}

	@Override
	public void setUser(UserType userRole) {
		this.userRole = userRole;
	}
}
