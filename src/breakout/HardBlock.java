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
    /**
     * Takes in the same parameters as the Block superclass, but sets the color to LIGHTSLATEGREY since this is the
     * color we chose to represent a hard block, one that takes three hits to be destroyed.
     * @param whichOne tells the game elements list which block this is
     * @param xPos sets the x position of the block on the scene
     * @param yPos sets the y position of the block on the scene
     * @param gameElements The list of all elements of the game
     */
    public HardBlock(int whichOne, int xPos, int yPos, ObservableList<Node> gameElements) {
        super(whichOne, xPos, yPos, gameElements);
        this.getRectangle().setFill(Color.LIGHTSLATEGREY);
        hitsLimit = 3;
    }
}
