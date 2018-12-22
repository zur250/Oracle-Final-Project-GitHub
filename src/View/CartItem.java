package View;

public class CartItem {

	private int productID;
	private String productName;
	private int ammount;
	private double price;
	
	public CartItem(int productID, String productName, int ammount, double price) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.ammount = ammount;
		this.price = price;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getAmmount() {
		return ammount;
	}

	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
}
