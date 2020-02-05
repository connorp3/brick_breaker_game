package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Create a block to be placed around the screen
 * Block will be hit by ball
 */


public class Block {
    private static final int WIDTH = 25;
    private static final int HEIGHT = 10;

    private Rectangle myShape;

    public Block (int whichOne, int x, int y) {
        myShape = new Rectangle(x, y, WIDTH, HEIGHT);
        myShape.setFill(Color.AQUAMARINE);
        myShape.setStroke(Color.BLACK);
        myShape.setId("block_" + whichOne);
    }

    public Rectangle getShape() {
        return myShape;
    }
}
