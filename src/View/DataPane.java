package View;

import java.sql.SQLException;
import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
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
	private ProductManagementPane manageProducts;
	private DiscountView discount;
	private ViewPurchaseHistoryPane historyPurchases;
	private UsersView users;
	private PurchasePane purchase;
	
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
		profile = new HomepageView();
		discount = new DiscountView();
		historyPurchases = new ViewPurchaseHistoryPane();
		manageProducts = new ProductManagementPane();
		users = new UsersView();
		purchase = new PurchasePane();
		
		views.put(WindowType.HOMEPAGE, profile);
		views.put(WindowType.DISCOUNT, discount);
		views.put(WindowType.PURCHASE_HISTORY, historyPurchases);
		views.put(WindowType.USERS, users);
		views.put(WindowType.MANAGE_PRODUCTS, manageProducts);
		views.put(WindowType.PRODUCTS, purchase);
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
			try {
				home.setCur_User(ControllerInstance.getInstance().getCont().getCurUser());
			} catch (SQLException e) {
				ErrorMessage.getInstance().showAlert(AlertType.ERROR, DataPane.getInstance().getScene().getWindow(), "Form Error!", e.getMessage());
			}
		}
		else if(type == WindowType.USERS) {
			UsersView UsersPane = (UsersView) this.currentPane;
			UsersPane.refreshTable();
		}
		else if (type == WindowType.MANAGE_PRODUCTS) {
			ProductManagementPane prPane = (ProductManagementPane) this.currentPane;
			prPane.createUpdateTablePane();
		}
		else if (type == WindowType.PRODUCTS) {
			PurchasePane prPane = (PurchasePane) this.currentPane;
			prPane.createProductsTablePane();
		}
		else if (type == WindowType.PURCHASE_HISTORY) {
			ViewPurchaseHistoryPane prPane = (ViewPurchaseHistoryPane) this.currentPane;
			prPane.clearData();
		}
		this.getChildren().add((Node) currentPane);
	}

	@Override
	public void setUser(UserType userRole) {
		this.userRole = userRole;
	}
}
