package Controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import View.*;

public interface ControllerInterface {
	void Connect(String userName,String password) throws SQLException, Exception;
	void register(String userName,String password,double balance,long phoneNumber) throws SQLException;
	void disconnect() throws SQLException, Exception;// we have one user at a time so current connected user can be achived from the controller impl
	void addProductToCart(int productID,int amount) throws SQLException; // need to check that ammount < ammount in stock
	void removeProductFromCart(int productID) throws SQLException;
	void updateRoleDiscount(String roleName,int percentage) throws Exception; 
	DataTypeGenericForTable get_all_produces() throws SQLException;
	void updatePrice(int productID,double newPrice) throws SQLException;
	void updateAllPrices(double percent) throws SQLException;
	void updateAmount(int productID, int addedAmount) throws SQLException;
	void deleteProductFromStore(int productID) throws SQLException; // need to check current user permisstions 
	void addProductToStore(String productName,String type,double price,int ammountInStock) throws SQLException; // need to check current usser permisstions
	void updateUserBalance(double newBalance) throws Exception; // we have one user at a time. current user can be achived from the controller impl
	void updateUserPassword(String currentPassword,String newPassword) throws Exception; // we have one user at a time. current user can be achived from controller impl
	void registerView(MainViewInterface IView) throws SQLException;
	void updateUserRole(String roleName,String userName) throws Exception;
	void deleteUser(String userName) throws Exception;
	DataTypeGenericForTable viewPastPurchases(String username, String focusOn, String timeAggregation, String groupAggregation, LocalDate from,
			LocalDate until, String productType, String sorting) throws SQLException;
	User getCurUser() throws SQLException;
	ArrayList<ViewRole> get_roles() throws SQLException;
	ArrayList<User> get_users() throws SQLException;
	DataTypeGenericForTable getCartDetails() throws SQLException;
	void purchase() throws SQLException;
	void createNewCart() throws SQLException;
	void deleteCart() throws SQLException;
	double getPaymentLeft() throws SQLException;
}
