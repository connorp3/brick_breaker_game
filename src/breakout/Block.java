package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Create a block to be placed around the screen
 * Block will be hit by ball.
 * This class extends CollidableObject so that collisions can be checked easier.
 *
 * @author cgp19, jmt86
 */

class Block extends CollidableObject {
    protected static final double WIDTH = 50;
    protected static final double HEIGHT = 20;
    protected Rectangle myRectangle;
    protected int hits;
    protected int hitsLimit;
    protected ObservableList<Node> myGameElements;

    public Block(int whichOne, int xPos, int yPos, ObservableList<Node> gameElements) {
        super();
        myRectangle = new Rectangle();
        myRectangle.setVisible(false);
        myRectangle.setWidth(WIDTH);
        myRectangle.setHeight(HEIGHT);
        myRectangle.setX(xPos);
        myRectangle.setY(yPos);
        myRectangle.setStroke(Color.BLACK);
        myRectangle.setId("block_" + whichOne);
        hits = 0;
        myGameElements = gameElements;

        super.setShape(myRectangle);

    }

    // Return the shape of the block
    public Rectangle getRectangle() {
        return myRectangle;
    }

    // Return the X position of the block
    public double getX() {
        return myRectangle.getX();
    }

    // Return the y position of the block
    public double getY() {
        return myRectangle.getY();
    }

    // Return the number of hits the block has taken
    public int getHits() {
        return hits;
    }

    //Removes block from game
    public void eliminateBlock() {
        hits++;

            if (hitsLimit - hits == 2) {
                myRectangle.setFill(Color.LIGHTSKYBLUE);
            } else if (hitsLimit - hits == 1) {
                myRectangle.setFill(Color.LIGHTSTEELBLUE);
            } else if(hits >= hitsLimit) {
                myGameElements.remove(myRectangle);
            }
    }

    // Return the hit limit of the block based on what type it is
    public int getHitLimit() {
        return hitsLimit;
    }

    @Override
    public void collision(boolean topHit) {
        this.eliminateBlock();
    }
}
