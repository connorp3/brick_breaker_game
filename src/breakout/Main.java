package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    /**
     * Start of the program.
     */
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0/FRAMES_PER_SECOND;
    private Paddle myPaddle;
    private Ball myBall;
    private Scene myScene;
    public static void main (String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        myPaddle = new Paddle();
        myBall = new Ball();
        Group root = new Group(myPaddle.getShape());
        ObservableList gameElements = root.getChildren();
        gameElements.add(myBall.getShape());
        Scene scene = new Scene(root, 550, 450);
        myScene = scene;

        scene.setOnKeyPressed(keyEventHandler(myPaddle));

        primaryStage.setTitle("Breakout");
        primaryStage.setScene(scene);
        primaryStage.show();


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
