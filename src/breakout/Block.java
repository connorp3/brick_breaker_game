package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Create a block to be placed around the screen
 * Block will be hit by ball
 */


public class Block extends Rectangle {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 20;

    //CGP19 I changed this to extend Rectangle; I don't think we are planning on having blocks that
    //aren't rectangle shaped, so this makes the functionality of the blocks easier to work with
    public Block(int whichOne, int xPos, int yPos) {
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setX(xPos);
        this.setY(yPos);
        this.setStroke(Color.BLACK);
        this.setId("block_" + whichOne);

    }


    //Removes block from game
    public void eliminateBlock(Group root) {
        root.getChildren().remove(this);
    }
}
