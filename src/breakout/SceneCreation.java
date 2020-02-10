package breakout;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static breakout.Block.HEIGHT;
import static breakout.Block.WIDTH;

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
    private Text lifeCounter;
    private Text gameOverMessage;
    private Text myScoreDisplay;
    private boolean moveR;
    private boolean moveL;
    private boolean checkShootBall;
    private boolean resetBall;
    private boolean stuckToPaddle;
    private ArrayList<Block> blockArrayList;
    private ArrayList<PowerUp> powerUpArrayList;
    private boolean powerUpExists;
    private int numLives;
    private int myScore;

    public int getBlockArrayListSize() {
        return blockArrayList.size();
    }

    public void initializeText() {
        numLives = 3;

        lifeCounter.setX(10);
        lifeCounter.setY(20);
        lifeCounter.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        lifeCounter.setText("Lives Remaining: " + numLives);

        myScore = 0;
        myScoreDisplay.setX(SCENE_WIDTH - 100);
        myScoreDisplay.setY(20);
        myScoreDisplay.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        myScoreDisplay.setText("Score: " + myScore);
    }

    // Method to handle key presses input by the user
    private void handleInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            moveR = true;   // Move paddle right
        } else if (code == KeyCode.LEFT) {
            moveL = true;   // Move paddle left
        } else if (code == KeyCode.UP && resetBall) { // Shoot ball from paddle
            checkShootBall = true;
        } else if (code == KeyCode.R) {
            resetBall = true; // Reset ball to stick to paddle  //CGP19 changed this to boolean value. When I worked with a TA a few days ago, he said it was
                                                                // a good idea to actually perform the actions of the key inputs in update and to use booleans here
        } else if (code == KeyCode.S) {
            myBall.halfSpeed();  // Cut the overall speed of the ball in half //CGP19 created helper methods in ball class for these
        } else if (code == KeyCode.F) {
            myBall.doubleSpeed();  // Double the speed of the ball
        } else if (code == KeyCode.P) {
            initializePowerUp(myPaddle.getX() + myPaddle.getWidth()/2, myPaddle.getY() + 75);
        } else if (code == KeyCode.L) {
            numLives += 1;
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

    // Initialize the elements of the game
    public void initializeLevel(int level) throws FileNotFoundException {
        ObservableList gameElements = myRoot.getChildren();
        InputStream levelFile = getClass().getClassLoader().getResourceAsStream("\\level" + level + ".txt");

        Scanner input = new Scanner(levelFile);

        initializeBlocks(gameElements, input);
        initializeBall();
        initializeText();
        powerUpArrayList = new ArrayList<>();
    }

    // Method to reset ball where it is stuck to paddle
    private void initializeBall() {
        myBall.ballReset(myPaddle);
        resetBall = true;
    }

    // Arrange the blocks based on the given configuration file
    private void initializeBlocks(ObservableList gameElements, Scanner input) {
        int yPosNextBlock = STARTING_Y_BLOCK_POS;
        int blockCounter = 1;
        blockArrayList = new ArrayList<Block>();  //I changed the way the ball block interaction method worked, so this list isn't actually used when removing blocks
        while (input.hasNextLine()) {
            String[] blockList = input.nextLine().split(" ");
            int xPosNextBlock = STARTING_X_BLOCK_POS;
            for(String block : blockList) { //jmt86 - Determines which number is read and adds corresponding block
                Block newBlock;
                if (block.equals("1")) {
                    newBlock = new EasyBlock(blockCounter, xPosNextBlock, yPosNextBlock);
                } else if (block.equals("2")) {
                    newBlock = new MediumBlock(blockCounter, xPosNextBlock, yPosNextBlock);
                } else if (block.equals("3")) {
                    newBlock = new HardBlock(blockCounter, xPosNextBlock, yPosNextBlock);
                } else {
                    newBlock = new EasyBlock(blockCounter, xPosNextBlock, yPosNextBlock);
                }
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
        lifeCounter = new Text();
        myRoot.getChildren().add(lifeCounter);
        myScoreDisplay = new Text();
        myRoot.getChildren().add(myScoreDisplay);

        //        initializeLevel(1);
        initializeLevel(2);

        myScene = new Scene(myRoot, width, height, background);
        myScene.setOnKeyPressed(e -> handleInput(e.getCode()));
        return myScene;
    }

    /**
     * This is the game loop
     * @param elapsedTime
     */
    public void update(double elapsedTime) {
        //CGP19 Split these methods up even more. Separated out interactions between game nodes.
        checkBallBlockInteraction();
        checkBallPaddleInteraction();
        checkPaddleMovements();
        checkBallMovements(elapsedTime);
        checkPowerUps(elapsedTime);
        checkPowerUpPaddleInteraction();
        displayStatus();
    }

    // Updates the number of lives the user has
    private void displayStatus() {

        if (myBall.passBottomWall(myScene)) {
            removeALife();
        }
        lifeCounter.setText("Lives Remaining: " + numLives);

        if (blockArrayList.isEmpty() || numLives <= 0) {
            displayFinalStatus();
        }

    }

    // Displays the final status of the game
    private void displayFinalStatus() {
        gameOverMessage = new Text();
        myRoot.getChildren().add(gameOverMessage);
        myRoot.getChildren().remove(lifeCounter);

        gameOverMessage.setX(SCENE_WIDTH / 4 - 10);
        gameOverMessage.setY(SCENE_HEIGHT / 2);
        gameOverMessage.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 60));
        gameOverMessage.setStrokeWidth(3);
        gameOverMessage.setStroke(Color.BLACK);


        if (blockArrayList.isEmpty()) {
            gameOverMessage.setText("You Win!");
            gameOverMessage.setFill(Color.GREEN);
        } else {
            gameOverMessage.setText("You Lose.");
            gameOverMessage.setFill(Color.RED);
        }
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

        // Calls method to reset the ball once it goes out of the bottom of the screen
        if(myBall.passBottomWall(myScene) || resetBall) {
            initializeBall();
            myPaddle.restoreWidth();
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

    private void removeALife() {
        numLives = numLives-1;
    }

    private void checkBallPaddleInteraction() {
        // Bounces the ball once it hits the paddle
        if(Shape.intersect(myBall, myPaddle).getBoundsInLocal().getWidth() != -1) {
            whichThirdOfPaddle();
            myBall.verticalCollision();

        }

        //CGP19 Removed checkStuckToPaddle as a method for ball. resetBall boolean able to handle everything that stuckToPaddle
        //stuckToPaddle = myBall.checkStuckToPaddle();

        //Moves ball with paddle if it is stuck and check the right bounds of the scene
        if(moveR && resetBall && !myPaddle.rWallReached(myScene)) {
            myBall.moveRight();
        }

        //Moves ball with paddle if it is stuck and check the left bounds of the scene
        if(moveL && resetBall && !myPaddle.lWallReached()) {
            myBall.moveLeft();
        }
    }

    private void whichThirdOfPaddle() {
        if (myBall.getCenterX() <= myPaddle.getX() + myPaddle.getWidth()/3 || myBall.getCenterX() >= myPaddle.getX() + 2*myPaddle.getWidth()/3) {
            myBall.horizontalCollision();
        }
    }

    //Remove node from myRoot and bounce ball if it is a block and the ball hits the block
    private void checkBallBlockInteraction() {
        // Create new list to keep track of blocks to remove from blockArrayList
        ArrayList<Shape> toRemove = new ArrayList<>();

        // If a block is hit, remove it from the myRoot group and add it to the toRemove list
        for (Block block : blockArrayList) {
            if (Shape.intersect(block, myBall).getBoundsInLocal().getWidth() != -1 &&
                    Shape.intersect(block, myBall).getBoundsInLocal().getHeight() < Shape.intersect(block, myBall).getBoundsInLocal().getWidth()) {
                myBall.verticalCollision();
                block.eliminateBlock(myRoot);
                if (block.isBlockDestroyed()) {
                    toRemove.add(block);
                    generatePowerUpBlockCollision(block);
                }
            }
        }
        for (Block block : blockArrayList) {
            if (Shape.intersect(block, myBall).getBoundsInLocal().getHeight() != -1 && Shape.intersect(block, myBall).getBoundsInLocal().getHeight() > Shape.intersect(block, myBall).getBoundsInLocal().getWidth()) {
                myBall.horizontalCollision();
                block.eliminateBlock(myRoot);
                if (block.isBlockDestroyed()) {
                    generatePowerUpBlockCollision(block);
                    toRemove.add(block);
                }
            }
        }

            // Remove eliminated blocks from blockArrayList
        for (Shape eliminatedBlock : toRemove) {
            blockArrayList.remove(eliminatedBlock);
            updateScore();
        }


    }

    private void updateScore() {
        myScore += 10;
        myScoreDisplay.setText("Score: " + myScore);
    }

    private void generatePowerUpBlockCollision(Block block) {
        Random rand = new Random();
        int rand_int = rand.nextInt(3);
        if (rand_int == 0) {
            initializePowerUp(block.getX() + WIDTH / 2, block.getY() + HEIGHT);
        }
    }

    private void initializePowerUp(double XPos, double YPos) {
        PowerUp powerUp = new PowerUp(XPos, YPos);
        myRoot.getChildren().add(powerUp);
        powerUpArrayList.add(powerUp);
    }

    private void checkPaddleMovements() {
        // Move the paddle to the right.
        if(moveR && !myPaddle.rWallReached(myScene)) {
            myPaddle.moveRight();
        }

        // Move the paddle to the left.
        if(moveL && !myPaddle.lWallReached()) {
            myPaddle.moveLeft();
        }

        // Resets the boolean variables for paddle movement
        moveR = false;
        moveL = false;
    }

    private void checkPowerUps(double elapsedTime) {
        if(powerUpArrayList.size() > 0) {
            for(PowerUp powerUp : powerUpArrayList) {
                powerUp.moveDown(elapsedTime);
            }
        }
    }

    private void checkPowerUpPaddleInteraction() {
        ArrayList<Shape> toRemove = new ArrayList<>();

        for(PowerUp powerUp : powerUpArrayList) {
            if(Shape.intersect(powerUp, myPaddle).getBoundsInLocal().getHeight() != -1) {
                powerUp.lengthenPaddle(myPaddle);
                powerUp.eliminatePowerUp(myRoot);
                toRemove.add(powerUp);
            }
        }

        for (Shape powerUp : toRemove) {
            powerUpArrayList.remove(powerUp);
        }

    }
}
