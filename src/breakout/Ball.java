package breakout;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

public class Ball extends Circle {
    private double XVel;
    private double YVel;
    private Random rand = new Random();
    private static final double START_X_POS = 275;
    private static final double START_Y_POS = 424;
    private final double START_X_VEL = getRandomInRange(-100, 100);
    private static final double START_Y_VEL = -150;
    private static final double RADIUS = 5;
    private boolean stuckToPaddle;

    public Ball() {
        this.setCenterX(START_X_POS);
        this.setCenterY(START_Y_POS);
        this.setRadius(RADIUS);
        this.setFill(Color.GOLD);
        this.setStroke(Color.BLACK);
        XVel = START_X_VEL;
        YVel = START_Y_VEL;
        stuckToPaddle = false;
        this.setId("ball");
    }

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
        this.setCenterX(this.getCenterX() + XVel * elapsedTime);
    }

    // The default vertical movement for the ball
    public void moveVertical(double elapsedTime) {
        this.setCenterY(this.getCenterY() + YVel * elapsedTime);
    }

    // Allows ball to move in perfect sync with the paddle when the ball should be "stuck" to the paddle
    public void moveRight() { this.setCenterX(this.getCenterX() + 10); }

    // Allows ball to move in perfect sync with the paddle when the ball should be "stuck" to the paddle
    public void moveLeft() { this.setCenterX(this.getCenterX() - 10); }

    // Returns whether the ball should be "stuck" to the paddle or not. Used by paddle.
    //public boolean checkStuckToPaddle() { return stuckToPaddle; }

    // Resets the boolean stuckToPaddle to resume normal gameplay after ball has been shot.
    //public void unStick() { this.stuckToPaddle = false; }

    // Returns whether the ball has passed the bottom wall of the window.
    public boolean passBottomWall(Scene scene) {
        return this.getCenterY() >= scene.getHeight();
    }

    // Returns whether the ball has collided with the top wall
    public boolean collideWithTopWall() {
        return this.getCenterY() - this.getRadius() <= 0;
    }

    // Returns whether the ball has collided with the side walls
    public boolean collideWithSideWalls(Scene scene) {
        return this.getCenterX() + this.getRadius() >= scene.getWidth() ||
                this.getCenterX() - this.getRadius() <= 0;
    }

    public void horizontalCollision() {
        XVel = -XVel;
    }

    public void verticalCollision() {
        YVel = -YVel;
    }

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
    }

    // Method to reset the ball and "stick" it to the paddle once it has passed the bottom wall.
    public void ballReset(Paddle myPaddle) {
        this.setCenterX(myPaddle.getX() + myPaddle.getWidth()/2);
        this.setCenterY(myPaddle.getY() - this.getRadius() - 1);
        XVel = 0;
        YVel = 0;
        stuckToPaddle = true;
    }

    // Used to randomize the direction of the ball
    public int getRandomInRange (int min, int max) {
        return min + rand.nextInt(max - min) + 1;
    }

}
