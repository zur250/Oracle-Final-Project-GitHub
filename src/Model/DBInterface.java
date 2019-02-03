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
	DataTypeGenericForTable viewPastPurchases(LocalDate from, LocalDate until, String productType, int function);
}
