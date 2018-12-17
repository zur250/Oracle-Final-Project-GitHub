package View;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;

public class ViewEffects {
	
	public static Effect getShadowEffect(int xOffset,int yOffset) {

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(xOffset);
        dropShadow.setOffsetY(yOffset);
        return dropShadow;
	}

}
