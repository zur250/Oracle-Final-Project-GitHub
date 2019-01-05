package View;

public enum ProductType {

	DAIRY ("Dairy"), GLUTEN_FREE ("Gluten free"), BREAD ("Bread"), MEAT ("Meat"),
	VEGETABLES ("Vegetables"), FRUITS ("Fruits"), RELIGIOUS_ARTIFACTS ("Religious artifacts");
	
	final String description;
	
	private ProductType (String description) {
		this.description=description;
	}

	public String getDescription() {
		return description;
	}
}
