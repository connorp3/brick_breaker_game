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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneCreation extends Application {
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0/FRAMES_PER_SECOND;
    public static final int SCENE_WIDTH = 550;
    public static final int SCENE_HEIGHT = 450;

    private Paddle myPaddle;
    private Ball myBall;
    private Scene myScene;
//    private Group myRoot;
    private Timeline myAnimation;
    private boolean moveR;
    private boolean moveL;

//    private EventHandler<KeyEvent> keyEventHandler(Paddle paddle) {
//        EventHandler<KeyEvent> inputEvent = new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.RIGHT) {
//                    moveR = true;
//                } else if (event.getCode() == KeyCode.LEFT) {
//                    moveL = true;
//                }
//            }
//        };
//        return inputEvent;
//    }
    private void handleInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            moveR = true;
        } else if (code == KeyCode.LEFT) {
            moveL = true;
        }
    }

    @Override
    public void start(Stage primaryStage) {
//        initializeLevel(1);
        Scene scene = createScene(SCENE_WIDTH, SCENE_HEIGHT, Color.BEIGE);
        SceneCreation sceneCreation = new SceneCreation();
        primaryStage.setTitle("Breakout");
        primaryStage.setScene(scene);
        primaryStage.show();

        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> update(SECOND_DELAY, myScene));
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }

    Scene createScene (int width, int height, Paint background) {
        Group root = new Group();

        myPaddle = new Paddle();
        root.getChildren().add(myPaddle);
        myBall = new Ball();
        root.getChildren().add(myBall);

        myScene = new Scene(root, width, height, background);
        myScene.setOnKeyPressed(e -> handleInput(e.getCode()));
        return myScene;
    }

    public void update(double elapsedTime, Scene scene) {

        if(moveR) {
            myPaddle.moveRight();
        }

        if(moveL) {
            myPaddle.moveLeft();
        }

        if (myPaddle.getX() + myPaddle.getWidth() >= scene.getWidth()) {
            myPaddle.moveLeft();
        }

        if (myPaddle.getX() <= 0) {
            myPaddle.moveRight();
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
            myBall.ballReset(myPaddle);
        }

        myBall.moveVertical(elapsedTime);
        myBall.moveLateral(elapsedTime);

    }
}
