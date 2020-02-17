package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

public class Ball extends CollidableObject {
    private double XVel;
    private double YVel;
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

    public void handleInput(KeyCode code, double xPos, double yPos, double Width) {
        if (code == KeyCode.UP && resetBall) { // Shoot ball from paddle
            checkShootBall = true;
        } else if (code == KeyCode.R) {
            this.ballReset( xPos, yPos, Width);
        } else if (code == KeyCode.LEFT && resetBall) {
            moveL = true;
        } else if (code == KeyCode.RIGHT && resetBall) {
            moveR = true;
        } else if (code == KeyCode.S) {
            this.halfSpeed();  // Cut the overall speed of the ball in half //CGP19 created helper methods in ball class for these
        } else if (code == KeyCode.F) {
            this.doubleSpeed();
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


        // Calls method to reset the ball once it goes out of the bottom of the screen


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

    //CGP19 The set velocity methods are no longer used. Duvall added to the design specification that using setters and getters
    //was generally not good design, so I turned them into halfSpeed and doubleSpeed methods

    // Set the X velocity for the ball
    public void setXVel(double newXVel) { XVel = newXVel; }

    // Set the Y velocity for the ball
    public void setYVel(double newYVel) { YVel = newYVel; }

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

    //CGP19 Might be smart to combine these into one method that takes the speed increase as a parameter
    public void doubleSpeed() {
        XVel = 2*XVel;
        YVel = 2*YVel;
    }

    public void halfSpeed() {
        XVel = 0.5*XVel;
        YVel = 0.5*YVel;
    }

    // The method called when the ball is "shot" by the user.
    public void shootBall() {
        XVel = getRandomInRange(-100, 100);
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
