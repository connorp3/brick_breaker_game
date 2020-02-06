package breakout;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class SceneCreation extends Application {

    private static final int X_BLOCK_GAP = 2;
    private static final int Y_BLOCK_GAP = 2;
    private static final int STARTING_Y_BLOCK_POS = 50;
    private static final int STARTING_X_BLOCK_POS = 16;
    private static final int BLOCK_HEIGHT = 20;
    private static final int BLOCK_WIDTH = 50;
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
    private boolean resetBall;
    private boolean stuckToPaddle;
    private ArrayList<Shape> blockArrayList;

    // Method to handle key presses input by the user
    private void handleInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            moveR = true;   // Move paddle right
        } else if (code == KeyCode.LEFT) {
            moveL = true;   // Move paddle left
        } else if (code == KeyCode.UP && resetBall == true) { // Shoot ball from paddle
            checkShootBall = true;
        } else if (code == KeyCode.R) {
            resetBall = true; // Reset ball to stick to paddle
        } else if (code == KeyCode.S) {
            myBall.halfSpeed();  // Cut the overall speed of the ball in half
        } else if (code == KeyCode.F) {
            myBall.doubleSpeed();  // Double the speed of the ball
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

        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> update(SECOND_DELAY));
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }


    public void initializeLevel(int level) throws FileNotFoundException {
        ObservableList gameElements = myRoot.getChildren();
        InputStream levelFile = getClass().getClassLoader().getResourceAsStream("\\level" + level + ".txt");

        Scanner input = new Scanner(levelFile);

        int yPosNextBlock = STARTING_Y_BLOCK_POS;
        int blockCounter = 1;
        blockArrayList = new ArrayList<Shape>();
        while (input.hasNextLine()) {
            String[] blockList = input.nextLine().split(" ");
            int xPosNextBlock = STARTING_X_BLOCK_POS;
            for(String block : blockList) {
                Block newBlock = new EasyBlock(blockCounter, xPosNextBlock, yPosNextBlock);
                blockArrayList.add(newBlock);
                gameElements.add(newBlock);
                xPosNextBlock += BLOCK_WIDTH + X_BLOCK_GAP;
                blockCounter += 1;
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
//        initializeLevel(2);

        myScene = new Scene(myRoot, width, height, background);
        myScene.setOnKeyPressed(e -> handleInput(e.getCode()));
        return myScene;
    }

    /**
     * This is the game loop
     * @param elapsedTime
     */
    public void update(double elapsedTime) {
        checkBallBlockInteraction();
        checkBallPaddleInteraction();
        checkPaddleMovements();
        checkBallMovements(elapsedTime);
    }

    private void checkBallMovements(double elapsedTime) {
        // Makes the ball bounce when it reaches the top of the window
        if(myBall.collideWithTopWall()) {
            myBall.verticalCollision();
        }

        // Makes the ball bounce when it collides with a wall
        if(myBall.collideWithSideWalls(myScene)) {
            myBall.horizontalCollision();
        }


//

        // Calls method to reset the ball once it goes out of the bottom of the screen
        if(myBall.passBottomWall(myScene) || resetBall == true) {
            myBall.ballReset(myPaddle);
            resetBall = true;
        }

        // Shoots ball and returns ball to normal movement.
        if (checkShootBall) {
            myBall.shootBall();
            resetBall = false;
            checkShootBall = false;
        }

        // Normal ball movement calls
        myBall.moveVertical(elapsedTime);
        myBall.moveLateral(elapsedTime);
    }

    private void checkBallPaddleInteraction() {
        // Bounces the ball once it hits the paddle
        if(Shape.intersect(myBall, myPaddle).getBoundsInLocal().getWidth() != -1) {
            myBall.verticalCollision();
        }


        //stuckToPaddle = myBall.checkStuckToPaddle();

        if(moveR && resetBall && !myPaddle.rWallReached(myScene)) {
            myBall.moveRight();
        }

        if(moveL && resetBall && !myPaddle.lWallReached()) {
            myBall.moveLeft();
        }
    }

    private void checkBallBlockInteraction() {
        for (Node gamePiece : myRoot.getChildren()) {
            if (gamePiece instanceof Block) {
                if (Shape.intersect(myBall, (Shape) gamePiece).getBoundsInLocal().getWidth() != -1) {
                    myBall.verticalCollision();
                    ((Block) gamePiece).eliminateBlock(myRoot);
                }
            }
        }
    }

    private void checkPaddleMovements() {

        // Move the paddle to the right. Also moves ball if it is stuck to the paddle.
        if(moveR && !myPaddle.rWallReached(myScene)) {
            myPaddle.moveRight();
        }

        // Move the paddle to the left. Also moves ball if it is stuck to the paddle.
        if(moveL && !myPaddle.lWallReached()) {
            myPaddle.moveLeft();
        }


        // Resets the boolean variables for paddle movement
        moveR = false;
        moveL = false;
    }
}
