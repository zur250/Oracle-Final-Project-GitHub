package Controller;

import View.ViewInterface;

public interface ControllerInterface {
	void Connect(String userName,String password);
	void register(String userName,String password,int balance,String phoneNumber);
	void disconnect();// we have one user at a time so current connected user can be achived from the controller impl
	void addProductToCart(int productID,int ammount); // need to check that ammount < ammount in stock
	void removeProductFromCart(int productID,int ammount);
	void updateRoleDiscount(int percentage); // we have one user at a time. current user role can be achived from controller impl
	void updateProdctInStore(int productID,String productName,int price,int ammountInStock); // need to check current user permisstions
	void deleteProductFromStore(int productID); // need to check current user permisstions 
	void addProductToStore(String productName,int price,int ammountInStock); // need to check current usser permisstions
	void updateUserBalance(int newBalance); // we have one user at a time. current user can be achived from the controller impl
	void updateUserPassword(String currentPassword,String newPassword); // we have one user at a time. current user can be achived from controller impl
	void registerView(ViewInterface IView);
}
