package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * This is a subclass of block that adds a PowerUp to the game root once it has been destroyed
 * @author cgp19, jmt86
 */

public class PowerUpBlock extends Block{
    private Ball myBall;
    private Paddle myPaddle;
    private StatusDisplay myStatusDisplay;
    private ArrayList<PowerUp> myPowerUps;

    /**
     * This is the constructor of the PowerUpBlock which creates a new PowerUp once the block is eliminated and
     * adds it to the game elements list
     * @param whichOne assigns a number to the block for querying
     * @param xPos the desired x position of the block
     * @param yPos the desired y position of the block
     * @param powerUps a list of all the PowerUps
     * @param paddle the paddle object that has to catch the PowerUp
     * @param statusDisplay the status display for the game, used for PowerUp to update score.
     * @param ball the ball object, used to update the speed of the ball if that PowerUp is caught
     * @param gameElements a list of all the observable game elements
     */
    public PowerUpBlock(int whichOne, int xPos, int yPos, ArrayList<PowerUp> powerUps, Paddle paddle,
                        StatusDisplay statusDisplay, Ball ball, ObservableList<Node> gameElements) {
        super(whichOne, xPos, yPos, gameElements);
        myBall = ball;
        myPaddle = paddle;
        myStatusDisplay = statusDisplay;
        myPowerUps = powerUps;
        this.getRectangle().setFill(Color.ORANGE);
        hitsLimit = 1;
    }

    /**
     * Upon collision with this block, a PowerUp will be dropped from the block which contains a random value
     * to determine what the PowerUp should do for the user.
     * @param topHit a variable determining if the ball has collided with the block
     */
    @Override
    public void collision(boolean topHit) {
        this.eliminateBlock();
        PowerUp powerUp = new PowerUp(this.getX(), this.getY(), myPaddle, myStatusDisplay, myBall, myGameElements);
        myGameElements.add(powerUp.getShape());
        myPowerUps.add(powerUp);
    }
}
