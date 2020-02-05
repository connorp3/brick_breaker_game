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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SceneCreation extends Application {

    private static final int X_BLOCK_GAP = 2;
    private static final int Y_BLOCK_GAP = 2;
    private static final int STARTING_Y_BLOCK_POS = 50;
    private static final int STARTING_X_BLOCK_POS = 6;
    private static final int BLOCK_HEIGHT = 20;
    private static final int BLOCK_WIDTH = 25;
    private static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0/FRAMES_PER_SECOND;
    public static final int SCENE_WIDTH = 550;
    public static final int SCENE_HEIGHT = 450;
    private Paddle myPaddle;
    private Ball myBall;
    private Group myRoot;
    private Scene myScene;
    private Timeline myAnimation;
    private boolean moveR;
    private boolean moveL;
    private boolean checkShootBall;
    private boolean stuckToPaddle;

    // Method to handle key presses input by the user
    private void handleInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            moveR = true;   // Move paddle right
        } else if (code == KeyCode.LEFT) {
            moveL = true;   // Move paddle left
        } else if (code == KeyCode.UP && myBall.checkStuckToPaddle()) { // Shoot ball from paddle
            checkShootBall = true;
        } else if (code == KeyCode.R) {
            myBall.ballReset(myPaddle);  // Reset ball to stick to paddle
        } else if (code == KeyCode.S) {
            myBall.setXVel(myBall.getXVel() / 2);  // Cut the overall speed of the ball in half
            myBall.setYVel(myBall.getYVel() / 2);
        } else if (code == KeyCode.F) {
            myBall.setXVel(myBall.getXVel() * 2);  // Double the speed of the ball
            myBall.setYVel(myBall.getYVel() * 2);
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
    public void start(Stage primaryStage) throws FileNotFoundException {

        Scene scene = createScene(SCENE_WIDTH, SCENE_HEIGHT, Color.BEIGE);
        primaryStage.setTitle("Breakout");
        primaryStage.setScene(scene);
        primaryStage.show();

        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> update(SECOND_DELAY, myScene));
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }


    public void initializeLevel(int level) throws FileNotFoundException {
        ObservableList gameElements = myRoot.getChildren();
        Scanner input = new Scanner(getClass().getClassLoader().getResourceAsStream("\\level"+level+".txt"));

        int yPosNextBlock = STARTING_Y_BLOCK_POS;
        int blockCounter = 1;
        while (input.hasNextLine()) {
            String[] blockList = input.nextLine().split(" ");
            int xPosNextBlock = STARTING_X_BLOCK_POS;
            for(String block : blockList) {
                Block newBlock = new Block (blockCounter, xPosNextBlock, yPosNextBlock);
                gameElements.add(newBlock.getShape());
                xPosNextBlock += BLOCK_WIDTH + X_BLOCK_GAP;
            }
            yPosNextBlock += BLOCK_HEIGHT + Y_BLOCK_GAP;
        }
    }

    // Creates the scene to be put on the stage
    Scene createScene(int width, int height, Paint background) throws FileNotFoundException {
        myRoot = new Group();

        myPaddle = new Paddle();
        myRoot.getChildren().add(myPaddle);
        myBall = new Ball();
        myRoot.getChildren().add(myBall);
        initializeLevel(1);

        myScene = new Scene(myRoot, width, height, background);
        myScene.setOnKeyPressed(e -> handleInput(e.getCode()));
        return myScene;
    }

    // Game loop
    public void update(double elapsedTime, Scene scene) {
        stuckToPaddle = myBall.checkStuckToPaddle();

        // Move the paddle to the right. Also moves ball if it is stuck to the paddle.
        if(moveR) {
            myPaddle.moveRight();
            if (stuckToPaddle) {
                myBall.moveRight();
            }
        }

        // Move the paddle to the left. Also moves ball if it is stuck to the paddle.
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
