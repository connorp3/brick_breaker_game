package breakout;

import javafx.scene.paint.Color;

public class EasyBlock extends Block {

    public EasyBlock(int whichOne, int xPos, int yPos) {
        super(whichOne, xPos, yPos);
        this.getRectangle().setFill(Color.LIGHTSTEELBLUE);
        hitsLimit = 1;

    }



}
