package breakout;

import java.util.concurrent.TimeUnit;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import static org.junit.jupiter.api.Assertions.*;

public class SceneCreationTest extends DukeApplicationTest {
    private final SceneCreation mySceneCreation = new SceneCreation();

    private Scene myScene;
    private Paddle myPaddle;
    private Ball myBall;

    @Override
    public void start (Stage stage) {
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
        assertEquals(50, myPaddle.getWidth());
        assertEquals(10, myPaddle.getHeight());
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
        mySceneCreation.update(SceneCreation.SECOND_DELAY, myScene);
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
        mySceneCreation.update(SceneCreation.SECOND_DELAY, myScene);
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
        mySceneCreation.update(SceneCreation.SECOND_DELAY, myScene);
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
        mySceneCreation.update(SceneCreation.SECOND_DELAY, myScene);
        //sleep(1, TimeUnit.SECONDS);

        assertEquals(myPaddle.getX() + myPaddle.getWidth()/2, myBall.getCenterX());
        assertEquals(myPaddle.getY() - myBall.getRadius() - 1, myBall.getCenterY());
    }
}
