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

    /**
     * This is the constructor for the Paddle object. It sets the attributes of the paddle, which is implemented
     * as a Rectangle shape, like the x position, y position, width, height, and color. Sets booleans for the
     * initial behavior of the paddle.
     */
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

    /**
     * This method returns the shape of the paddle
     * @return myRectangle, the shape object of the paddle
     */
    public Rectangle getRectangle() {
        return myRectangle;
    }

    /**
     * This method returns the width of the paddle
     * @return the width of the paddle object
     */
    public double getWidth() {
        return myRectangle.getWidth();
    }

    /**
     * This method returns the x position of the paddle
     * @return the x position of the paddle object
     */
    public double getX() {
        return myRectangle.getX();
    }

    /**
     * This method returns the y position of the paddle
     * @return the y position of the paddle object
     */
    public double getY() {
        return myRectangle.getY();
    }


    /**
     * This method takes in a KeyCode user input and adjusts boolean variables deciding position accordingly by
     * setting whether the paddle should be moved left or right depending on the code that is input
     * @param code a KeyCode based on input from the user
     */
    public void handleInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            moveR = true;
        } else if (code == KeyCode.LEFT) {
            moveL = true;
        }
    }

    /**
     * This method updates the position of the paddle based on the boolean variables that are changed in the
     * handleInput method. It also ensures that the paddle does not move past the walls of the scene.
     */
    public void update() {
        if(moveR && !this.rWallReached()) {
            this.moveRight();
        }

        if(moveL && !this.lWallReached()) {
            this.moveLeft();
        }

        moveR = false;
        moveL = false;
    }

    /**
     * This method moves the paddle to the right by updating its x position by 10
     */
    public void moveRight() { myRectangle.setX(myRectangle.getX() + 10); }

    /**
     * This method moves the paddle to the left by updating its x position by -10
     */
    public void moveLeft() { myRectangle.setX(myRectangle.getX() - 10); }

    /**
     * This method determines if the paddle has reached the right wall of the scene
     * @return a boolean variable based on whether the paddle is touching the right side of the screen
     */
    public boolean rWallReached () {
        return myRectangle.getX() + myRectangle.getWidth() >= GamePlay.SCENE_WIDTH;
    }

    /**
     * This method determines if the paddle has reached the left wall of the scene
     * @return a boolean variable based on whether the paddle is touching the left side of the screen
     */
    public boolean lWallReached () {
        return myRectangle.getX() <= 0;
    }

    /**
     * This method updates the width of the paddle.
     * Used to make the paddle smaller as the player progresses through the levels
     * @param multiplier a double value that determines how much the paddle should be shrunk by
     */
    public void changeWidth(double multiplier) {
        myRectangle.setWidth(WIDTH * multiplier);
    }

    /**
     * Overrides the collision method in the CollidableObject superclass to determine if
     * the paddle is colliding with any other game objects.
     * @param topHit a variable that determines if the paddle has collided with another object
     */
    @Override
    public void collision(boolean topHit) {
    }
}
