package View;

public enum WindowType {

	SIGNUP ("Sign up") ,REGIRSTRATION ("Register"), CART ("Cart"), MAIN ("Main"), DISCOUNTS("Manage Discount"),
	ADD_PRODUCT ("Add product"), ALL_PURCHASES("All Purchases"), PURCHASE("Purchase"), USERS("Show Users");
	
	final String text;
	private WindowType (String text) {
		this.text = text;
	}
	public String getText (){
		return text;
	}
}
