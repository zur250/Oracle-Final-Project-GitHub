package View;

import Controller.*;

public abstract class BasicView {

	private ControllerInterface cont;
	
	
	
	public BasicView() {
		super();
		cont = new ControllerImpl();
	}
	//void showError(ErrorMessage msg);
	abstract void updateData(DataType data);
	abstract void clearData();
	
	public ControllerInterface getCont() {
		return cont;
	}
	
	
}
