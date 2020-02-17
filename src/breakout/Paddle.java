package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * This class outlines a Paddle to be controlled by the player. It takes in key inputs allowing
 * it to move left and right, and it has the ability to shoot the ball. It extends the CollidableObject
 * abstract class allowing it to check for collisions with the Ball and Power-Ups.
 *
 * @author cgp19, jmt86
 */
public class Paddle extends CollidableObject {
    private static final int X_POS = 245;
    private static final int Y_POS = 430;
    private static final double WIDTH = 60;
    private static final double HEIGHT = 5;
    
    private Rectangle myRectangle;

    private boolean moveR;
    private boolean moveL;

    public Paddle() {
        super();
        myRectangle = new Rectangle();
        myRectangle.setX(X_POS);
        myRectangle.setY(Y_POS);
        myRectangle.setWidth(WIDTH);
        myRectangle.setHeight(HEIGHT);
        myRectangle.setFill(Color.RED);
        myRectangle.setStroke(Color.BLACK);
        myRectangle.setId("paddle");
        myRectangle.setVisible(false);

        moveR = false;
        moveL = false;

        super.setShape(myRectangle);
    }

    // returns the shape of the paddle
    public Rectangle getRectangle() {
        return myRectangle;
    }

    // returns the width of the paddle
    public double getWidth() {
        return myRectangle.getWidth();
    }

    // returns the x position of the paddle
    public double getX() {
        return myRectangle.getX();
    }

    // returns the y position of the paddle
    public double getY() {
        return myRectangle.getY();
    }


    public void handleInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            moveR = true;   // Move paddle right
        } else if (code == KeyCode.LEFT) {
            moveL = true;   // Move paddle left
        }
    }

    // stops the paddle once it has reached a side wall
    public void update() {
        // Move the paddle to the right.
        if(moveR && !this.rWallReached()) {
            this.moveRight();
        }

        // Move the paddle to the left.
        if(moveL && !this.lWallReached()) {
            this.moveLeft();
        }

        // Resets the boolean variables for paddle movement
        moveR = false;
        moveL = false;
    }

    // Method to move the paddle to the right
    public void moveRight() { myRectangle.setX(myRectangle.getX() + 10); }

    // Method to move the paddle to the left
    public void moveLeft() { myRectangle.setX(myRectangle.getX() - 10); }

    public boolean rWallReached () {
        return myRectangle.getX() + myRectangle.getWidth() >= GamePlay.SCENE_WIDTH;
    }

    public boolean lWallReached () {
        return myRectangle.getX() <= 0;
    }

    // Change the width of the paddle
    public void changeWidth(double multiplier) {
        myRectangle.setWidth(WIDTH * multiplier);
    }


    @Override
    public void collision(boolean topHit) {
    }
}
