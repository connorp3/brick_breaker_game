package breakout;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static breakout.SceneCreation.SECOND_DELAY;
import static org.junit.jupiter.api.Assertions.*;

public class SceneCreationTest extends DukeApplicationTest {
    private static final int X_BLOCK_GAP = 2;
    private static final int Y_BLOCK_GAP = 2;
    private static final int STARTING_Y_BLOCK_POS = 50;
    private static final int STARTING_X_BLOCK_POS = 6;
    private static final int BLOCK_HEIGHT = 20;
    private static final int BLOCK_WIDTH = 25;
    private final SceneCreation mySceneCreation = new SceneCreation();

    private Scene myScene;
    private Paddle myPaddle;
    private Ball myBall;
    private Timeline myAnimation;

    @Override
    public void start (Stage stage) throws FileNotFoundException {
        myScene = mySceneCreation.createScene(SceneCreation.SCENE_WIDTH, SceneCreation.SCENE_HEIGHT, Color.BEIGE);
        stage.setScene(myScene);
        stage.show();

        myPaddle = lookup("#paddle").query();
        myBall = lookup("#ball").query();

    }

    @Test
    public void testPaddleInitialPosition() {
        assertEquals(250, myPaddle.getX());
        assertEquals(430, myPaddle.getY());
        assertEquals(60, myPaddle.getWidth());
        assertEquals(5, myPaddle.getHeight());
    }

    @Test
    public void testBallInitialPosition() {
        assertEquals(275, myBall.getCenterX());
        assertEquals(424, myBall.getCenterY());
        assertEquals(5, myBall.getRadius());
    }

    @Test
    public void testBallMove() {
        //sleep(1, TimeUnit.SECONDS);
        mySceneCreation.update(SECOND_DELAY, myScene);
        //sleep(1, TimeUnit.SECONDS);
        // Tests to see if the ball has moved from its starting position
        assertTrue(myBall.getCenterX() != 275);
        assertTrue(myBall.getCenterY() < 424);
    }

    @Test
    public void testPaddleMoveRight() {
        myPaddle.setX(myScene.getWidth() / 2);
        myPaddle.setY(myScene.getHeight() / 2);
        //sleep(1, TimeUnit.SECONDS);
        press(myScene, KeyCode.RIGHT);
        mySceneCreation.update(SECOND_DELAY, myScene);
        //sleep(1, TimeUnit.SECONDS);
        assertEquals(myScene.getWidth()/2 + 10, myPaddle.getX());
        assertEquals(myScene.getHeight()/2, myPaddle.getY());
    }

    @Test
    public void testPaddleMoveLeft() {
        myPaddle.setX(myScene.getWidth() / 2);
        myPaddle.setY(myScene.getHeight() / 2);
        //sleep(1, TimeUnit.SECONDS);
        press(myScene, KeyCode.LEFT);
        mySceneCreation.update(SECOND_DELAY, myScene);
        //sleep(1, TimeUnit.SECONDS);
        assertEquals(myScene.getWidth()/2 - 10, myPaddle.getX());
        assertEquals(myScene.getHeight()/2, myPaddle.getY());
    }

    @Test
    public void testBallReset () {
        myBall.topWallCollision();
        myBall.setCenterX(10);
        myBall.setCenterY(myScene.getHeight() + 1);
        //sleep(1, TimeUnit.SECONDS);
        mySceneCreation.update(SECOND_DELAY, myScene);
        //sleep(1, TimeUnit.SECONDS);

        assertEquals(myPaddle.getX() + myPaddle.getWidth()/2, myBall.getCenterX());
        assertEquals(myPaddle.getY() - myBall.getRadius() - 1, myBall.getCenterY());
    }

    @Test
    public void testShootBall () {
        // Start ball from the reset position.
        myBall.ballReset(myPaddle);
        // sleep(1, TimeUnit.SECONDS);
        press(myScene, KeyCode.UP);
        mySceneCreation.update(SECOND_DELAY, myScene);
        // sleep(1, TimeUnit.SECONDS);

        assertTrue(myBall.getCenterX() != 275);
        assertTrue(myBall.getCenterY() < 424);
    }

    @Test
    public void testCheatKeyR () {
        // Set ball in arbitrary position that is not the start position
        myBall.setCenterX(100);
        myBall.setCenterY(100);

        press(myScene, KeyCode.R);
        mySceneCreation.update(SECOND_DELAY, myScene);

        assertEquals(myPaddle.getX() + myPaddle.getWidth()/2, myBall.getCenterX());
        assertEquals(myPaddle.getY() - myBall.getRadius() - 1, myBall.getCenterY());
    }

    @Test
    public void testCheatKeyS() {
        mySceneCreation.update(SECOND_DELAY, myScene);
        double initXVel = myBall.getXVel();
        double initYVel = myBall.getYVel();
        press(myScene, KeyCode.S);
        mySceneCreation.update(SECOND_DELAY, myScene);

        assertEquals(initXVel / 2, myBall.getXVel());
        assertEquals(initYVel / 2, myBall.getYVel());
    }

    @Test
    public void testCheatKeyF() {
        mySceneCreation.update(SECOND_DELAY, myScene);
        double initXVel = myBall.getXVel();
        double initYVel = myBall.getYVel();
        press(myScene, KeyCode.F);
        mySceneCreation.update(SECOND_DELAY, myScene);

        assertEquals(initXVel * 2, myBall.getXVel());
        assertEquals(initYVel * 2, myBall.getYVel());
    }
/*    @Test
    public void testCheatKeySpace () {

        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> mySceneCreation.update(SECOND_DELAY, myScene));
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);

        sleep(1, TimeUnit.SECONDS);
        mySceneCreation.update(SECOND_DELAY, myScene);

        press(myScene, KeyCode.SPACE);


        // Tests to see if the ball has moved from its starting position
        assertTrue(myBall.getCenterX() == 275 + myBall.getXVel());
        assertTrue(myBall.getCenterY() == 424 + myBall.getYVel());
    }*/


    @Test
    public void testFirstInEachRowBlockPosition() {

        Rectangle keyBlock1 = lookup("#block_1").query();
        Rectangle keyBlock21 = lookup("#block_21").query();
        Rectangle keyBlock41 = lookup("#block_41").query();
        Rectangle keyBlock61 = lookup("#block_61").query();
        Rectangle keyBlock81 = lookup("#block_81").query();

        assertEquals(STARTING_X_BLOCK_POS, keyBlock1.getX());
        assertEquals(STARTING_Y_BLOCK_POS, keyBlock1.getY());

        assertEquals(STARTING_X_BLOCK_POS, keyBlock21.getX());
        assertEquals(STARTING_Y_BLOCK_POS + BLOCK_HEIGHT + Y_BLOCK_GAP, keyBlock21.getY());

        assertEquals(STARTING_X_BLOCK_POS, keyBlock41.getX());
        assertEquals(STARTING_Y_BLOCK_POS + 2*(BLOCK_HEIGHT + Y_BLOCK_GAP), keyBlock41.getY());

        assertEquals(STARTING_X_BLOCK_POS, keyBlock61.getX());
        assertEquals(STARTING_Y_BLOCK_POS + 3*(BLOCK_HEIGHT + Y_BLOCK_GAP), keyBlock61.getY());

        assertEquals(STARTING_X_BLOCK_POS, keyBlock81.getX());
        assertEquals(STARTING_Y_BLOCK_POS + 4*(BLOCK_HEIGHT + Y_BLOCK_GAP), keyBlock81.getY());
    }
}
