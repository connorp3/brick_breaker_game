package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

/** This class represents the powerUps in the game, and once created, it moves down. If it collides with the paddle, it randomly chooses a specific powerup
 * to perform in the game. This class extends collidableobject and implements what it does when it collides with the paddle in collision.
 * @author cgp19, jmt86*/

public class PowerUp extends CollidableObject{
    public static final double Y_VEL = 225;
    public static final double RADIUS = 4;
    public Circle myCircle;
    public Ball myBall;
    public Paddle myPaddle;
    public StatusDisplay myStatusDisplay;
    public ObservableList<Node> myGameElements;

    /**Constructs a PowerUp with a circle object and default position, size and color attributes. Takes several game elements as parameters and keeps them
     * as instance variables so that it can perform necessary functions as a power-up on these game elements in the collision method.
     * Sets the Shape of PowerUp in CollidableObject.
     * */
    public PowerUp(double centerX, double centerY, Paddle paddle, StatusDisplay statusDisplay, Ball ball, ObservableList<Node> gameElements) {
        super();
        myCircle = new Circle();
        myCircle.setCenterX(centerX);
        myCircle.setCenterY(centerY);
        myCircle.setRadius(RADIUS);
        myCircle.setFill(Color.PURPLE);
        myCircle.setId("powerUp");
        myBall = ball;
        myPaddle = paddle;
        myStatusDisplay = statusDisplay;
        myGameElements = gameElements;
        super.setShape(myCircle);


    }

    /**Updates the position of the PowerUp object so that it moves downward in the scene at a set velocity*/
    public void update(double elapsedTime) {
        myCircle.setCenterY(myCircle.getCenterY() + Y_VEL * elapsedTime);
    }

    /**Chooses a random PowerUp to execute. Either increases the width of the Paddle, decreases the speed of the ball,
     * or adds 50 to the score.
     * @param topHit is not used in this method but it is a parameter for other CollidableObject's collision methods
     * so it is passed in to every collidable object*/
    @Override
    public void collision(boolean topHit) {
        myGameElements.remove(myCircle);
        Random rand = new Random();
        int rand_int = rand.nextInt(3);
        if(rand_int == 0) {
            myPaddle.changeWidth(1.5);
        }
        else if(rand_int == 1) {
            myBall.changeSpeed(0.5);
        }
        else if(rand_int == 2) {
            myStatusDisplay.updateScoreDisplay(50);
        }
    }
}
