package View;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UpperPane extends HBox {

	public UpperPane() {
		super();
	    setPadding(new Insets(5, 5, 5, 5));
	    Image zur = new Image("Images/Zur.jpg");
	    Image guy = new Image("Images/Guy.jpg");
	    
	    ImageView guyView = new ImageView(guy);
	    ImageView zurView = new ImageView(zur);
	    guyView.fitHeightProperty().bind(this.heightProperty().subtract(5));
	    guyView.fitWidthProperty().bind(this.widthProperty().divide(6));
	    zurView.fitHeightProperty().bind(this.heightProperty().subtract(5));
	    zurView.fitWidthProperty().bind(this.widthProperty().divide(6));
	    
	    Text text = new Text(20, 20, "Welcome to Zur and Guy's online store");
	    text.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 15));
	    
	    getChildren().addAll(zurView, text, guyView);
	}
}
