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
}
