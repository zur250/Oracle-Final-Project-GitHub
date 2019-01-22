package Model;

import java.util.Date;

public class Role {
	private String roleName;
	private int roleID;
	private int percentage;
	
	public Role(String roleName, int roleID, int percentage) {
		super();
		this.roleName = roleName;
		this.roleID = roleID;
		this.percentage = percentage;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public String getRoleName() {
		return roleName;
	}

	public int getRoleID() {
		return roleID;
	}
	
	
}
