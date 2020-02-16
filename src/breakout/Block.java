package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


/**
 * Create a block to be placed around the screen
 * Block will be hit by ball
 */


class Block extends CollidableObject {
    protected static final double WIDTH = 50;
    protected static final double HEIGHT = 20;
    protected Rectangle myRectangle;
    protected int hits;
    protected int hitsLimit;
    protected ObservableList<Node> myGameElements;

    //CGP19 I changed myRectangle to extend Rectangle; I don't think we are planning on having blocks that
    //aren't rectangle shaped, so myRectangle makes the functionality of the blocks easier to work with
    public Block(int whichOne, int xPos, int yPos) {
        super();
        myRectangle = new Rectangle();
        
        myRectangle.setWidth(WIDTH);
        myRectangle.setHeight(HEIGHT);
        myRectangle.setX(xPos);
        myRectangle.setY(yPos);
        myRectangle.setStroke(Color.BLACK);
        myRectangle.setId("block_" + whichOne);
        hits = 0;

        super.setShape(myRectangle);

    }

    public Shape getShape() {
        return myRectangle;
    }

    public double getWidth() {
        return myRectangle.getWidth();
    }

    public double getHeight() {
        return myRectangle.getHeight();
    }

    public double getX() {
        return myRectangle.getX();
    }

    public double getY() {
        return myRectangle.getY();
    }

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
            } else if(hits == hitsLimit) {
                myRectangle.setVisible(false);

            }
    }

    public boolean isBlockDestroyed() {
        return (hits == hitsLimit);
    }


    public int getHitLimit() {
        return hitsLimit;
    }

    @Override
    public void collision(boolean topHit) {
        this.eliminateBlock();
    }
}
