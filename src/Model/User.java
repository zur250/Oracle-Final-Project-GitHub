package Model;

import java.util.Date;

public class User {
	private String userName;
	private String password;
	private int phoneNumber;
	private Date hire_date;
	private int roleID;
	private double balance;
	
	public String getUserName() {
		return userName;
	}
	
	
	public String getPassword() {
		return password;
	}
	
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Date getHire_date() {
		return hire_date;
	}
	
	public int getRoleID() {
		return roleID;
	}
	
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}


	public User(String userName, String password, int phoneNumber, Date hire_date, int roleID, double balance) {
		super();
		this.userName = userName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.hire_date = hire_date;
		this.roleID = roleID;
		this.balance = balance;
	}
	
	
	
	

}
