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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**This is the main class that launches the JavaFX application, creates a scene, adds elements to the scene,
 * and updates those elements as necessary based on interactions between those elements and key inputs.*/

public class GamePlay extends Application {
    private Scene myScene;
    private Group myRoot;
    private Paddle myPaddle;
    private Ball myBall;
    private Text myTitle;
    private Text myInstructions;
    private StatusDisplay myStatusDisplay;
    private ArrayList<CollidableObject> myCollidables;
    private ArrayList<PowerUp> myPowerUps;
    private ObservableList<Node> gameElements;
    private ArrayList<Block> blockArrayList;
    private Timeline myAnimation;
    private int currentLevel;


    private static final Text LEVEL_TRANSITION = new Text();
    private static final String INSTRUCTIONS_TEXT =  "Connor Penny & John Taylor \n" +
                                                    "Use Arrow Keys to move Paddle Left and Right \n" +
                                                    "Press Up Arrow to shoot Ball \n" +
                                                    "Press Space to Pause \n" +
                                                    "Clear All Balls in Level to Advance \n" +
                                                    "Catch PowerUps with the Paddle \n" +
                                                    "Good Luck!";
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
    public static final int NUM_LEVELS = 3;


    /**An overridden method from the Application class for JavaFX. This takes a stage as a parameter and creates a Scene
     * and Animation timeline to display in the stage. Uses the createScene method to create the appropriate Breakout scene.*/
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

    /**Creates the Breakout Scene. This adds the respective Shapes of Paddle and Ball as nodes to the root of the scene. It also
     * adds the different Text objects in StatusDisplay to the root of the scene. It creates an ArrayList of CollidableObject objects
     * to be used in updating collisions. It also creates an ArrayList of PowerUp objects to use when updating collisions. It initializes the
     * first level's block configuration, and it displays a Title and Instructions text.
     * @return myScene returns the scene it creates for GamePlayTest to function properly*/
    public Scene createScene() throws FileNotFoundException {
        myRoot = new Group();
        myCollidables = new ArrayList();
        gameElements = myRoot.getChildren();
        myPowerUps = new ArrayList<>();
        myPaddle = new Paddle();
        gameElements.add(myPaddle.getRectangle());
        myCollidables.add(myPaddle);
        myBall = new Ball();
        gameElements.add(myBall.getCircle());
        StatusDisplay statusDisplay = new StatusDisplay(NUM_LIVES);
        myStatusDisplay = statusDisplay;

        gameElements.add(myStatusDisplay.getScoreDisplay());
        gameElements.add(myStatusDisplay.getLevelCounter());
        gameElements.add(myStatusDisplay.getLifeCounter());

        myTitle = new Text();
        myTitle.setText("BREAKOUT");
        myTitle.setX(GamePlay.SCENE_WIDTH / 2 - 200);
        myTitle.setY(GamePlay.SCENE_HEIGHT/2 - 100);
        myTitle.setFill(Color.AQUAMARINE);
        myTitle.setStroke(Color.BLACK);
        myTitle.setStrokeWidth(3);
        myTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 60));
        myInstructions = new Text();
        myInstructions.setText(INSTRUCTIONS_TEXT);
        myInstructions.setX(GamePlay.SCENE_WIDTH / 2 - 200);
        myInstructions.setY(myTitle.getY() + 50);
        myInstructions.setFont(Font.font("verdana", FontWeight.LIGHT, FontPosture.REGULAR, 15));
        gameElements.add(myTitle);
        gameElements.add(myInstructions);

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

    private void initializeLevel(int level) throws FileNotFoundException {
        initializeBlocks(level);
        myBall.ballReset(myPaddle.getX(), myPaddle.getY(), myPaddle.getWidth());
        showTransitionMessage();

    }

    // Reads the file of blocks in the data directory and adds the Rectangle object contained in a Block object to the game elements. Also
    //adds the Block objects themselves to blockArrayList instance variable so that proper methods can be called on the blocks in the scene.
    private void initializeBlocks(int level) {
        InputStream levelFile = getClass().getClassLoader().getResourceAsStream("\\level" + level + ".txt");
        Scanner input = new Scanner(levelFile);

        int yPosNextBlock = STARTING_Y_BLOCK_POS;
        int blockCounter = 1;
        blockArrayList = new ArrayList<Block>();
        while (input.hasNextLine()) {
            String[] blockList = input.nextLine().split(" ");
            int xPosNextBlock = STARTING_X_BLOCK_POS;
            for(String block : blockList) { //jmt86 - Determines which number is read and adds corresponding block
                Block newBlock;
                if (block.equals("1")) {
                    newBlock = new EasyBlock(blockCounter, xPosNextBlock, yPosNextBlock, gameElements);
                    generateBlock(newBlock);
                } else if (block.equals("2")) {
                    newBlock = new MediumBlock(blockCounter, xPosNextBlock, yPosNextBlock, gameElements);
                    generateBlock(newBlock);
                } else if (block.equals("3")) {
                    newBlock = new HardBlock(blockCounter, xPosNextBlock, yPosNextBlock, gameElements);
                    generateBlock(newBlock);
                } else if (block.equals("4")) {
                    newBlock = new PowerUpBlock(blockCounter, xPosNextBlock, yPosNextBlock, myPowerUps, myPaddle, myStatusDisplay, myBall, gameElements);
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
        gameElements.add(newBlock.getRectangle());
        myCollidables.add(newBlock);
    }



    /**Returns the Ball instance variable that exists as a node in myScene. Used exclusively for testing.*/
    public Ball getBall() {
        return myBall;
    }

    /**Returns the instance variable that is a list of game element nodes in myScene. Used for testing.*/
    public ObservableList<Node> getGameElements() {return gameElements;}

    /**Returns the instance variable ArrayList of Block objects nodes in myScene. Used for testing*/
    public ArrayList<Block> getBlockArrayList() {return blockArrayList;}

    /**Returns the StatusDisplay object that is an instance variable of this class. Used for testing*/
    public StatusDisplay getMyStatusDisplay() {
        return myStatusDisplay;
    }

    /**Calls methods of nodes in the scene to handle appropriate key inputs for those Objects. Also handles
     * key input for actions specific to the game scene, such as changing levels, transitioning out of splash screen,
     * creating a powerUp, and pausing*/
    private void handleInput (KeyCode code) throws FileNotFoundException {

        myPaddle.handleInput(code);
        myBall.handleInput(code, myPaddle.getX(), myPaddle.getY(), myPaddle.getWidth());

        if(code == KeyCode.D) {
            gameElements.remove(blockArrayList.get(0).getShape());
            myCollidables.remove(blockArrayList.get(0));
            blockArrayList.remove(0);
        }

        if(code == KeyCode.Q) {
            myStatusDisplay.resetStatusDisplay();
            currentLevel = 1;
            newLevel(1);

        }
        if(code == KeyCode.Z) {
            for(Node x : gameElements) {
                x.setVisible(true);
            }
            myTitle.setY(10000);
            myInstructions.setY(10000);
            gameElements.remove(LEVEL_TRANSITION);
        }
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
            PowerUp powerUp = new PowerUp(myPaddle.getX() + myPaddle.getWidth()/2,
                    myPaddle.getY() - 75, myPaddle, myStatusDisplay, myBall, gameElements);
            myPowerUps.add(powerUp);
            gameElements.add(powerUp.getShape());
        } else if (code == KeyCode.L) { // Add a life to the player's life count
            myStatusDisplay.changeLifeCounter(1);
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
    /**Returns the number of Blocks in the scene. Used for testing*/
    public int getBlockArrayListSize() {
        return blockArrayList.size();
    }

    private void showTransitionMessage() {
        LEVEL_TRANSITION.setText("PRESS Z TO CONTINUE");
        LEVEL_TRANSITION.setX(GamePlay.SCENE_WIDTH / 2);
        LEVEL_TRANSITION.setY(GamePlay.SCENE_HEIGHT/2 + 175);
        LEVEL_TRANSITION.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        gameElements.add(LEVEL_TRANSITION);
    }

    /**
     * This method is responsible for updating the elements of the scene for each frame of the game. It the update method for
     * several instance variables (game elements) in the scene. It also handles some updates that involve multiple game elements
     * itself.
     * @param elapsedTime
     */
    public void update(double elapsedTime) throws FileNotFoundException {
        updateCollisions();
        removeBlocks();
        myPaddle.update();
        myBall.update(elapsedTime);

        for(PowerUp powerUp : myPowerUps) {
            powerUp.update(elapsedTime);
        }

        if(myBall.passBottomWall()) {
            myBall.ballReset(myPaddle.getX(), myPaddle.getY(), myPaddle.getWidth());
            myStatusDisplay.changeLifeCounter(-1);
        }
        myBall.updateResetBall(!myPaddle.lWallReached(), !myPaddle.rWallReached());

        if(myStatusDisplay.getNumLives() <= 0) {
            myStatusDisplay.displayFinalStatus(gameElements, myAnimation, false);
        }
        if(currentLevel > 3){
            myStatusDisplay.displayFinalStatus(gameElements, myAnimation, true);
        }

        if(blockArrayList.isEmpty()) {
            currentLevel++;
            newLevel(currentLevel);
        }
    }

    private void newLevel(int newLevel) throws FileNotFoundException {
        myPaddle.changeWidth(1.0 / newLevel);
        for(Block block : blockArrayList) {
            gameElements.remove(block.getRectangle());
            myCollidables.remove(block);
        }
        myPowerUps.clear();
        blockArrayList.clear();
        if(currentLevel <= NUM_LEVELS) {
            initializeLevel(newLevel);
            myStatusDisplay.updateLevelDisplay(currentLevel);
        }

    }

    private void removeBlocks() {
        ArrayList<Block> toRemove = new ArrayList<>();
        for(Block block : blockArrayList) {
            if (block.getHits() >= block.getHitLimit()) {
                toRemove.add(block);
                myStatusDisplay.updateScoreDisplay(BLOCK_VAL);
                myCollidables.remove(block);

            }
        }
        for(Block block : toRemove) {
            blockArrayList.remove(block);
        }
    }

    private void updateCollisions() {
        boolean topHit = false;

        for(CollidableObject collidableGameElement : myCollidables) {
            if (CollidableObject.intersectsTop(collidableGameElement, myBall)) {
                topHit = true;
                myBall.collision(topHit);
                collidableGameElement.collision(topHit);
            }

            if (CollidableObject.intersectsSide(collidableGameElement, myBall)) {
                myBall.collision(topHit);
                collidableGameElement.collision(topHit);
            }
        }
        ArrayList<CollidableObject> toRemove = new ArrayList<>();
        for(CollidableObject powerUp : myPowerUps) {

            if (CollidableObject.intersectsTop(myPaddle, powerUp)) {
                powerUp.collision(topHit);
                toRemove.add(powerUp);
            }

            if (CollidableObject.intersectsSide(myPaddle, powerUp)) {
                powerUp.collision(topHit);
                myPowerUps.remove(powerUp);
            }
        }
        for(CollidableObject powerUp : toRemove) {
            myPowerUps.remove(powerUp);
        }



    }
}
