package Controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

import Model.*;
import Model.User;
import View.*;

public class ControllerImpl implements ControllerInterface {//should there be a "change view" function?
	//what about update user's role? from worker to customer in case he was fired for example...
	private DBInterface dbModel;
	private User curUser;
	private Role curUserRole;
	private final int adminRoleId = 1;
	private final int customerRoleId = 2;
	private final int managerRoleId = 3;
	private final String dbAdmin = "admin";
	private final String dbLogin = "login";
	private final String dbManager = "manager";
	private final String dbCustomer = "customer";
	private final String dbPass = "147258";
	private MainViewInterface curView;
	private int cartID;
	
	
	public ControllerImpl() throws Exception {
		super();
		dbModel = new DBImpl();
		dbModel.dbConnect(dbLogin, dbPass);
	}

	@Override
	public void Connect(String userName, String password) throws Exception {
			User temp = dbModel.get_user(userName);
			System.out.println(temp.getUserName());
			System.out.println(temp.getPassword());
			System.out.println(temp.getPassword().equals(password));
			if(temp.getPassword().equals(password)) {
				curUser = temp;
				curUserRole = dbModel.get_role(curUser.getRoleID());
				switch (curUserRole.getRoleName()) {
				case "Admin": dbModel.dbConnect(dbAdmin, dbPass);
								curView.setUser(UserType.ADMIN);
								curView.changeView(WindowType.HOMEPAGE);
								createNewCart();
								break;
				case "Customer": dbModel.dbConnect(dbCustomer, dbPass);
								curView.setUser(UserType.CUSTOMER);
								curView.changeView(WindowType.HOMEPAGE);
								createNewCart();
								break;
				case "Worker": dbModel.dbConnect(dbManager, dbPass);
								curView.setUser(UserType.WORKER);
								curView.changeView(WindowType.HOMEPAGE);
								createNewCart();
								break;	
				}
			}
			else {
				// TODO show relevant error message to the user
			}
	}

	@Override
	public void register(String userName, String password, double balance, long phoneNumber) throws SQLException { 
			dbModel.register_user(userName, password, customerRoleId, balance, phoneNumber);
	}

	@Override
	public void disconnect() throws Exception {
		if(curUser != null) {
			curUser = null;
				deleteCart();
				dbModel.dbConnect(dbLogin, dbPass);
				curView.setUser(UserType.ANONMOUS);
		}
		else {
			// TODO show to the user that he needs to be connected
			System.out.println("User is not connected to the db");
		}
		
	}
	/*This should be initiated when a login occurs*/
	@Override
	public void createNewCart() throws SQLException {
		this.cartID=dbModel.create_new_cart(curUser.getUserName());
	}
	
	@Override
	public void deleteCart() throws SQLException {
		dbModel.delete_cart(cartID);
	}

	@Override
	public void addProductToCart(int productID, int amount) throws SQLException {
		dbModel.add_product_to_cart(this.cartID, productID, amount);

	}

	@Override
	public void removeProductFromCart(int productID) throws SQLException {//same question as above
			dbModel.remove_product_from_cart(this.cartID, productID);
	}
	
	@Override
	public void purchase() throws SQLException {
			dbModel.purchase(this.cartID);
			deleteCart();
			createNewCart();	
	}

	@Override
	public void updateRoleDiscount(String roleName,int percentage) throws Exception {
		try {
			dbModel.change_discount(roleName, percentage);
			System.out.println("Update user : "+roleName+" to percentage of : " + percentage);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	public DataTypeGenericForTable get_all_produces() throws SQLException {
		return dbModel.get_products();
	}

	@Override
	public void updatePrice(int productID, double newPrice) throws SQLException {
			dbModel.update_product_price(productID, newPrice);
	}
	

	@Override
	public void updateAllPrices(double percent) throws SQLException {
		dbModel.update_prices(percent);

	}

	@Override
	public void updateAmount(int productID, int addedAmount) throws SQLException {
			dbModel.update_amount(productID, addedAmount);	
	}

	@Override
	public void deleteProductFromStore(int productID) throws SQLException {
			dbModel.delete_product(productID);
	}

	@Override
	public void addProductToStore(String productName, String type, double price, int ammountInStock) throws SQLException {
			dbModel.add_product(productName, type, price, ammountInStock);
	}

	@Override
	public void updateUserBalance(double newBalance) throws Exception {
					double tempBalance = curUser.getBalance()+newBalance;
					dbModel.change_Balance(curUser.getUserName(), tempBalance);
					curUser.setBalance(tempBalance);
	}

	@Override
	public void updateUserPassword(String currentPassword, String newPassword) throws Exception {
			if(curUser.getPassword().equals(currentPassword)) {
				dbModel.change_password(curUser.getUserName(), newPassword);
				curUser.setPassword(newPassword);
			}
			else
				throw new Exception("Passwords are not correct");
	}

	@Override
	public void registerView(MainViewInterface IView) {//wrong interface
		curView = IView;
		
	}

	@Override
	public void updateUserRole(String roleName, String userName) throws Exception {
		try {
			switch (roleName) {
				case "Admin": dbModel.change_User_Role(userName, adminRoleId);
							  break;
				case "Customer": dbModel.change_User_Role(userName, customerRoleId);
								 break;
				case "Employee": dbModel.change_User_Role(userName, managerRoleId);
								break;
			}
		} catch (SQLException e) {
			// Show relevant msg to the user that the user is not found
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public void deleteUser(String userName) throws Exception {
		try {
			dbModel.delete_user(userName);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public DataTypeGenericForTable viewPastPurchases(String username, String focusOn, String timeAggregation, String groupAggregation, LocalDate from,
			LocalDate until, String productType, String sorting) throws SQLException {
		int temp = 0;
		if (focusOn.equals("Products purchased"))
			temp+=100;
		if (timeAggregation.equals("Monthly"))
			temp+=10;
		else if (timeAggregation.equals("Yearly"))
			temp+=20;
		if (groupAggregation.equals("Workers"))
			temp+=30;
		else if (groupAggregation.equals("Customers"))
			temp+=60;
		if (from!=null)
			temp+=1;
		if (until!=null)
			temp+=2;
		return dbModel.viewPastPurchases(username, from, until, productType, sorting, temp);
		
	}
	
	@Override
	public View.User getCurUser() {
		View.User u =new View.User(curUser.getUserName(), String.valueOf(curUser.getPhoneNumber()), curUserRole.getRoleName(), curUser.getBalance(), 2, curUser.getHire_date());
		return u;
	}

	@Override
	public ArrayList<ViewRole> get_roles() throws SQLException {
			ArrayList<Role> dbRoles = dbModel.get_roles();
			ArrayList<ViewRole> roles = new ArrayList<>();
			for (Role dbRole : dbRoles) {
				roles.add(new ViewRole(dbRole.getRoleName(), dbRole.getPercentage()));
			}
			return roles;
	}

	@Override
	public ArrayList<View.User> get_users() throws SQLException {
			ArrayList<Model.User> dbUsers= dbModel.get_users();
			ArrayList<View.User> users = new ArrayList<>();
			for (Model.User dbUser : dbUsers) {
				users.add(new View.User(dbUser.getUserName(), Integer.toString(dbUser.getPhoneNumber()), Integer.toString(dbUser.getRoleID()), dbUser.getBalance(), Calendar.getInstance().getTime().getYear()-dbUser.getHire_date().getYear(), dbUser.getHire_date()));
			}
			return users;
	}
	
	@Override
	public DataTypeGenericForTable getCartDetails() throws SQLException {
		return dbModel.getCartDetails(this.cartID);
	}
	
	@Override
	public double getPaymentLeft() throws SQLException {
		return dbModel.get_payment_left(this.cartID);
	}
}
