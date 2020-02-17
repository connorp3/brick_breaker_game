package breakout;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


import static org.junit.jupiter.api.Assertions.*;

public class GamePlayTest extends DukeApplicationTest {
    private static final int X_BLOCK_GAP = 2;
    private static final int Y_BLOCK_GAP = 2;
    private static final int STARTING_Y_BLOCK_POS = 50;
    private static final int STARTING_X_BLOCK_POS = 16;
    private static final int BLOCK_HEIGHT = 20;
    private static final int BLOCK_WIDTH = 50;
    private final GamePlay mySceneCreation = new GamePlay();

    private Scene myScene;
    private ArrayList<Block> myBlockArrayList;
    private Rectangle myPaddleShape;
    private Circle myBallShape;
    private StatusDisplay myStatusDisplay;
    private Text myLifeCounter;
    private Text myLevelCounter;
    private Text myScoreDisplay;
    private ArrayList<PowerUp> powerUpArrayList;
    private Timeline myAnimation;




    @Override
    public void start (Stage stage) throws FileNotFoundException {
        myScene = mySceneCreation.createScene();


        stage.setScene(myScene);
        stage.show();
        
        myPaddleShape = lookup("#paddle").query();
        myBallShape = lookup("#ball").query();


        myStatusDisplay = mySceneCreation.getMyStatusDisplay();
        myBlockArrayList = mySceneCreation.getBlockArrayList();

        myLifeCounter = lookup("#lifeCounter").query();
        myScoreDisplay = lookup("#scoreDisplay").query();
        myLevelCounter = lookup("#levelCounter").query();

    }

    @Test
    public void testPaddleInitialPosition() {
        assertEquals(245, myPaddleShape.getX());
        assertEquals(430, myPaddleShape.getY());
        assertEquals(60, myPaddleShape.getWidth());
        assertEquals(5, myPaddleShape.getHeight());
    }

    @Test
    public void testBallInitialPosition() {
        assertEquals(275, myBallShape.getCenterX());
        assertEquals(424, myBallShape.getCenterY());
        assertEquals(5, myBallShape.getRadius());
    }

    @Test
    public void testBallMove() throws FileNotFoundException {
        //sleep(1, TimeUnit.SECONDS);
        press(myScene, KeyCode.UP);
        mySceneCreation.update(GamePlay.SECOND_DELAY);
        //sleep(1, TimeUnit.SECONDS);
        // Tests to see if the ball has moved from its starting position
        assertTrue(myBallShape.getCenterX() != GamePlay.SCENE_WIDTH/2);
        assertTrue(myBallShape.getCenterY() < GamePlay.SCENE_HEIGHT-1);
    }

    @Test
    public void testPaddleMoveRight() throws FileNotFoundException {
        myPaddleShape.setX(myScene.getWidth() / 2);
        myPaddleShape.setY(myScene.getHeight() / 2);
        //sleep(1, TimeUnit.SECONDS);
        press(myScene, KeyCode.RIGHT);
        mySceneCreation.update(GamePlay.SECOND_DELAY);
        //sleep(1, TimeUnit.SECONDS);
        assertEquals(myScene.getWidth()/2 + 10, myPaddleShape.getX());
        assertEquals(myScene.getHeight()/2, myPaddleShape.getY());
    }

    @Test
    public void testPaddleMoveLeft() throws FileNotFoundException {
        myPaddleShape.setX(myScene.getWidth() / 2);
        myPaddleShape.setY(myScene.getHeight() / 2);
        //sleep(1, TimeUnit.SECONDS);
        press(myScene, KeyCode.LEFT);
        mySceneCreation.update(GamePlay.SECOND_DELAY);
        //sleep(1, TimeUnit.SECONDS);
        assertEquals(myScene.getWidth()/2 - 10, myPaddleShape.getX());
        assertEquals(myScene.getHeight()/2, myPaddleShape.getY());
    }

    @Test
    public void testBallReset () throws FileNotFoundException {
        myBallShape.setCenterY(1000);
        myBallShape.setCenterX(10);
        myBallShape.setCenterY(myScene.getHeight() + 1);
        //sleep(1, TimeUnit.SECONDS);
        mySceneCreation.update(GamePlay.SECOND_DELAY);
        //sleep(1, TimeUnit.SECONDS);

        assertEquals(myPaddleShape.getX() + myPaddleShape.getWidth()/2, myBallShape.getCenterX());
        assertEquals(myPaddleShape.getY() - myBallShape.getRadius() - 1, myBallShape.getCenterY());
        assertEquals(60, myPaddleShape.getWidth());
    }

    @Test
    public void testShootBall () throws FileNotFoundException {
        // Start ball from the reset position.
        press(myScene, KeyCode.R);
        // sleep(1, TimeUnit.SECONDS);
        press(myScene, KeyCode.UP);
        mySceneCreation.update(GamePlay.SECOND_DELAY);
        // sleep(1, TimeUnit.SECONDS);

        assertTrue(myBallShape.getCenterX() != 275);
        assertTrue(myBallShape.getCenterY() < 424);
    }

    @Test
    public void testCheatKeyR () throws FileNotFoundException {
        // Set ball in arbitrary position that is not the start position
        myBallShape.setCenterX(100);
        myBallShape.setCenterY(100);

        press(myScene, KeyCode.R);
        mySceneCreation.update(GamePlay.SECOND_DELAY);

        assertEquals(myPaddleShape.getX() + myPaddleShape.getWidth()/2, myBallShape.getCenterX());
        assertEquals(myPaddleShape.getY() - myBallShape.getRadius() - 1, myBallShape.getCenterY());
    }

    @Test
    public void testCheatKeyS() throws FileNotFoundException {
        press(myScene, KeyCode.UP);
        mySceneCreation.update(GamePlay.SECOND_DELAY);

        double initXVel = mySceneCreation.getBall().getXVel();
        double initYVel = mySceneCreation.getBall().getYVel();

        press(myScene, KeyCode.S);
        mySceneCreation.update(GamePlay.SECOND_DELAY);

        double finalXVel = mySceneCreation.getBall().getXVel();
        double finalYVel = mySceneCreation.getBall().getYVel();

        assertEquals(0.5*initXVel, finalXVel);
        assertEquals(0.5*initYVel, finalYVel);
    }

    @Test
    public void testCheatKeyF() throws FileNotFoundException {
        press(myScene, KeyCode.UP);
        mySceneCreation.update(GamePlay.SECOND_DELAY);

        double initXVel = mySceneCreation.getBall().getXVel();
        double initYVel = mySceneCreation.getBall().getYVel();

        press(myScene, KeyCode.F);
        mySceneCreation.update(GamePlay.SECOND_DELAY);

        double finalXVel = mySceneCreation.getBall().getXVel();
        double finalYVel = mySceneCreation.getBall().getYVel();

        assertEquals(2*initXVel, finalXVel);
        assertEquals(2*initYVel, finalYVel);
    }
    
/*
@Test
    public void testCheatKeySpace () throws FileNotFoundException {

        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
            try {
                mySceneCreation.update(SECOND_DELAY);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);

        sleep(1, TimeUnit.SECONDS);
        mySceneCreation.update(SECOND_DELAY);

        press(myScene, KeyCode.SPACE);


        // Tests to see if the ball has moved from its starting position
        assertTrue(myBallShape.getShape().getCenterX() == 275 + myBallShape.getXVel());
        assertTrue(myBallShape.getShape().getCenterY() == 424 + myBallShape.getYVel());
    }
*/



    @Test
    public void testFirstInEachRowBlockPosition() {

        Rectangle keyBlock1 = lookup("#block_1").query();
        Rectangle keyBlock11 = lookup("#block_11").query();
        Rectangle keyBlock21 = lookup("#block_21").query();
        Rectangle keyBlock31 = lookup("#block_31").query();
        Rectangle keyBlock41 = lookup("#block_41").query();

        assertEquals(STARTING_X_BLOCK_POS, keyBlock1.getX());
        assertEquals(STARTING_Y_BLOCK_POS, keyBlock1.getY());

        assertEquals(STARTING_X_BLOCK_POS, keyBlock11.getX());
        assertEquals(STARTING_Y_BLOCK_POS + BLOCK_HEIGHT + Y_BLOCK_GAP, keyBlock11.getY());

        assertEquals(STARTING_X_BLOCK_POS, keyBlock21.getX());
        assertEquals(STARTING_Y_BLOCK_POS + 2*(BLOCK_HEIGHT + Y_BLOCK_GAP), keyBlock21.getY());

        assertEquals(STARTING_X_BLOCK_POS, keyBlock31.getX());
        assertEquals(STARTING_Y_BLOCK_POS + 3*(BLOCK_HEIGHT + Y_BLOCK_GAP), keyBlock31.getY());

        assertEquals(STARTING_X_BLOCK_POS, keyBlock41.getX());
        assertEquals(STARTING_Y_BLOCK_POS + 4*(BLOCK_HEIGHT + Y_BLOCK_GAP), keyBlock41.getY());
    }

    @Test

    public void EasyBlockDestroyed() {
        int startNumBlocks = mySceneCreation.getBlockArrayListSize();
        Rectangle keyBlock41 = lookup("#block_41").query();
        setBallOnBlock(keyBlock41);


        sleep(1, TimeUnit.SECONDS);

        javafxRun(() -> {
            try {
                mySceneCreation.update(GamePlay.SECOND_DELAY);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        assertEquals(startNumBlocks - 1, mySceneCreation.getBlockArrayListSize());
        assertEquals(false, keyBlock41.isVisible());
    }

    @Test

    public void MediumBlockDestroyed() {
        press(myScene, KeyCode.DIGIT2);
        int startNumBlocks = mySceneCreation.getBlockArrayListSize();
        Rectangle keyBlock11 = lookup("#block_11").query();
        for(int x = 0; x < 2; x++) {
            setBallOnBlock(keyBlock11);
            javafxRun(() -> {
                try {
                    mySceneCreation.update(GamePlay.SECOND_DELAY);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });

        }

        sleep(1, TimeUnit.SECONDS);

        assertEquals(startNumBlocks - 1, mySceneCreation.getBlockArrayListSize());
        assertEquals(false, keyBlock11.isVisible());
    }

    @Test
    public void MediumBlockNotDestroyed() {
        press(myScene, KeyCode.DIGIT2);
        int startNumBlocks = mySceneCreation.getBlockArrayListSize();
        Rectangle keyBlock11 = lookup("#block_11").query();
        setBallOnBlock(keyBlock11);
        javafxRun(() -> {
            try {
                mySceneCreation.update(GamePlay.SECOND_DELAY);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });



        sleep(1, TimeUnit.SECONDS);

        assertEquals(startNumBlocks, mySceneCreation.getBlockArrayListSize());
        assertEquals(true, keyBlock11.isVisible());
    }

    @Test
    public void HardBlockDestroyed() {
        press(myScene, KeyCode.DIGIT2);
        int startNumBlocks = mySceneCreation.getBlockArrayListSize();
        Rectangle keyBlock1 = lookup("#block_1").query();
        for(int x = 0; x < 3; x++) {
            setBallOnBlock(keyBlock1);
            javafxRun(() -> {
                try {
                    mySceneCreation.update(GamePlay.SECOND_DELAY);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }


        sleep(1, TimeUnit.SECONDS);

        assertEquals(startNumBlocks - 1, mySceneCreation.getBlockArrayListSize());
        assertEquals(false, keyBlock1.isVisible());
    }

    @Test
    public void HardBlockNotDestroyed() {
        press(myScene, KeyCode.DIGIT2);
        int startNumBlocks = mySceneCreation.getBlockArrayListSize();
        Rectangle keyBlock1 = lookup("#block_1").query();
        for(int x = 0; x < 2; x++) {
            setBallOnBlock(keyBlock1);
            javafxRun(() -> {
                try {
                    mySceneCreation.update(GamePlay.SECOND_DELAY);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }


        sleep(1, TimeUnit.SECONDS);

        assertEquals(startNumBlocks, mySceneCreation.getBlockArrayListSize());
        assertEquals(true, keyBlock1.isVisible());
    }

    private void setBallOnBlock(Rectangle block) {
        double blockXPos = block.getX();
        double blockYPos = block.getY();
        myBallShape.setCenterX(blockXPos - myBallShape.getRadius());
        myBallShape.setCenterY(blockYPos + BLOCK_HEIGHT/2);
    }

    @Test
    public void testPowerUpMove() throws FileNotFoundException {
        press(myScene, KeyCode.P);

        PowerUp powerUp = lookup("#powerUp").query();

        double powerUpStartXPos = powerUp.getCenterX();
        double powerUpStartYPos = powerUp.getCenterY();


        mySceneCreation.update(GamePlay.SECOND_DELAY);




        assertEquals(powerUp.getCenterX(), powerUpStartXPos);
        assertEquals(true, powerUp.getCenterY() > powerUpStartYPos);

    }

    @Test
    public void testPowerUpEffect() {
        press(myScene, KeyCode.P);

        PowerUp powerUp = lookup("#powerUp").query();

        powerUp.setCenterX(myPaddleShape.getX());
        powerUp.setCenterY(myPaddleShape.getY());

        javafxRun(() -> {
            try {
                mySceneCreation.update(GamePlay.SECOND_DELAY);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        assertEquals(90, myPaddleShape.getWidth());
    }

    @Test
    public void testStatusDisplayPosition() {
        assertEquals(10, myLifeCounter.getX());
        assertEquals(20, myLifeCounter.getY());
        assertEquals(GamePlay.SCENE_WIDTH - 75, myScoreDisplay.getX());
        assertEquals(20, myScoreDisplay.getY());
        assertEquals(GamePlay.SCENE_WIDTH / 2, myLevelCounter.getX());
        assertEquals(20, myLevelCounter.getY());
    }

    @Test
    public void testLoseALife() throws FileNotFoundException {
        int startLives = myStatusDisplay.getNumLives();
        myBallShape.setCenterY(1000);
        mySceneCreation.update(GamePlay.SECOND_DELAY);


        assertEquals("Lives Remaining: " + (startLives - 1), myLifeCounter.getText());

    }

    @Test
    public void testIncreasePoints() {
        int startScore = myStatusDisplay.getMyScore();
        Rectangle keyBlock41 = lookup("#block_41").query();
        setBallOnBlock(keyBlock41);


        sleep(1, TimeUnit.SECONDS);

        javafxRun(() -> {
            try {
                mySceneCreation.update(GamePlay.SECOND_DELAY);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        assertEquals("Score: " + (startScore + GamePlay.BLOCK_VAL), myScoreDisplay.getText());
    }

    @Test
    public void testChangeLevel() {
        for(int blockNum = 1; blockNum <= 50; blockNum++){
            Rectangle keyBlock = lookup("#block_" + blockNum).query();
            setBallOnBlock(keyBlock);

            javafxRun(() -> {
                try {
                    mySceneCreation.update(GamePlay.SECOND_DELAY);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        javafxRun(() -> {
            try {
                mySceneCreation.update(GamePlay.SECOND_DELAY);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        Rectangle keyBlock1 = lookup("#block_1").query();
        Rectangle keyBlock11 = lookup("#block_11").query();
        Rectangle keyBlock21 = lookup("#block_21").query();
        Rectangle keyBlock31 = lookup("#block_31").query();
        Rectangle keyBlock41 = lookup("#block_41").query();

        assertEquals(STARTING_X_BLOCK_POS, keyBlock1.getX());
        assertEquals(STARTING_Y_BLOCK_POS, keyBlock1.getY());
        assertTrue(mySceneCreation.getBlockArrayList().get(0) instanceof HardBlock);

        assertEquals(STARTING_X_BLOCK_POS, keyBlock11.getX());
        assertEquals(STARTING_Y_BLOCK_POS + BLOCK_HEIGHT + Y_BLOCK_GAP, keyBlock11.getY());
        assertTrue(mySceneCreation.getBlockArrayList().get(10) instanceof MediumBlock);

        assertEquals(STARTING_X_BLOCK_POS, keyBlock21.getX());
        assertEquals(STARTING_Y_BLOCK_POS + 2*(BLOCK_HEIGHT + Y_BLOCK_GAP), keyBlock21.getY());
        assertTrue(mySceneCreation.getBlockArrayList().get(20) instanceof EasyBlock);

        assertEquals(STARTING_X_BLOCK_POS, keyBlock31.getX());
        assertEquals(STARTING_Y_BLOCK_POS + 3*(BLOCK_HEIGHT + Y_BLOCK_GAP), keyBlock31.getY());
        assertTrue(mySceneCreation.getBlockArrayList().get(30) instanceof EasyBlock);

        assertEquals(STARTING_X_BLOCK_POS, keyBlock41.getX());
        assertEquals(STARTING_Y_BLOCK_POS + 4*(BLOCK_HEIGHT + Y_BLOCK_GAP), keyBlock41.getY());
        assertTrue(mySceneCreation.getBlockArrayList().get(40) instanceof EasyBlock);
    }


}


