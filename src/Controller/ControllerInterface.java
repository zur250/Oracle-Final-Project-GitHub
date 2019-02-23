package Controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import View.*;

public interface ControllerInterface {
	void Connect(String userName,String password);
	void register(String userName,String password,double balance,long phoneNumber);
	void disconnect();// we have one user at a time so current connected user can be achived from the controller impl
	void addProductToCart(int productID,int amount) throws SQLException; // need to check that ammount < ammount in stock
	void removeProductFromCart(int productID);
	void updateRoleDiscount(String roleName,int percentage) throws Exception; 
	DataTypeGenericForTable get_all_produces();
	void updatePrice(int productID,double newPrice);
	void updateAllPrices(double percent) throws SQLException;
	void updateAmount(int productID, int addedAmount);
	void deleteProductFromStore(int productID); // need to check current user permisstions 
	void addProductToStore(String productName,String type,double price,int ammountInStock) throws SQLException; // need to check current usser permisstions
	void updateUserBalance(double newBalance) throws Exception; // we have one user at a time. current user can be achived from the controller impl
	void updateUserPassword(String currentPassword,String newPassword) throws Exception; // we have one user at a time. current user can be achived from controller impl
	void registerView(MainViewInterface IView);
	void updateUserRole(String roleName,String userName) throws Exception;
	void deleteUser(String userName) throws Exception;
	DataTypeGenericForTable viewPastPurchases(String username, String focusOn, String timeAggregation, String groupAggregation, LocalDate from,
			LocalDate until, String productType, String sorting);
	User getCurUser();
	ArrayList<ViewRole> get_roles();
	ArrayList<User> get_users();
	DataTypeGenericForTable getCartDetails();
	void purchase() throws SQLException;
	void createNewCart();
	void deleteCart();
	double getPaymentLeft();
}
