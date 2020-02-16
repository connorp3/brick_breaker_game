package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class BallPaddleCollision implements CollisionHandler {
    Paddle myPaddle;
    Ball myBall;

    public BallPaddleCollision(Ball ball, Paddle paddle) {
        myPaddle = paddle;
        myBall = ball;
    }
    @Override
    public void collision() {
        if(Shape.intersect(myBall, myPaddle).getBoundsInLocal().getWidth() != -1) {
            whichThirdOfPaddle();
            myBall.verticalCollision();

        }
    }

    private void whichThirdOfPaddle() {
        if (myBall.getCenterX() <= myPaddle.getX() + myPaddle.getWidth()/3 || myBall.getCenterX() >= myPaddle.getX() + 2*myPaddle.getWidth()/3) {
            myBall.horizontalCollision();
        }
    }
}
