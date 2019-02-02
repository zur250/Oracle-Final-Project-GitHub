package Model;

import java.sql.*;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class DBImpl implements DBInterface {

	private Connection conn;
	public DBImpl() {
		
	}

	@Override
	public void dbConnect(String userName, String password) throws Exception {
		try {
			if(conn!=null) {
				conn.close();
			}
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", userName, password);
		}
		catch (Exception e){
			System.out.println(e);
			throw new Exception("Error connecting to the db : " +e.getMessage());
		}
	}

	@Override
	public void init_tables() throws SQLException {
		CallableStatement stmt = conn.prepareCall("{CALL Zur.users_pkg.fill_tables}");
		stmt.execute();
	}

	@Override
	public void register_user(String username, String pass, int roleID, double balance,long phone) throws SQLException {
		try {
		CallableStatement stmt = conn.prepareCall("{CALL Zur.users_pkg.register_user(?,?,?,?,?)}");
		stmt.setString(1, username);
		stmt.setString(2, pass);
		stmt.setInt(3, roleID);
		stmt.setLong(4, phone);
		stmt.setDouble(5, balance);
		stmt.executeUpdate();
		}
		catch (Throwable e) {
			System.out.println(e.getMessage());
		}
		
		
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
	public void change_User_Role(String userName, int roleID) throws SQLException {
		CallableStatement stmt = conn.prepareCall("{CALL Zur.users_pkg.change_user_role(?,?)}");
		stmt.setString(1, userName);
		stmt.setInt(2,roleID);
		stmt.executeUpdate();
		
	}
	
	@Override
	public void change_Balance(String userName, double newBalance) throws SQLException {
		CallableStatement stmt = conn.prepareCall("{CALL Zur.users_pkg.change_user_balance(?,?)}");
		stmt.setString(1, userName);
		stmt.setDouble(2,newBalance);
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
	
	@Override
	public Role get_role(int roleID) throws SQLException {
		try {
			Role cur_role = null;
			CallableStatement stmt = conn.prepareCall("BEGIN Zur.users_pkg.get_role(?,?); END;");
			stmt.setInt(1, roleID);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(2);
		    while (rs.next()) {
		    	cur_role = new Role(rs.getString("rolename"),rs.getInt("roleid"),rs.getInt("discount_percentage"));
		      }
			return cur_role;
			}
			catch(Exception e){
				System.out.println(e.getMessage()+"Role does not exsist?");
			}
			return null;
	}
	
	@Override
	public ArrayList<User> get_users() throws SQLException {
		try {
			User cur_User = null;
			ArrayList<User> users = new ArrayList<>();
			CallableStatement stmt = conn.prepareCall("BEGIN Zur.users_pkg.get_all_users(?); END;");
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(1);
		    while (rs.next()) {
		        cur_User = new User(rs.getString("username"), rs.getString("pass"), rs.getInt("phone"), rs.getDate("joindate"), rs.getInt("roleid"), rs.getDouble("balance"));
		        users.add(cur_User);

				System.out.println(cur_User.getUserName());
				System.out.println(cur_User.getPassword());
				System.out.println(cur_User.getPhoneNumber());
				System.out.println(cur_User.getBalance());
				System.out.println(cur_User.getHire_date());
				System.out.println(cur_User.getRoleID());
		      }
			return users;
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		return null;
	}

	@Override
	public ArrayList<Role> get_roles() throws SQLException {
		try {
			Role cur_Role = null;
			ArrayList<Role> roles = new ArrayList<>();
			CallableStatement stmt = conn.prepareCall("BEGIN Zur.users_pkg.get_all_roles(?); END;");
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(1);
		    while (rs.next()) {
		    	cur_Role = new Role(rs.getString("rolename"),rs.getInt("roleid"),rs.getInt("discount_percentage"));
		        roles.add(cur_Role);

				System.out.println(cur_Role.getRoleName());
				System.out.println(cur_Role.getRoleID());
				System.out.println(cur_Role.getPercentage());				
		      }
			return roles;
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		return null;
	}
	
	public static void main(String[] args)
	{ 
		DBImpl d = new DBImpl();
		try {
			d.dbConnect("login", "147258");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			User u = d.get_user("test12");
			System.out.println(u.getUserName());
			System.out.println(u.getPassword());
			System.out.println(u.getPhoneNumber());
			System.out.println(u.getBalance());
			System.out.println(u.getHire_date());
			System.out.println(u.getRoleID());
			//ArrayList<User> users_lst = d.get_users();
			/*ArrayList<Role> roles_lst = d.get_roles();
			Role r = d.get_role(1);
			System.out.println(r.getRoleID());
			System.out.println(r.getRoleName());
			System.out.println(r.getPercentage());*/
			//d.change_User_Role("test12", 2);
			//d.change_Balance("test12", 1234.55);
			//d.register_user("asdasdasd","147aaa147aaa",1,1234.2,548070392);
			System.out.println("done");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	

	


}