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
    private boolean resetBall;
    private boolean checkShootBall;
    private boolean moveL;
    private boolean moveR;
    private Circle myCircle;

    /**Constructs a ball with a circle object and default position, size and color attributes. Sets booleans for
     * initial behavior of a reset ball. Sets the shape of ball as a collidable object. Sets ball as invisible at first
     * to display a splash screen*/
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
        resetBall = true;
        moveL = false;
        moveR = false;
        myCircle.setId("ball");

        super.setShape(myCircle);
    }

    /**Returns the Circle object contained within the ball object. Used to edit the attributes of the Ball (position).
     * @return Circle representing the ball*/
    public Circle getCircle() {
        return myCircle;
    }

    /**Handles the key input to the ball (shoot ball, speed up, slow down, reset).
     * @param xPos x position of the paddle passed to ballReset method
     * @param yPos y position of the paddle passed to ballReset method
     * @param Width width of the paddle passed to ballReset method*/
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

    /**Updates the ball's position based on movement of ball around the screen. Ball moves constantly both vertically
     * and horizontally and reacts when it hits walls or passes bottom wall.
     * @param elapsedTime the amount of time passing per frame of the game*/
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


     /**Returns the X velocity of the ball. Only used for testing purposes.*/
    public double getXVel() { return XVel; }

    /**Returns the Y velocity of the ball. Only used for testing purposes.*/
    public double getYVel() { return YVel; }

    private void moveLateral(double elapsedTime) {
        myCircle.setCenterX(myCircle.getCenterX() + XVel * elapsedTime);
    }

    private void moveVertical(double elapsedTime) {
        myCircle.setCenterY(myCircle.getCenterY() + YVel * elapsedTime);
    }

    /**Moves the ball to move in sync with the paddle when the ball should be "stuck" to the paddle*/
    public void moveRight() { myCircle.setCenterX(myCircle.getCenterX() + 10); }

    /**Moves the ball to move in sync with the paddle when the ball should be "stuck" to the paddle*/
    public void moveLeft() { myCircle.setCenterX(myCircle.getCenterX() - 10); }

    /**Returns whether the ball has passed the bottom wall of the window.*/
    public boolean passBottomWall() {
        return myCircle.getCenterY() >= GamePlay.SCENE_HEIGHT;
    }


    private boolean collideWithTopWall() {
        return myCircle.getCenterY() - myCircle.getRadius() <= 0;
    }

    // Returns whether the ball has collided with the side walls
    private boolean collideWithSideWalls() {
        return myCircle.getCenterX() + myCircle.getRadius() >= GamePlay.SCENE_WIDTH ||
                myCircle.getCenterX() - myCircle.getRadius() <= 0;
    }
    // Changes velocity upon hitting the side of the scene or a block (to be implemented)
    private void horizontalCollision() {
        XVel = -XVel;
    }
    // Changes velocity upon hitting the top or bottom of the scene or a block (to be implemented)
    private void verticalCollision() {
        YVel = -YVel;
    }

    /**Changes the speed of the ball with a desired multiplier. This is changed either from a cheat key or from a power-up. Limits
     * the change to remain between 0.5x and 2.0x original speed.*/
    public void changeSpeed(double multiplier) {
        if (Math.abs(XVel) >= Math.abs(XStartVel) * 0.5 && Math.abs(XVel) <= Math.abs(XStartVel) * 2.0) {
            XVel = multiplier * XVel;
            YVel = multiplier * YVel;
        }
    }

    // The method called when the ball is "shot" by the user.
    private void shootBall() {
        XVel = getRandomInRange(-100, 100);
        XStartVel = XVel;
        YVel = START_Y_VEL;
        resetBall = false;
    }

    /**Method to reset the ball and "stick" it to the paddle once it has passed the bottom wall.
     * @param hitLWall checks that left wall has not been hit by paddle
     * @param hitRWall checks that right wall has not been hit by paddle*/
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

    /**Resets the ball to be stuck to the center of the paddle. Parameters are attributes of the
     * paddle so the ball can reset itself to the correct position.
     * @param xPos paddle X position
     * @param yPos paddle Y position
     * @param Width paddle width*/
    public void ballReset(double xPos, double yPos, double Width) {
        myCircle.setCenterX(xPos + Width / 2);
        myCircle.setCenterY(yPos - myCircle.getRadius() - 1);
        XVel = 0;
        YVel = 0;
        resetBall = true;
    }

    // Used to randomize the direction of the ball
    private int getRandomInRange (int min, int max) {
        return min + rand.nextInt(max - min) + 1;
    }

    /**Overrides method collision in CollidableObject. Updates the balls velocities.
     * Used when the ball collides with another collidable object (paddle or block)*/
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
