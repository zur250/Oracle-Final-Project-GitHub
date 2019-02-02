package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginView extends BorderPane implements ViewInterface{
	String user = "zur";
	String pw = "password";
	String checkUser, checkPw;
	
	private HBox hb;
	private GridPane gridPane;
	private Label lblUserName;
    private TextField txtUserName;
    private Label lblPassword;
    private PasswordField pf;
    private Button btnLogin;
    private Label lblMessage;
    
    public LoginView() {
		super();
		this.setPadding(new Insets(10,50,50,50));
		gridPane = createLoginFormPane();
        addUIControls(gridPane);
        this.setAlignment(this, Pos.CENTER);
        
        this.setTop(hb);
        this.setCenter(gridPane);
	}
    
	/*@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Login Page");
        
        gridPane = createLoginFormPane();
        
        addUIControls(gridPane);
        
      //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);  
        
        //Adding BorderPane to the scene and loading CSS
    	Scene scene = new Scene(bp);
    	//scene.getStylesheets().add(getClass().getClassLoader().getResource("login.css").toExternalForm());
    	primaryStage.setScene(scene);
    	primaryStage.setResizable(false);
    	primaryStage.show();
	}*/

	private GridPane createLoginFormPane() {
		      
        //Adding HBox
        hb = new HBox();
        hb.setPadding(new Insets(20,20,20,30));
        
        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        

        //Reflection for gridPane
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        gridPane.setEffect(r);
        
        return gridPane;
	}
	
	private void addUIControls(GridPane gridPane) {
		   //Implementing Nodes for GridPane
        lblUserName = new Label("Username");
        txtUserName = new TextField();
        lblPassword = new Label("Password");
        PasswordField pf = new PasswordField();
        btnLogin = new Button("Login");
        Label lblMessage = new Label();
        
        //Adding Nodes to GridPane layout
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(txtUserName, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(pf, 1, 1);
        gridPane.add(btnLogin, 2, 1);
        gridPane.add(lblMessage, 1, 2);
        
        //Adding text and DropShadow effect to it
        Text text = new Text("Login");
        text.setFont(ViewEffects.getHeadersFont());
        text.setEffect(ViewEffects.getShadowEffect(5, 5));
        
        //Adding text to HBox
        hb.getChildren().add(text);
                          
        //Add ID's to Nodes
        this.setId("bp");
        gridPane.setId("root");
        btnLogin.setId("btnLogin");
        text.setId("text");
        
        //Action for btnLogin
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		checkUser = txtUserName.getText().toString();
        		checkPw = pf.getText().toString();
        		if(checkUser.isEmpty()) {
                    ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your User Name");
                    return;
                }
                if(checkPw.isEmpty()) {
                	ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your Password");
                    return;
                }        		
        		// TODO send to controller
                ControllerInstance.getInstance().getCont().Connect(checkUser, checkPw);
                
           		txtUserName.setText("");
        		pf.setText("");
        	}
        	});
	}
	
    /*public static void main(String[] args) {
        launch(args);
    }*/
	
	@Override
	public void updateData(DataType data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		txtUserName.clear();
		pf.clear();
	}
	
	public void handle(ActionEvent event) {
		checkUser = txtUserName.getText().toString();
		checkPw = pf.getText().toString();
		if(checkUser.isEmpty()) {
            ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your User Name");
            return;
        }
        if(checkPw.isEmpty()) {
        	ErrorMessage.getInstance().showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your Password");
            return;
        }        		
		// TODO send to controller
        this.getClass();
        
   		txtUserName.setText("");
		pf.setText("");
	}

}
