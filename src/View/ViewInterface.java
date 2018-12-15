package View;

public interface ViewInterface {

	void showError(ErrorMessage msg);
	void updateData(DataType data);
	void clearData();
	boolean addProductToCart(int productID,int ammount);
	boolean deleteProductFromCart(int productID);
	boolean purchaseCart(); // we have one account at a time. so the cart number will be a member in the controller implementation
	boolean roleDiscount(int percentage); // we have one account at a time. so the roleid will be achived from current logged in user.
	boolean updateBalance(int balance); // we have one account at a time. so the account id will be achived from the conotroller implmentation
	boolean updatePassword(String lastPass,String newPass); // we have one account at a time. so the account id will be achived from the controller implementation
	boolean removeProductFromStore(int productID); // need to check user permisstions for this method.
	boolean addNewProductToStore(String productName,int ammountInStock,int price);// need to check user permissions for this method.
	boolean updateProductInfo(int producID,String productName,int ammountInStock,int price);
	void showData(); // Think later
}
