package View;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ViewEffects {
	
	public static Effect getShadowEffect(int xOffset,int yOffset) {

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(xOffset);
        dropShadow.setOffsetY(yOffset);
        return dropShadow;
	}
	
	public static Font getHeadersFont() {
		return Font.font("Arial", FontWeight.BOLD, 24);
	}

}
