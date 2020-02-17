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
    public Circle getCircle() {
        return myCircle;
    }
    public void update(double elapsedTime) {
        myCircle.setCenterY(myCircle.getCenterY() + Y_VEL * elapsedTime);
    }


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
