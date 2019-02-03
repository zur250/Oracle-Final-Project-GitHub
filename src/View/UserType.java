package View;

public enum UserType implements Comparable<UserType> {

	ADMIN ("Admin", 3) ,WORKER ("Worker", 2), CUSTOMER("Customer", 1),ANONMOUS("Anonymous",0);
	
	final String text;
	final int level;
	private UserType (String text, int level) {
		this.text = text;
		this.level=level;
	}
	public String getText (){
		return text;
	}
	public int getLevel() {
		return level;
	}
	
	//ZUR - why isn't the compareTo function working??
	/*	@Override
	public final int compareTo(UserType o) {
		return this.getLevel()-o.getLevel();
	}*/
}
