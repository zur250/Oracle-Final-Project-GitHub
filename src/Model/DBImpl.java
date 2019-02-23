package Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import View.DataTypeGenericForTable;
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
		        cur_User = new User(rs.getString(1), rs.getString(2), rs.getInt(5), rs.getDate(4), rs.getInt(6), rs.getDouble(3));
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
	
	@Override
	public DataTypeGenericForTable getCartDetails(int cartID) {
		final String[] columnNames = {"Product name", "Product ID", "Amount", "Total value"};
		List<Object> temp;
		List<List<Object>> tableContent = new ArrayList<>();
		List<String> columns = new ArrayList<>();
		CallableStatement stmt;
		for (int i=0; i<columnNames.length; i++)
			columns.add(columnNames[i]);
		try {
			stmt = conn.prepareCall("BEGIN Zur.MAKE_PURCHASE_PACKAGE.view_cart(?,?); END;");			
			stmt.setInt(1, cartID);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(2);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getDouble(4));
		        
				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return new DataTypeGenericForTable(columns, tableContent);
	}
	
	@Override
	public int create_new_cart(String username) {
		CallableStatement stmt;
		try {
			stmt = conn.prepareCall("{? = call Zur.MAKE_PURCHASE_PACKAGE.create_new_cart(?)}");
		
		stmt.registerOutParameter(1, Types.INTEGER);
		stmt.setString(2,username);       
		stmt.execute(); 
		int output = stmt.getInt(1);
		return output;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public void delete_cart(int CartID) throws SQLException {
		CallableStatement stmt;
		stmt = conn.prepareCall("BEGIN Zur.MAKE_PURCHASE_PACKAGE.delete_cart(?); END;");			
		stmt.setInt(1, CartID);
		stmt.executeUpdate();
	}
	
	@Override
	public void add_product_to_cart(int cartID, int productID, int amount) throws SQLException {
		CallableStatement stmt;
		stmt = conn.prepareCall("BEGIN Zur.MAKE_PURCHASE_PACKAGE.add_product_to_cart(?,?,?); END;");			
		stmt.setInt(1, productID);
		stmt.setInt(2, cartID);
		stmt.setInt(3, amount);
		stmt.executeUpdate();
	}
	
	@Override
	public void remove_product_from_cart(int cartID, int productID) throws SQLException {
		CallableStatement stmt;
		stmt = conn.prepareCall("BEGIN Zur.MAKE_PURCHASE_PACKAGE.delete_product_from_cart(?,?); END;");			
		stmt.setInt(1, productID);
		stmt.setInt(2, cartID);
		stmt.executeUpdate();		
	}
	
	@Override
	public void purchase(int cartID) throws SQLException {
		CallableStatement stmt;
		stmt = conn.prepareCall("BEGIN Zur.MAKE_PURCHASE_PACKAGE.purchase(?); END;");			
		stmt.setInt(1, cartID);
		stmt.executeUpdate();
	}
	
	@Override
	public double get_payment_left(int cartID) {
		CallableStatement stmt;
		try {
			stmt = conn.prepareCall("{? = call Zur.MAKE_PURCHASE_PACKAGE.get_payment_left(?)}");
		
		stmt.registerOutParameter(1, Types.DOUBLE);
		stmt.setInt(2,cartID);       
		stmt.execute(); 
		double output = stmt.getDouble(1);
		return output;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public DataTypeGenericForTable get_products() {
		final String[] columnNames = {"Product ID", "Price", "Product name", "Amount in stock", "Product type"};
		List<Object> temp;
		List<List<Object>> tableContent = new ArrayList<>();
		List<String> columns = new ArrayList<>();
		CallableStatement stmt;
		for (int i=0; i<columnNames.length; i++)
			columns.add(columnNames[i]);
		
		try {
			stmt = conn.prepareCall("BEGIN Zur.MAKE_PURCHASE_PACKAGE.show_all_products(?); END;");			
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(1);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getDouble(2));
		        temp.add(rs.getString(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getString(5));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return new DataTypeGenericForTable(columns, tableContent);
	}

	@Override
	public void add_product(String productName, String type, double price, int amount) throws SQLException {
		CallableStatement stmt;
		stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_MANAGEMENT_PACKAGE.add_new_product(?,?,?,?); END;");			
		stmt.setString(1, productName);
		stmt.setString(2, type);
		stmt.setDouble(3, price);
		stmt.setInt(4, amount);
		stmt.executeUpdate();
	}

	@Override
	public void update_product_price(int ID, double newPrice) throws SQLException {
		CallableStatement stmt;
		stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_MANAGEMENT_PACKAGE.update_product_price(?,?); END;");			
		stmt.setInt(1, ID);
		stmt.setDouble(2, newPrice);
		stmt.executeUpdate();
	}

	@Override
	public void update_prices(double percentage) throws SQLException {
		CallableStatement stmt;
		stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_MANAGEMENT_PACKAGE.update_all_product_prices(?); END;");			
		stmt.setDouble(1, percentage);
		stmt.executeUpdate();		
	}

	@Override
	public void delete_product(int ID) throws SQLException {
		CallableStatement stmt;
		stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_MANAGEMENT_PACKAGE.delete_product(?); END;");			
		stmt.setInt(1, ID);
		stmt.executeUpdate();
	}

	@Override
	public void update_amount(int ID, int difference) throws SQLException {
		CallableStatement stmt;
		stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_MANAGEMENT_PACKAGE.update_product_amount(?,?); END;");			
		stmt.setInt(1, ID);
		stmt.setInt(2, difference);
		stmt.executeUpdate();
	}

	@Override
	public DataTypeGenericForTable viewPastPurchases(String username, LocalDate from, LocalDate until, String productType, String sorting, int function) {
		List<Object> temp;
		List<List<Object>> tableContent = new ArrayList<>();
		List<String> columns = new ArrayList<>();
		CallableStatement stmt;
		int columnFunction = function/10;
		if (columnFunction>=10) {
			columnFunction--;
		}
		final String[][] columnNames = {{"Cart number", "Number of Products", "Products value", "Running sum of value"},
				{"Year", "Month", "Number of products", "Month value", "Running sum value"},
				{"Year", "Number of products", "Year value", "Running sum year"},
				{"Worker", "Payment date", "Number of products", "Payment number", "Running sum value"},
				{"Year", "Month", "Total running value"},
				{"Year", "Total running value"},
				{"Customer", "Payment date", "Number of products", "Payment number", "Running sum value"},
				{"Year", "Month", "Total running value"},
				{"Year", "Total running value"},
				{"Product", "Price", "Purchased", "Total value"},
				{"Year", "Month", "Purchased", "Total value"},
				{"Year", "Purchased", "Total value"},
				{"Product", "Price", "Purchased", "Workers purchased", "Workers exist", "Total value"},
				{"Year", "Month", "Purchased", "Workers purchased", "Workers exist", "Total value"},
				{"Year", "Purchased", "Workers purchased", "Workers exist", "Total value"},
				{"Product", "Price", "Purchased", "Customers purchased", "Customers exist", "Total value"},
				{"Year", "Month", "Purchased", "Customers purchased", "Customers exist", "Total value"},
				{"Year", "Purchased", "Customers purchased", "Customers exist", "Total value"}};
		
		for (int i = 0; i < columnNames[columnFunction].length; i++)
			columns.add(columnNames[columnFunction][i]);
		
		switch (function) {
		case 0:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_purc(?,?,?); END;");			
			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case 1:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_purc_from(?,?,?,?); END;");			
			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(from));
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 2:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_purc_to(?,?,?,?); END;");			
			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(until));
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_purc_from_to(?,?,?,?,?); END;");			
			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(from));
			stmt.setDate(4, Date.valueOf(until));
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 10:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_purc_month(?,?,?); END;");			
			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 11:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_purc_month_from(?,?,?,?); END;");			
			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(from));
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 12:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_purc_month_to(?,?,?,?); END;");			
			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(until));
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 13:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_purc_month_from_to(?,?,?,?,?); END;");			
			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(from));
			stmt.setDate(4, Date.valueOf(until));
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 20:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_purc_year(?,?,?); END;");			
			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        
				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 21:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_purc_year_from(?,?,?,?); END;");			
			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(from));
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        
				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 22:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_purc_year_to(?,?,?,?); END;");			
			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(until));
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        
				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 23:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_purc_year_from_to(?,?,?,?,?); END;");			
			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(from));
			stmt.setDate(4, Date.valueOf(until));
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        
				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 30:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_purc(?,?); END;");
			stmt.setString(1, productType);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(2);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getDate(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 31:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_purc_from(?,?,?); END;");
			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getDate(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 32:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_purc_to(?,?,?); END;");
			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(until));
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getDate(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 33:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_purc_from_to(?,?,?,?); END;");
			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setDate(3, Date.valueOf(until));
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getDate(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 40:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_purc_month(?,?); END;");
			stmt.setString(1, productType);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(2);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 41:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_purc_month_from(?,?,?); END;");
			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 42:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_purc_month_to(?,?,?); END;");
			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(until));
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 43:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_purc_month_from_to(?,?,?,?); END;");
			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setDate(3, Date.valueOf(until));
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 50:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_purc_year(?,?); END;");
			stmt.setString(1, productType);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(2);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 51:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_purc_year_from(?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 52:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_purc_year_to(?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(until));
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 53:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_purc_year_from_to(?,?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setDate(3, Date.valueOf(until));
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 60:
			try {
			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_purc(?,?); END;");

			stmt.setString(1, productType);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(2);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getDate(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
			break;
		case 61:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_purc_from(?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getDate(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 62:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_purc_to(?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(until));
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getDate(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 63:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_purc_from_to(?,?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setDate(3, Date.valueOf(until));
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getDate(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 70:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_purc_month(?,?); END;");

			stmt.setString(1, productType);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(2);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 71:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_purc_month_from(?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 72:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_purc_month_to(?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(until));
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 73:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_purc_month_from_to(?,?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setDate(3, Date.valueOf(until));
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 80:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_purc_year(?,?); END;");

			stmt.setString(1, productType);
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(2);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 81:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_purc_year_from(?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 82:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_purc_year_to(?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(until));
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 83:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_purc_year_from_to(?,?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setDate(3, Date.valueOf(until));
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 100:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_prod(?,?,?,?); END;");			

			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 101:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_prod_from(?,?,?,?,?); END;");			

			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(from));
			stmt.setString(4, sorting);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 102:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_prod_to(?,?,?,?,?); END;");			

			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(until));
			stmt.setString(4, sorting);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 103:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_prod_from_to(?,?,?,?,?,?); END;");			

			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(from));
			stmt.setDate(4, Date.valueOf(until));
			stmt.setString(5, sorting);
			stmt.registerOutParameter(6, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(6);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 110:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_prod_month(?,?,?,?); END;");			

			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 111:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_prod_month_from(?,?,?,?,?); END;");			

			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(from));
			stmt.setString(4, sorting);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			break;
		case 112:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_prod_month_to(?,?,?,?,?); END;");			

			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(until));
			stmt.setString(4, sorting);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			break;
		case 113:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_prod_month_from_to(?,?,?,?,?,?); END;");			

			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(from));
			stmt.setDate(4, Date.valueOf(until));
			stmt.setString(5, sorting);
			stmt.registerOutParameter(6, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(6);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			break;
		case 120:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_prod_year(?,?,?,?); END;");			

			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 121:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_prod_year_from(?,?,?,?,?); END;");			

			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(from));
			stmt.setString(4, sorting);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 122:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_prod_year_to(?,?,?,?,?); END;");			

			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(until));
			stmt.setString(4, sorting);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 123:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_MINE.get_my_prod_year_from_to(?,?,?,?,?,?); END;");			

			stmt.setString(1, username);
			stmt.setString(2, productType);
			stmt.setDate(3, Date.valueOf(from));
			stmt.setDate(4, Date.valueOf(until));
			stmt.setString(5, sorting);
			stmt.registerOutParameter(6, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(6);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 130:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_prod(?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setString(2, sorting);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 131:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_prod_from(?,?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 132:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_prod_to(?,?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(until));
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 133:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_prod_from_to(?,?,?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setDate(3, Date.valueOf(until));
			stmt.setString(4, sorting);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 140:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_prod_month(?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setString(2, sorting);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 141:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_prod_month_from(?,?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 142:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_prod_month_to(?,?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(until));
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 143:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_prod_month_from_to(?,?,?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setDate(3, Date.valueOf(until));
			stmt.setString(4, sorting);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 150:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_prod_year(?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setString(2, sorting);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 151:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_prod_year_from(?,?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 152:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_prod_year_to(?,?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(until));
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 153:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_WORKERS.get_workers_prod_year_from_to(?,?,?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setDate(3, Date.valueOf(until));
			stmt.setString(4, sorting);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 160:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_prod(?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setString(2, sorting);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 161:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_prod_from(?,?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 162:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_prod_to(?,?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(until));
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 163:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_prod_from_to(?,?,?,?,?); END;");

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setDate(3, Date.valueOf(until));
			stmt.setString(4, sorting);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getString(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 170:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_prod_month(?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setString(2, sorting);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 171:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_prod_month_from(?,?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 172:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_prod_month_to(?,?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(until));
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 173:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_prod_month_from_to(?,?,?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setDate(3, Date.valueOf(until));
			stmt.setString(4, sorting);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));
		        temp.add(rs.getInt(6));
		        

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				System.out.println(temp.get(5));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 180:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_prod_year(?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setString(2, sorting);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(3);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 181:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_prod_year_from(?,?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 182:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_prod_year_to(?,?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(until));
			stmt.setString(3, sorting);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(4);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 183:
			try {

			stmt = conn.prepareCall("BEGIN Zur.PRODUCTS_PURCHASES_CUSTOMERS.get_cust_prod_year_from_to(?,?,?,?,?); END;");			

			stmt.setString(1, productType);
			stmt.setDate(2, Date.valueOf(from));
			stmt.setDate(3, Date.valueOf(until));
			stmt.setString(4, sorting);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.execute();
		    ResultSet rs = ((OracleCallableStatement)stmt).getCursor(5);
		    while (rs.next()) {
		    	temp = new ArrayList<>();
		    	temp.add(rs.getInt(1));
		        temp.add(rs.getInt(2));
		        temp.add(rs.getInt(3));
		        temp.add(rs.getInt(4));
		        temp.add(rs.getInt(5));

				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));
				System.out.println(temp.get(4));
				
				tableContent.add(temp);
		      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;

		default:
			break;
		}
		
		return new DataTypeGenericForTable(columns, tableContent);
	}

	
	public static void main(String[] args)
	{ 
		DBImpl d = new DBImpl();
		try {
			d.dbConnect("admin", "147258");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			//System.out.println(d.create_new_cart(username));
			//d.change_discount("Admin", 95);
			//System.out.println(d.get_users());
			//System.out.println(d.get_cart_price(1));
			//System.out.println(d.viewPastPurchases(username, null, null, "none", "asc", 160));
			//d.get_roles();
			//System.out.println(d.get_user("guy"));
			//d.get_users();			//d.viewPastPurchases(null, null, null, 0);
			//d.viewPastPurchases(LocalDate.now(), null, "none", 1);
			//d.viewPastPurchases(null, LocalDate.now(), "none", 2);
			//d.viewPastPurchases(LocalDate.now().minusDays(10), LocalDate.now(), "DAIRY", 3);
			//d.viewPastPurchases(null, null, "DAIRY", 10);
			//d.viewPastPurchases(LocalDate.now(), null, "DAIRY", 11);
			//d.viewPastPurchases(null, null, "DAIRY", 20);
			//d.viewPastPurchases(null, null, "DAIRY", 30);
			//d.viewPastPurchases(null, null, "food", 110);
			//User u = d.get_user("guy");
			//System.out.println(u.getUserName());
			//System.out.println(u.getPassword());
			//System.out.println(u.getPhoneNumber());
			//System.out.println(u.getBalance());
			//System.out.println(u.getHire_date());
			//System.out.println(u.getRoleID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	

	


}