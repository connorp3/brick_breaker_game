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
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GamePlay extends Application {
    private Scene myScene;
    private Group myRoot;
    private Paddle myPaddle;
    private Ball myBall;
    private StatusDisplay myStatusDisplay;
    private Text lifeCounter;
    private int numLives;
    private Text myScoreDisplay;
    private Text levelCounter;
    private ObservableList<Node> gameElements;
    private Paddle pTwoPaddle;
    private Ball pTwoBall;
    private ArrayList<Block> blockArrayList;
    private Timeline myAnimation;
    private ArrayList<PowerUp> powerUpArrayList;
    private int currentLevel;



    private static final int X_BLOCK_GAP = 2;
    private static final int Y_BLOCK_GAP = 2;
    private static final int STARTING_Y_BLOCK_POS = 50;
    private static final int STARTING_X_BLOCK_POS = 16;
    public static final int SCENE_WIDTH = 550;
    public static final int SCENE_HEIGHT = 450;
    private static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0/FRAMES_PER_SECOND;
    public static final int BLOCK_VAL = 10;
    public static final int NUM_LIVES = 3;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        createScene();
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
            try {
                update(SECOND_DELAY);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        primaryStage.setTitle("Breakout");
        primaryStage.setScene(myScene);
        primaryStage.show();

        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }

    public Scene createScene() throws FileNotFoundException {
        myRoot = new Group();
        gameElements = myRoot.getChildren();
        myPaddle = new Paddle();
        gameElements.add(myPaddle);
        myBall = new Ball();
        gameElements.add(myBall);
        StatusDisplay statusDisplay = new StatusDisplay(NUM_LIVES);
        myStatusDisplay = statusDisplay;

        gameElements.add(myStatusDisplay.getScoreDisplay());
        gameElements.add(myStatusDisplay.getLevelCounter());
        gameElements.add(myStatusDisplay.getLifeCounter());

        currentLevel = 1;
        initializeLevel(currentLevel);


        myScene = new Scene(myRoot, SCENE_WIDTH, SCENE_HEIGHT, Color.BEIGE);
        myScene.setOnKeyPressed(e -> {
            try {
                handleInput(e.getCode());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        return myScene;
    }

    private void handleInput (KeyCode code) throws FileNotFoundException {
// Reset ball to stick to paddle  //CGP19 changed this to boolean value. When I worked with a TA a few days ago, he said it was
            // a good idea to actually perform the actions of the key inputs in update and to use booleans here
          // Double the speed of the ball
        myPaddle.handleInput(code);
        myBall.handleInput(code, myPaddle);
        if(code == KeyCode.DIGIT1) {
            currentLevel = 1;
            newLevel(1);
        }
        if(code == KeyCode.DIGIT2) {
            currentLevel = 2;
            newLevel(2);
        }
        if(code == KeyCode.DIGIT3) {
            currentLevel = 3;
            newLevel(3);
        }
        if (code == KeyCode.P) {
            initializePowerUp(myPaddle.getX() + myPaddle.getWidth()/2, myPaddle.getY() + 75);
        } else if (code == KeyCode.L) { // Add a life to the player's life count
            numLives += 1;
        } else if (code == KeyCode.X) {
            blockArrayList.clear();
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

    public int getBlockArrayListSize() {
        return blockArrayList.size();
    }

    public void twoPlayer() {
        pTwoPaddle = new Paddle();
        pTwoBall = new Ball();
        gameElements.add(pTwoPaddle);
        gameElements.add(pTwoBall);
    }

    private void initializeLevel(int level) throws FileNotFoundException {
        initializeBlocks(level);
        myBall.ballReset(myPaddle);
        powerUpArrayList = new ArrayList<>();
    }

    // Arrange the blocks based on the given configuration file
    private void initializeBlocks(int level) {
        InputStream levelFile = getClass().getClassLoader().getResourceAsStream("\\level" + level + ".txt");
        Scanner input = new Scanner(levelFile);

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
                    generateBlock(newBlock);
                } else if (block.equals("2")) {
                    newBlock = new MediumBlock(blockCounter, xPosNextBlock, yPosNextBlock);
                    generateBlock(newBlock);
                } else if (block.equals("3")) {
                    newBlock = new HardBlock(blockCounter, xPosNextBlock, yPosNextBlock);
                    generateBlock(newBlock);
                }
                xPosNextBlock += Block.WIDTH + X_BLOCK_GAP;
                blockCounter += 1;
            }
            yPosNextBlock += Block.HEIGHT + Y_BLOCK_GAP;
        }
    }

    private void generateBlock(Block newBlock) {
        blockArrayList.add(newBlock);
        gameElements.add(newBlock);
    }


    /**
     * This is the game loop
     * @param elapsedTime
     */
    public void update(double elapsedTime) throws FileNotFoundException {
        //CGP19 Split these methods up even more. Separated out interactions between game nodes.
        checkBallBlockInteraction();
        checkBallPaddleInteraction();
        myPaddle.update();
        myBall.update(elapsedTime);

        if(myBall.passBottomWall()) {
            myBall.ballReset(myPaddle);
            myStatusDisplay.subtractLifeCounter(myBall);
        }
        myBall.updateResetBall(myPaddle);

        if(myStatusDisplay.getNumLives() <= 0) {
            myStatusDisplay.displayLossStatus(gameElements, myAnimation);
        }

        checkPowerUps(elapsedTime);
        checkPowerUpPaddleInteraction();

        if(blockArrayList.isEmpty()) {
            currentLevel++;
            newLevel(currentLevel);
        }
    }

    private void newLevel(int newLevel) throws FileNotFoundException {
        for(Block block : blockArrayList) {
            gameElements.remove(block);
        }
        blockArrayList = new ArrayList<Block>();
        myStatusDisplay.updateLevelDisplay(currentLevel);
        initializeLevel(newLevel);
    }
    private void checkBallPaddleInteraction() {
        // Bounces the ball once it hits the paddle
        BallPaddleCollision ballPaddleCollision = new BallPaddleCollision(myBall, myPaddle);
        ballPaddleCollision.collision();

    }

    //Remove node from myRoot and bounce ball if it is a block and the ball hits the block
    private void checkBallBlockInteraction() {
        // Create new list to keep track of blocks to remove from blockArrayList
        ArrayList<Shape> toRemove = new ArrayList<>();

        // If a block is hit, remove it from the myRoot group and add it to the toRemove list
        for (Block block : blockArrayList) {
            BallBlockCollision ballBlockCollision = new BallBlockCollision(myBall, block, myRoot);
            ballBlockCollision.collision();

            if (block.isBlockDestroyed()) {
                toRemove.add(block);
                generatePowerUpBlockCollision(block);
            }
        }

        // Remove eliminated blocks from blockArrayList
        for (Shape eliminatedBlock : toRemove) {
            blockArrayList.remove(eliminatedBlock);
            myStatusDisplay.updateScoreDisplay(BLOCK_VAL);
        }
    }

    private void generatePowerUpBlockCollision(Block block) {
        Random rand = new Random();
        int rand_int = rand.nextInt(3);
        if (rand_int == 0) {
            initializePowerUp(block.getX() + Block.WIDTH / 2, block.getY() + Block.HEIGHT);
        }
    }

    private void initializePowerUp(double XPos, double YPos) {
        PowerUp powerUp = new PowerUp(XPos, YPos);
        myRoot.getChildren().add(powerUp);
        powerUpArrayList.add(powerUp);
    }


    private void checkPowerUps(double elapsedTime) {
        if(!powerUpArrayList.isEmpty()) {
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

    public StatusDisplay getMyStatusDisplay() {
        return myStatusDisplay;
    }
}