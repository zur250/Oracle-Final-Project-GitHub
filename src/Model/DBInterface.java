package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DBInterface {

	void dbConnect(String userName,String password);
	void init_tables() throws SQLException;
	void register_user(String username,String pass,int roleID,double balance,long phone) throws SQLException;
	void delete_user(String userName) throws SQLException;
	void change_password(String userName,String newPass) throws SQLException;
	void change_discount(String roleName,int discountValue) throws SQLException;
	User get_user(String userName) throws SQLException;
	ArrayList<User> get_users() throws SQLException;
	ArrayList<Role> get_roles() throws SQLException;
	
	
}
