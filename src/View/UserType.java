package View;

public enum UserType {

	ADMIN ("Admin") ,WORKER ("Worker"), CUSTOMER("Customer");
	
	final String text;
	private UserType (String text) {
		this.text = text;
	}
	public String getText (){
		return text;
	}
}
