package Model;

import java.sql.*;

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
	public void register_user(String username, String pass, int roleID, double balance) {
		// TODO Auto-generated method stub
		
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
	public void change_discount(String roleName, int discountValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User get_user(String userName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args)
	{ 
		DBImpl d = new DBImpl();
		d.dbConnect("admin", "147258");
		try {
			d.change_password("Admin","147258852");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}