package View;


import java.util.Date;

import javafx.beans.property.SimpleStringProperty;

public class User {
	private String userName;
	private String phone;
	private String roleName;
	private double balance;
	private double seniority;
	private Date hireDate;
	
	public User(String userName, String phone, String roleName, double balance, double seniority, Date hireDate) {
		super();
		this.userName = userName;
		this.phone = phone;
		this.roleName = roleName;
		this.balance = balance;
		this.seniority = seniority;
		this.hireDate = hireDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getSeniority() {
		return seniority;
	}

	public void setSeniority(double seniority) {
		this.seniority = seniority;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	
	
	
}