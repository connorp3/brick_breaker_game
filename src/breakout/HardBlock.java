package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class HardBlock extends Block {

    public HardBlock(int whichOne, int xPos, int yPos) {
        super(whichOne, xPos, yPos);
        this.getShape().setFill(Color.LIGHTSLATEGREY);
        hitsLimit = 3;
    }

}
