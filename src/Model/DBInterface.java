package Model;

import java.sql.SQLException;

public interface DBInterface {

	void dbConnect(String userName,String password);
	void init_tables() throws SQLException;
	void register_user(String username,String pass,int roleID,double balance);
	void delete_user(String userName) throws SQLException;
	void change_password(String userName,String newPass) throws SQLException;
	void change_discount(String roleName,int discountValue);
	User get_user(String userName);
	/*
    FUNCTION get_user(username users.username%type) return users%rowtype;
    */
	
}
