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

	@Override
	public void addProductToCart(String productID, int ammount) {//how do I know which cart? is the user stored in the controller
		//or should it be passed as an argument in this function?
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeProductFromCart(String productID, int ammount) {//same question as above
		// TODO Auto-generated method stub
		
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
	public void updateProdctInStore(String productID, String productName, double price, int ammountInStock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProductFromStore(String productID) {//same as above
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProductToStore(String productName, int price, int ammountInStock) {//is the price int?
		// TODO Auto-generated method stub
		
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
	public void viewPastPurchases(String focusOn, String timeAggregation, String groupAggregation, LocalDate from,
			LocalDate until, String productType) {
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
		dbModel.viewPastPurchases(from, until, productType, temp);
		
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
	
	

}
