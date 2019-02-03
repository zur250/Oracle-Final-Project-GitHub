package Controller;

import java.sql.SQLException;
import java.time.LocalDate;

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
				case "Manager": dbModel.dbConnect(dbManager, dbPass);
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
	public void updateRoleDiscount(String roleID,int percentage) {//which role?
		// TODO Auto-generated method stub
		
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
	public void updateUserBalance(double newBalance) {
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
				System.out.println("Cant change to negative value");
			}
		}
		else {
			// TODO show to the user that he needs to be connected
			System.out.println("User is not connected to the db");
		}
		
	}

	@Override
	public void updateUserPassword(String currentPassword, String newPassword) {
		if(curUser != null) {
			if(curUser.getPassword().equals(currentPassword)) {
				try {
					dbModel.change_password(curUser.getUserName(), newPassword);
					curUser.setPassword(newPassword);
				} catch (SQLException e) {
					// TODO show error that something went wrong, maybe password isnt fit to our password policy
					System.out.println(e.getMessage());
				}
			}
			else {
				// TODO show to the user a message that current password must be correct
				System.out.println("Pls input a correct current password");
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
	public void updateUserRole(String roleName, String userName) {
		try {
			switch (roleName) {
				case "Admin": dbModel.change_User_Role(curUser.getUserName(), adminRoleId);
							  curUser.setRoleID(adminRoleId);
							  break;
				case "Customer": dbModel.change_User_Role(curUser.getUserName(), customerRoleId);
								 curUser.setRoleID(customerRoleId);
								 break;
				case "Manager": dbModel.change_User_Role(curUser.getUserName(), managerRoleId);
								curUser.setRoleID(managerRoleId);
								break;
			}
		} catch (SQLException e) {
			// Show relevant msg to the user that the user is not found
			System.out.println("User not Found");
		}
		
	}

	@Override
	public void deleteUser(String userName) {
		try {
			dbModel.delete_user(userName);
		} catch (SQLException e) {
			System.out.println("User does not exsist in the db?");
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
	
	
	
	

}
