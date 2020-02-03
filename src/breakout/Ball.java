package breakout;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball {
    private Circle ball;
    private double ballSpeed;
    private double XVel;
    private double YVel;
    private static final double START_X_POS = 275;
    private static final double START_Y_POS = 424;
    private static final double START_X_VEL = 50;
    private static final double START_Y_VEL = -100;
    private static final double RADIUS = 5;

    public Ball() {
        ball = new Circle(START_X_POS, START_Y_POS, RADIUS);
        ball.setFill(Color.GOLD);
        ball.setStroke(Color.BLACK);
        ballSpeed = 100;
        XVel = START_X_VEL;
        YVel = START_Y_VEL;
    }

    public Circle getShape() {
        return ball;
    }

    public void moveLateral(double elapsedTime) {
        ball.setCenterX(ball.getCenterX() + XVel * elapsedTime);
    }

    public void moveVertical(double elapsedTime) {
        ball.setCenterY(ball.getCenterY() + YVel * elapsedTime);
    }

    public boolean passBottomWall(Scene scene) {
        return ball.getCenterY() >= scene.getHeight();
    }


    public boolean collideWithTopWall() {
        return ball.getCenterY() <= 0;


    }

    public boolean collideWithSideWalls(Scene scene) {
        return ball.getCenterX() >= scene.getWidth() ||
                ball.getCenterX() <= 0;
    }

    public void sideWallCollision() {
        XVel = -XVel;
    }

    public void topWallCollision() {
        YVel = -YVel;
    }

    public void ballReset() {
        ball.setCenterX(START_X_POS);
        ball.setCenterY(START_Y_POS);
        XVel = START_X_VEL;
        YVel = START_Y_VEL;
    }

}
