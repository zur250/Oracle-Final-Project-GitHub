package View;

import Controller.*;

public class ControllerInstance {

	private ControllerInterface cont;
	
	private static ControllerInstance instance;
	
	public static ControllerInstance getInstance() {
		if (instance == null) {
			instance = new ControllerInstance();
		}
		return instance;
	}
	
	private ControllerInstance() {
		super();
		cont = new ControllerImpl();
		//System.out.println("new cont");
	}
	
	public ControllerInterface getCont() {
		return cont;
	}
	
	
}
