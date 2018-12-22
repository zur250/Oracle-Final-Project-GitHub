package View;

public class ViewRole {
	private String roleName;
	private int percentage;
	
	public ViewRole(String roleName, int percentage) {
		super();
		this.roleName = roleName;
		this.percentage = percentage;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
	

}
