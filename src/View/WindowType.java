package View;

public enum WindowType {

	LOGIN ("Login", null) ,REGIRSTRATION ("Register", null), CART ("Cart", UserType.CUSTOMER),
	PROFILE ("My Profile", UserType.CUSTOMER), DISCOUNT("Manage Discounts", UserType.ADMIN),
	ADD_PRODUCT ("Add Product to Stock", UserType.ADMIN), PURCHASE_HISTORY("Purchase History", UserType.CUSTOMER),
	PRODUCTS("View and Purchase Products", UserType.CUSTOMER), USERS("Manage Users", UserType.ADMIN),HOMEPAGE("Home page",UserType.CUSTOMER);
	
	final String text;
	final UserType lowestPermitted;
	private WindowType (String text, UserType lowestPermittedType) {
		this.text = text;
		this.lowestPermitted=lowestPermittedType;
	}
	public String getText (){
		return text;
	}
	public UserType getLowestPermitted() {
		return lowestPermitted;
	}
}
