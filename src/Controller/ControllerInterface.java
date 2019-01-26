package Controller;

import View.*;

public interface ControllerInterface {
	void Connect(String userName,String password);
	void register(String userName,String password,double balance,long phoneNumber);
	void disconnect();// we have one user at a time so current connected user can be achived from the controller impl
	void addProductToCart(String productID,int ammount); // need to check that ammount < ammount in stock
	void removeProductFromCart(String productID,int ammount);
	void updateRoleDiscount(String roleID,int percentage); 
	void updateProdctInStore(String productID,String productName,double price,int ammountInStock); // need to check current user permisstions
	void deleteProductFromStore(String productID); // need to check current user permisstions 
	void addProductToStore(String productName,int price,int ammountInStock); // need to check current usser permisstions
	void updateUserBalance(double newBalance); // we have one user at a time. current user can be achived from the controller impl
	void updateUserPassword(String currentPassword,String newPassword); // we have one user at a time. current user can be achived from controller impl
	void registerView(BasicView IView);
	void updateUserRole(String roleName,String userName);
	void deleteUser(String userName);
}
