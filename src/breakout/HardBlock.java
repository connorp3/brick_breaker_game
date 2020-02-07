package breakout;

import javafx.scene.paint.Color;

public class HardBlock extends Block {

    public HardBlock(int whichOne, int xPos, int yPos) {
        super(whichOne, xPos, yPos);
        this.setFill(Color.LIGHTSLATEGREY);
    }

}
