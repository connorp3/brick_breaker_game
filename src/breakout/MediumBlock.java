package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class MediumBlock extends Block {
    private static final int HIT_LIMIT = 2;

    public MediumBlock(int whichOne, int xPos, int yPos) {
        super(whichOne, xPos, yPos);
        this.setFill(Color.LIGHTSKYBLUE);
        hitsLimit = 2;
    }
}
