package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * This class is an extension of the Block class. It has many of the same
 * parameters, but it is a specific color and it takes three hits to be
 * destroyed.
 *
 * @author cgp19, jmt86
 */

public class HardBlock extends Block {

    public HardBlock(int whichOne, int xPos, int yPos, ObservableList<Node> gameElements) {
        super(whichOne, xPos, yPos, gameElements);
        this.getRectangle().setFill(Color.LIGHTSLATEGREY);
        hitsLimit = 3;
    }

}
