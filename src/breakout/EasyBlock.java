package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class EasyBlock extends Block {

    public EasyBlock(int whichOne, int xPos, int yPos) {
        super(whichOne, xPos, yPos);
        this.setFill(Color.LIGHTSTEELBLUE);
        hitsLimit = 1;

    }



}
