package Model;

import java.sql.*;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class DBImpl implements DBInterface {

	private Connection conn;
	public DBImpl() {
		
	}

	@Override
	public void dbConnect(String userName, String password) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", userName, password);
		}
		catch (Exception e){
			System.out.println(e);
		}
	}

	@Override
	public void init_tables() throws SQLException {
		CallableStatement stmt = conn.prepareCall("{CALL Zur.users_pkg.fill_tables}");
		stmt.execute();
	}

	@Override
	public void register_user(String username, String pass, int roleID, double balance,long phone) throws SQLException {
		CallableStatement stmt = conn.prepareCall("{CALL Zur.users_pkg.register_user(?,?,?,?,?)}");
		stmt.setString(1, username);
		stmt.setString(2, pass);
		stmt.setInt(3, roleID);
		stmt.setLong(4, phone);
		stmt.setDouble(5, balance);
		stmt.execute();
		
	}

	@Override
	public void delete_user(String userName) throws SQLException {
		CallableStatement stmt = conn.prepareCall("{CALL Zur.users_pkg.drop_user(?)}");
		stmt.setString(1, userName);
		stmt.executeUpdate();
		
	}

	@Override
	public void change_password(String userName, String newPass) throws SQLException {
		CallableStatement stmt = conn.prepareCall("{CALL Zur.users_pkg.change_password(?,?)}");
		stmt.setString(1, userName);
		stmt.setString(2,newPass);
		stmt.executeUpdate();
		
	}

	@Override
	public void change_discount(String roleName, int discountValue) throws SQLException {
		CallableStatement stmt = conn.prepareCall("{CALL Zur.users_pkg.change_discount(?,?)}");
		stmt.setString(1, roleName);
		stmt.setInt(2,discountValue);
		stmt.executeUpdate();
		
	}

	@Override
	public User get_user(String userName) throws SQLException {
		try {
		User cur_User = null;
		CallableStatement stmt = conn.prepareCall("BEGIN Zur.users_pkg.get_user(?,?); END;");
		stmt.setString(1, userName);
		stmt.registerOutParameter(2, OracleTypes.CURSOR);
		stmt.execute();
	    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(2);
	    while (rs.next()) {
	        cur_User = new User(rs.getString("username"), rs.getString("pass"), rs.getInt("phone"), rs.getDate("joindate"), rs.getInt("roleid"), rs.getDouble("balance"));
	      }
		return cur_User;
		}
		catch(Exception e){
			System.out.println(e.getMessage()+"User does not exsist?");
		}
		return null;
	}
	
	public static void main(String[] args)
	{ 
		DBImpl d = new DBImpl();
		d.dbConnect("admin", "147258");
		try {
			User u = d.get_user("test12");
			System.out.println(u.getUserName());
			System.out.println(u.getPassword());
			System.out.println(u.getPhoneNumber());
			System.out.println(u.getBalance());
			System.out.println(u.getHire_date());
			System.out.println(u.getRoleID());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}