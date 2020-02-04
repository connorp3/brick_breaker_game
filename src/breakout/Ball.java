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
    private final double START_X_VEL = getRandomInRange(-50, 50);
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

    public void moveLateral(double elapsedTime) {
        this.setCenterX(this.getCenterX() + XVel * elapsedTime);
    }

    public void moveVertical(double elapsedTime) {
        this.setCenterY(this.getCenterY() + YVel * elapsedTime);
    }

    public void moveRight() { this.setCenterX(this.getCenterX() + 10); }

    public void moveLeft() {
        this.setCenterX(this.getCenterX() - 10);
    }

    public boolean checkStuckToPaddle() { return stuckToPaddle; }

    public void unStick() { this.stuckToPaddle = false; }

    public boolean passBottomWall(Scene scene) {
        return this.getCenterY() >= scene.getHeight();
    }


    public boolean collideWithTopWall() {
        return this.getCenterY() <= 0;
    }

    public boolean collideWithSideWalls(Scene scene) {
        return this.getCenterX() >= scene.getWidth() ||
                this.getCenterX() <= 0;
    }

    public void sideWallCollision() {
        XVel = -XVel;
    }

    public void topWallCollision() {
        YVel = -YVel;
    }

    public void shootBall() {
        XVel = getRandomInRange(-50, 50);
        YVel = START_Y_VEL;
    }

    public void ballReset(Paddle myPaddle) {
        this.setCenterX(myPaddle.getX() + myPaddle.getWidth()/2);
        this.setCenterY(myPaddle.getY() - this.getRadius() - 1);
        XVel = 0;
        YVel = 0;
        stuckToPaddle = true;

    }

    public int getRandomInRange (int min, int max) {
        return min + rand.nextInt(max - min) + 1;
    }

}
