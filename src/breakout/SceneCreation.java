package breakout;

import javafx.animation.Animation;
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
    private Timeline myAnimation;
    private boolean moveR;
    private boolean moveL;
    private boolean checkShootBall;
    private boolean stuckToPaddle;

    // Method to handle key presses input by the user
    private void handleInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            moveR = true;
        } else if (code == KeyCode.LEFT) {
            moveL = true;
        } else if (code == KeyCode.UP) {
            checkShootBall = true;
        } else if (code == KeyCode.R) {
            myBall.ballReset(myPaddle);
        }
        // pause/restart animation
        if (code == KeyCode.SPACE) {
            if (myAnimation.getStatus() == Animation.Status.RUNNING) {
                myAnimation.pause();
            }
            else {
                myAnimation.play();
            }
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

    // Creates the scene to be put on the stage
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

    // Game loop
    public void update(double elapsedTime, Scene scene) {
        stuckToPaddle = myBall.checkStuckToPaddle();

        // Move the paddle to the right if necessary. Also moves ball if it is stuck to the paddle.
        if(moveR) {
            myPaddle.moveRight();
            if (stuckToPaddle) {
                myBall.moveRight();
            }
        }

        // Move the paddle to the left if necessary. Also moves ball if it is stuck to the paddle.
        if(moveL) {
            myPaddle.moveLeft();
            if (stuckToPaddle) {
                myBall.moveLeft();
            }
        }

        // Stops paddle at right side of the window
        if (myPaddle.getX() + myPaddle.getWidth() >= scene.getWidth()) {
            myPaddle.moveLeft();
            if (stuckToPaddle) {
                myBall.moveLeft();
            }
        }

        // Stops paddle at the left side of the window
        if (myPaddle.getX() <= 0) {
            myPaddle.moveRight();
            if (stuckToPaddle) {
                myBall.moveRight();
            }
        }

        // Resets the boolean variables for paddle movement
        moveR = false;
        moveL = false;

        // Makes the ball bounce when it reaches the top of the window
        if(myBall.collideWithTopWall()) {
            myBall.topWallCollision();
        }

        // Makes the ball bounce when it collides with a wall
        if(myBall.collideWithSideWalls(scene)) {
            myBall.sideWallCollision();
        }

        // Bounces the ball once it hits the paddle
        if(Shape.intersect(myBall, myPaddle).getBoundsInLocal().getWidth() != -1) {
            myBall.topWallCollision();
        }

        // Calls method to reset the ball once it goes out of the bottom of the screen
        if(myBall.passBottomWall(scene)) {
            myBall.ballReset(myPaddle);
        }

        // Shoots ball and returns ball to normal movement.
        if (checkShootBall) {
            myBall.shootBall();
            myBall.unStick();
            checkShootBall = false;
        }

        // Normal ball movement calls
        myBall.moveVertical(elapsedTime);
        myBall.moveLateral(elapsedTime);
    }
}
