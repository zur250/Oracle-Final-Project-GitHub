package View;

import javafx.scene.layout.BorderPane;

public class BottomPane extends BorderPane implements MainViewInterface {
	
	private User user;
	private MenuPane menu;
	private MainViewInterface data;
	
	public BottomPane() {
		super();
		data = new DataPane();
		menu = new EntranceMenuPane();
		setLeft(menu);
		setCenter((DataPane)data);
		
	}
	
	@Override
	public void showError(ErrorMessage msg) {
		this.data.showError(msg);

	}

	@Override
	public void updateData(DataType data) {
		this.data.updateData(data);
	}

	@Override
	public void clearData() {
		this.data.clearData();

	}

	@Override
	public void changeView(WindowType type) {
		this.data.changeView(type);
	}

	public void setUser(User user) {
		this.user=user;
		switch (user.type) {
		case UserType.ADMIN:
			menu = new AdminMenuPane();
			//how do I show this menu?
			break;
		case UserType.WORKER:
			menu = new CustomerWorkerMenuPane();
			//how do I show this menu?
			break;
		case UserType.CUSTOMER:
			menu = new CustomerWorkerMenuPane();
			//how do I show this menu?
			break;
		default:
			menu = new EntranceMenuPane();
			//how do I show this menu?
			break;
		}
		data.setUser(user);
	}
}
