package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**This is a subclass of block that adds a powerup to the game root when it has been destroyed.
 * @author cgp19, jmt86*/

public class PowerUpBlock extends Block{
    private Ball myBall;
    private Paddle myPaddle;
    private StatusDisplay myStatusDisplay;
    private ArrayList<PowerUp> myPowerUps;

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

    @Override
    public void collision(boolean topHit) {
        this.eliminateBlock();
        PowerUp powerUp = new PowerUp(this.getX(), this.getY(), myPaddle, myStatusDisplay, myBall, myGameElements);
        myGameElements.add(powerUp.getShape());
        myPowerUps.add(powerUp);
    }
}
