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
	
	
	public ControllerImpl() {
		super();
		dbModel = new DBImpl();
		try {
			dbModel.dbConnect(dbLogin, dbPass);
		} catch (Exception e1) {
			// TODO handle db user not exsist
			System.out.println("db User does not exist");
		}
	}

	@Override
	public void Connect(String userName, String password) {
		try {
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
								break;
				case "User": dbModel.dbConnect(dbCustomer, dbPass);
								curView.setUser(UserType.CUSTOMER);
								curView.changeView(WindowType.HOMEPAGE);
								break;
				case "Employee": dbModel.dbConnect(dbManager, dbPass);
								curView.setUser(UserType.WORKER);
								curView.changeView(WindowType.HOMEPAGE);
								break;	
				}
			}
			else {
				// TODO show relevant error message to the user
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void register(String userName, String password, double balance, long phoneNumber) { 
		try {
			dbModel.register_user(userName, password, customerRoleId, balance, phoneNumber);
		} catch (Exception e) {
			// TODO handle user allready exsist or some checks did not worked well
		}
	}

	@Override
	public void disconnect() {//shouldn't this function know who is connected?
		if(curUser != null) {
			curUser = null;
			try {
				dbModel.dbConnect(dbLogin, dbPass);
				curView.setUser(UserType.ANONMOUS);
			} catch (Exception e) {
				// TODO , for some reason cant connect with login user to the db. show relevant message to the user
				System.out.println(e.getMessage());
			}
		}
		else {
			// TODO show to the user that he needs to be connected
			System.out.println("User is not connected to the db");
		}
		
	}
	/*This should be initiated when a login occurs*/
	@Override
	public void createNewCart() {
		this.cartID=dbModel.create_new_cart("apachi");
	}
	
	@Override
	public void deleteCart() {
		try {
			dbModel.delete_cart(cartID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addProductToCart(int productID, int amount) {
		try {
			dbModel.add_product_to_cart(22, productID, amount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void removeProductFromCart(int productID) {//same question as above
		try {
			dbModel.remove_product_from_cart(22, productID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void purchase() {
		try {
			dbModel.purchase(22);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public DataTypeGenericForTable get_all_produces() {
		return dbModel.get_products();
	}

	@Override
	public void updatePrice(int productID, double newPrice) {
		try {
			dbModel.update_product_price(productID, newPrice);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void updateAllPrices(double percent) {
		try {
			dbModel.update_prices(percent);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateAmount(int productID, int addedAmount) {
		try {
			dbModel.update_amount(productID, addedAmount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void deleteProductFromStore(int productID) {
		try {
			dbModel.delete_product(productID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addProductToStore(String productName, String type, double price, int ammountInStock) {
		try {
			dbModel.add_product(productName, type, price, ammountInStock);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateUserBalance(double newBalance) throws Exception {
		if(curUser != null) {
			if(newBalance >0) {
				try {
					double tempBalance = curUser.getBalance()+newBalance;
					dbModel.change_Balance(curUser.getUserName(), tempBalance);
					curUser.setBalance(tempBalance);
				} catch (SQLException e) {
					// TODO write proper error
					System.out.println(e.getMessage());
				}
			}
			else {
				// TODO show to the user a message that he cant change balance to negative value
				throw new Exception("Cant change to negative value");
			}
		}
		else {
			// TODO show to the user that he needs to be connected
			throw new Exception("User is not connected to the db");
		}
		
	}

	@Override
	public void updateUserPassword(String currentPassword, String newPassword) throws Exception {
		if(curUser != null) {
			if(curUser.getPassword().equals(currentPassword)) {
				dbModel.change_password(curUser.getUserName(), newPassword);
				curUser.setPassword(newPassword);
			}
			else {
				// TODO show to the user a message that current password must be correct
				throw new Exception("Pls input a correct current password");
			}
		}
		else {
			// TODO show to the user that he needs to be connected
			System.out.println("User is not connected to the db");
		}
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
			LocalDate until, String productType, String sorting) {
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
	public ArrayList<ViewRole> get_roles() {
		try {
			ArrayList<Role> dbRoles = dbModel.get_roles();
			ArrayList<ViewRole> roles = new ArrayList<>();
			for (Role dbRole : dbRoles) {
				roles.add(new ViewRole(dbRole.getRoleName(), dbRole.getPercentage()));
			}
			return roles;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<View.User> get_users() {
		try {
			ArrayList<Model.User> dbUsers= dbModel.get_users();
			ArrayList<View.User> users = new ArrayList<>();
			for (Model.User dbUser : dbUsers) {
				users.add(new View.User(dbUser.getUserName(), Integer.toString(dbUser.getPhoneNumber()), Integer.toString(dbUser.getRoleID()), dbUser.getBalance(), Calendar.getInstance().getTime().getYear()-dbUser.getHire_date().getYear(), dbUser.getHire_date()));
			}
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public DataTypeGenericForTable getCartDetails() {
		return dbModel.getCartDetails(22);
	}
	
	@Override
	public double getPaymentLeft() {
		return dbModel.get_payment_left(22);
	}
}
