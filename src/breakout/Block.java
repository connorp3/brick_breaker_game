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

    /**
     * This constructor takes in the variables below and sets their position so they can be added on the screen.
     * The protected variables above allow the blocks to be uniform in size, but allow the hitsLimit to differ
     * for different blocks so that there can be EasyBlocks, MediumBlocks, HardBlocks, and PowerUp blocks.
     * @param whichOne tells the game elements list which block this is
     * @param xPos sets the x position of the block on the scene
     * @param yPos sets the y position of the block on the scene
     * @param gameElements The list of all elements of the game
     */
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

    /**
     * This method returns the rectangle shape that the block is built on. It is used to add the object to
     * the game elements.
     * @return myRectangle, the shape of the block
     */
    public Rectangle getRectangle() {
        return myRectangle;
    }

    /**
     * This method returns the x position of the block within the scene
     * @return the x position of the block
     */
    public double getX() {
        return myRectangle.getX();
    }

    /**
     * This method returns the y position of the block within the scene
     * @return the y position of the block
     */
    public double getY() {
        return myRectangle.getY();
    }

    /**
     * This method returns the number of hits that the block has already taken
     * @return the number of hits the block has taken
     */
    public int getHits() {
        return hits;
    }

    /**
     * This method removes the block from the game, or changes the color of the block based on hits.
     * It compares the number of hits it has taken to the limit on the number of hits the block
     * can take to determine if the color of the block should change, or if the block should
     * be eliminated completely.
     */
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

    /**
     * This method returns the hit limit for the block (the number of hits it can take before it is destroyed)
     * @return the hitsLimit value for the block
     */
    public int getHitLimit() {
        return hitsLimit;
    }

    /**
     * this method calls the eliminateBlock method with the boolean topHit to determine if a hit should be
     * taken away from the block or if the block should be removed completely.
     * @param topHit a variable determining if the ball has collided with the block
     */
    @Override
    public void collision(boolean topHit) {
        this.eliminateBlock();
    }
}
