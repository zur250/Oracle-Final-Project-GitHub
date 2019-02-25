package Model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import View.DataTypeGenericForTable;

public interface DBInterface {

	void dbConnect(String userName,String password) throws Exception;
	void init_tables() throws SQLException;
	void register_user(String username,String pass,int roleID,double balance,long phone) throws SQLException;
	void delete_user(String userName) throws SQLException;
	void change_password(String userName,String newPass) throws SQLException;
	void change_discount(String roleName,int discountValue) throws SQLException;
	void change_Balance(String userName,double newBalance) throws SQLException;
	void change_User_Role(String userName,int roleID) throws SQLException;
	User get_user(String userName) throws SQLException;
	Role get_role(int roleID) throws SQLException;
	ArrayList<User> get_users() throws SQLException;
	ArrayList<Role> get_roles() throws SQLException;
	DataTypeGenericForTable get_products() throws SQLException;
	void add_product(String productName, String type, double price, int amount) throws SQLException;
	void update_product_price(int ID, double newPrice) throws SQLException;
	void update_prices (double percentage) throws SQLException;
	void delete_product (int ID) throws SQLException;
	void update_amount (int ID, int difference) throws SQLException;
	DataTypeGenericForTable viewPastPurchases(String username, LocalDate from, LocalDate until, String productType, String sorting, int function) throws SQLException;
	DataTypeGenericForTable getCartDetails(int cartID) throws SQLException;
	int create_new_cart(String username) throws SQLException;
	void delete_cart(int CartID) throws SQLException;
	void add_product_to_cart(int cartID, int productID, int amount) throws SQLException;
	void remove_product_from_cart(int cartID, int productID) throws SQLException;
	void purchase(int cartID) throws SQLException;
	double get_payment_left(int cartID) throws SQLException;
}
