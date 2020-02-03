package breakout;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
    private double ballSpeed;
    private double XVel;
    private double YVel;
    private static final double START_X_POS = 275;
    private static final double START_Y_POS = 424;
    private static final double START_X_VEL = 50;
    private static final double START_Y_VEL = -100;
    private static final double RADIUS = 5;

    public Ball() {
        this.setCenterX(START_X_POS);
        this.setCenterY(START_Y_POS);
        this.setRadius(RADIUS);
        this.setFill(Color.GOLD);
        this.setStroke(Color.BLACK);
        ballSpeed = 100;
        XVel = START_X_VEL;
        YVel = START_Y_VEL;
    }

    public void moveLateral(double elapsedTime) {
        this.setCenterX(this.getCenterX() + XVel * elapsedTime);
    }

    public void moveVertical(double elapsedTime) {
        this.setCenterY(this.getCenterY() + YVel * elapsedTime);
    }

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

    public void ballReset() {
        this.setCenterX(START_X_POS);
        this.setCenterY(START_Y_POS);
        XVel = START_X_VEL;
        YVel = START_Y_VEL;
    }

}
