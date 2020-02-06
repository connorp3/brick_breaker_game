package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Create a block to be placed around the screen
 * Block will be hit by ball
 */


public class Block {
    private static final int WIDTH = 25;
    private static final int HEIGHT = 20;

    private Rectangle myShape;

    public Block(int whichOne, String blockType, int xPos, int yPos) {
        myShape = new Rectangle(xPos, yPos, WIDTH, HEIGHT);
        if (blockType.equals("1")) {
            myShape.setFill(Color.LIGHTSTEELBLUE);
        } else if (blockType.equals("2")) {
            myShape.setFill(Color.LIGHTSKYBLUE);
        } else if (blockType.equals("3")) {
            myShape.setFill(Color.LIGHTSLATEGREY);
        }

        myShape.setStroke(Color.BLACK);
        myShape.setId("block_" + whichOne);
    }

    public Rectangle getShape() {
        return myShape;
    }
}
