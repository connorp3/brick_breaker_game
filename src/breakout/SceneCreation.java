package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneCreation extends Application {
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0/FRAMES_PER_SECOND;
    private Paddle myPaddle;
    private Ball myBall;
    private Scene myScene;
    private Group myRoot;
    private boolean moveR;
    private boolean moveL;

    private EventHandler<KeyEvent> keyEventHandler(Paddle paddle) {
        EventHandler<KeyEvent> inputEvent = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    moveR = true;
                } else if (event.getCode() == KeyCode.LEFT) {
                    moveL = true;
                }
            }
        };
        return inputEvent;
    }

    public void start(Stage primaryStage) {
        initializeLevel(1);
        Scene scene = new Scene(myRoot, 550, 450);
        myScene = scene;

        scene.setOnKeyPressed(keyEventHandler(myPaddle));
        SceneCreation sceneCreation = new SceneCreation();
        primaryStage.setTitle("Breakout");
        primaryStage.setScene(scene);
        primaryStage.show();


        Timeline myAnimation = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> update(SECOND_DELAY, myScene));
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }

    public void initializeLevel(int level) {
        myPaddle = new Paddle();
        myBall = new Ball();
        myRoot = new Group(myPaddle);
        ObservableList gameElements = myRoot.getChildren();
        gameElements.add(myBall);
    }

    public void update(double elapsedTime, Scene scene) {
        if(moveR) {
            myPaddle.moveRight();
        }

        if(moveL) {
            myPaddle.moveLeft();
        }

        moveR = false;
        moveL = false;

        if(myBall.collideWithTopWall()) {
            myBall.topWallCollision();
        }

        if(myBall.collideWithSideWalls(scene)) {
            myBall.sideWallCollision();
        }

        if(Shape.intersect(myBall, myPaddle).getBoundsInLocal().getWidth() != -1) {
            myBall.topWallCollision();
        }

        if(myBall.passBottomWall(scene)) {
            myBall.ballReset();
        }

        myBall.moveVertical(elapsedTime);
        myBall.moveLateral(elapsedTime);

    }
}
