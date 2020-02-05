package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Create a block to be placed around the screen
 * Block will be hit by ball
 */

public class Block extends Rectangle {

    private Rectangle myShape;

    public Block (int whichOne, int x, int y, int width, int height) {
        myShape = new Rectangle(x, y, width, height);
        myShape.setFill(Color.AQUAMARINE);
        myShape.setStroke(Color.BLACK);
        myShape.setId("block_" + whichOne);
    }

    public Rectangle getShape() {
        return myShape;
    }
}
