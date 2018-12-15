package Controller;

import View.ViewInterface;

public class ControllerImpl implements ControllerInterface {//should there be a "change view" function?
	//what about update user's role? from worker to customer in case he was fired for example...

	@Override
	public void Connect(String userName, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void register(String userName, String password, double balance, String phoneNumber) { //
		//that would turn the price into fractions
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {//shouldn't this function know who is connected?
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProductToCart(int productID, int ammount) {//how do I know which cart? is the user stored in the controller
		//or should it be passed as an argument in this function?
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeProductFromCart(int productID, int ammount) {//same question as above
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRoleDiscount(int percentage) {//which role?
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProdctInStore(int productID, String productName, double price, int ammountInStock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProductFromStore(int productID) {//same as above
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProductToStore(String productName, int price, int ammountInStock) {//is the price int?
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserBalance(int newBalance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserPassword(String currentPassword, String newPassword) {//which user?
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerView(ViewInterface IView) {//wrong interface
		// TODO Auto-generated method stub
		
	}

}
