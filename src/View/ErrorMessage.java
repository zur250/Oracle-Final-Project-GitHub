package View;

public class ErrorMessage {

	private String message, title;
	
	public ErrorMessage(String message, String title) {
		this.message=message;
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public String getTitle() {
		return title;
	}
	
}
