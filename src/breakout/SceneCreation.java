package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class SceneCreation {
    private Paddle myPaddle;
    private Ball myBall;

    public SceneCreation() {
        myPaddle = new Paddle();
        myBall = new Ball();
        Group root = new Group(myPaddle.getShape());
        ObservableList gameElements = root.getChildren();
        gameElements.add(myBall.getShape());
        Scene scene = new Scene(root, 550, 450);
        scene.setOnKeyPressed(keyEventHandler(myPaddle));

        Timeline myAnimation = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> update(SECOND_DELAY, myScene));
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }

    private EventHandler<KeyEvent> keyEventHandler(Paddle paddle) {
        EventHandler<KeyEvent> inputEvent = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    paddle.moveRight();
                } else if (event.getCode() == KeyCode.LEFT) {
                    paddle.moveLeft();
                }
            }
        };
        return inputEvent;
    }

    public void update(double elapsedTime, Scene scene) {
        if(myBall.collideWithTopWall()) {
            myBall.topWallCollision();
        }

        if(myBall.collideWithSideWalls(scene)) {
            myBall.sideWallCollision();
        }

        if(Shape.intersect(myBall.getShape(), myPaddle.getShape()).getBoundsInLocal().getWidth() != -1) {
            myBall.topWallCollision();
        }

        if(myBall.passBottomWall(scene)) {
            myBall.ballReset();
        }

        myBall.moveVertical(elapsedTime);
        myBall.moveLateral(elapsedTime);

    }
}
