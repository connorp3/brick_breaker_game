package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;


/**
 * This class creates the ball that bounces around the screen. This class handles its own inputs
 * and has methods to stick it to the paddle.
 *
 * @author cgp19, jmt86
 */
public class Ball extends CollidableObject {
    private double XVel;
    private double YVel;
    private double XStartVel;
    private Random rand = new Random();
    private static final double START_X_POS = 275;
    private static final double START_Y_POS = 424;
    private static final double START_Y_VEL = -150;
    private static final double RADIUS = 5;
    private boolean stuckToPaddle;
    private boolean resetBall;
    private boolean checkShootBall;
    private boolean moveL;
    private boolean moveR;
    private Circle myCircle;

    public Ball() {
        super();
        myCircle = new Circle();
        myCircle.setCenterX(START_X_POS);
        myCircle.setCenterY(START_Y_POS);
        myCircle.setRadius(RADIUS);
        myCircle.setFill(Color.GOLD);
        myCircle.setStroke(Color.BLACK);
        myCircle.setVisible(false);
        XVel = 0;
        YVel = 0;
        stuckToPaddle = false;
        resetBall = true;
        moveL = false;
        moveR = false;
        myCircle.setId("ball");

        super.setShape(myCircle);
    }

    public Circle getCircle() {
        return myCircle;
    }

    // Handle the input to the ball (shoot ball, speed up, slow down, reset)
    public void handleInput(KeyCode code, double xPos, double yPos, double Width) {
        if (code == KeyCode.UP && resetBall) {
            checkShootBall = true;
        } else if (code == KeyCode.R) {
            this.ballReset( xPos, yPos, Width);
        } else if (code == KeyCode.LEFT && resetBall) {
            moveL = true;
        } else if (code == KeyCode.RIGHT && resetBall) {
            moveR = true;
        } else if (code == KeyCode.S) {
            this.changeSpeed(0.5);
        } else if (code == KeyCode.F) {
            this.changeSpeed(2.0);
        }
    }

    public void update(double elapsedTime) {
        // Makes the ball bounce when it reaches the top of the window
        if(this.collideWithTopWall()) {
            this.verticalCollision();
        }

        // Makes the ball bounce when it collides with a wall
        if(this.collideWithSideWalls()) {
            this.horizontalCollision();
        }

        // Shoots ball and returns ball to normal movement.
        if (checkShootBall) {
            this.shootBall();
            resetBall = false;
            checkShootBall = false;
        }

        // Normal ball movement calls
        this.moveVertical(elapsedTime);
        this.moveLateral(elapsedTime);
    }

    // Get the X velocity of the ball
    public double getXVel() { return XVel; }

    // Get the Y velocity of the ball
    public double getYVel() { return YVel; }

    // The default lateral movement for the ball
    public void moveLateral(double elapsedTime) {
        myCircle.setCenterX(myCircle.getCenterX() + XVel * elapsedTime);
    }

    // The default vertical movement for the ball
    public void moveVertical(double elapsedTime) {
        myCircle.setCenterY(myCircle.getCenterY() + YVel * elapsedTime);
    }

    // Allows ball to move in perfect sync with the paddle when the ball should be "stuck" to the paddle
    public void moveRight() { myCircle.setCenterX(myCircle.getCenterX() + 10); }

    // Allows ball to move in perfect sync with the paddle when the ball should be "stuck" to the paddle
    public void moveLeft() { myCircle.setCenterX(myCircle.getCenterX() - 10); }

    // Returns whether the ball has passed the bottom wall of the window.
    public boolean passBottomWall() {
        return myCircle.getCenterY() >= GamePlay.SCENE_HEIGHT;
    }

    // Returns whether the ball has collided with the top wall
    public boolean collideWithTopWall() {
        return myCircle.getCenterY() - myCircle.getRadius() <= 0;
    }

    // Returns whether the ball has collided with the side walls
    public boolean collideWithSideWalls() {
        return myCircle.getCenterX() + myCircle.getRadius() >= GamePlay.SCENE_WIDTH ||
                myCircle.getCenterX() - myCircle.getRadius() <= 0;
    }
    // Changes velocity upon hitting the side of the scene or a block (to be implemented)
    public void horizontalCollision() {
        XVel = -XVel;
    }
    // Changes velocity upon hitting the top or bottom of the scene or a block (to be implemented)
    public void verticalCollision() {
        YVel = -YVel;
    }

    // Change the speed of the ball with a desired multiplier
    public void changeSpeed(double multiplier) {
        if (Math.abs(XVel) >= Math.abs(XStartVel) * 0.5 && Math.abs(XVel) <= Math.abs(XStartVel) * 2.0) {
            XVel = multiplier * XVel;
            YVel = multiplier * YVel;
        }
    }

    // The method called when the ball is "shot" by the user.
    public void shootBall() {
        XVel = getRandomInRange(-100, 100);
        XStartVel = XVel;
        YVel = START_Y_VEL;
        resetBall = false;
    }

    // Method to reset the ball and "stick" it to the paddle once it has passed the bottom wall.
    public void updateResetBall(boolean hitLWall, boolean hitRWall) {
        if(moveR && resetBall && hitRWall) {
            this.moveRight();
        }
        if(moveL && resetBall && hitLWall) {
            this.moveLeft();
        }
        moveR = false;
        moveL = false;
    }

    // This method resets the ball to be stuck to the paddle
    public void ballReset(double xPos, double yPos, double Width) {
        myCircle.setCenterX(xPos + Width / 2);
        myCircle.setCenterY(yPos - myCircle.getRadius() - 1);
        XVel = 0;
        YVel = 0;
        resetBall = true;
    }

    // Used to randomize the direction of the ball
    public int getRandomInRange (int min, int max) {
        return min + rand.nextInt(max - min) + 1;
    }

    // Updates the balls velocities based on the collision location with a collidable object
    @Override
    public void collision(boolean topHit) {
        if(topHit) {
            this.verticalCollision();
        }

        if(!topHit) {
            this.horizontalCollision();
        }
    }
}
