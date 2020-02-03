package breakout;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.css.Rect;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    public static final String TITLE = "Breakout";
    public static final String WINNING_MESSAGE = "WINNER!";
    public static final int SIZE = 550;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.WHITE;
    public static final int PADDLE_SPEED = 15;
    public static final int BLOCK_WIDTH = 100;
    public static final int BLOCK_HEIGHT = 50;
    public static final int PADDLE_MIN_SPEED = 30;
    public static final int PADDLE_MAX_SPEED = 100;
    public static final int PADDLE_SPEEDUP_FACTOR = 2;

    private Scene myScene;
    private Timeline myAnimation;
    private Paddle myPaddle;
    private Block myBlock;
    private int myPaddleSpeed;

    @Override
    public void start(Stage stage) {
        myScene = setupScene(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();

        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }

    Scene setupScene (int width, int height, Paint background) {
        Group root = new Group();
        myBlock = new Block (0, 0, 0, BLOCK_WIDTH, BLOCK_HEIGHT);
        root.getChildren().add(myBlock.getShape());

        myPaddle = new Paddle(width / 2 - BLOCK_WIDTH / 2, height - BLOCK_HEIGHT / 2 - 5, BLOCK_WIDTH, BLOCK_HEIGHT / 2);
        root.getChildren().add(myPaddle.getShape());

        myScene = new Scene(root, width, height, background);

        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return myScene;
    }

    void step (double elapsedTime) {
        Rectangle blockShape = myBlock.getShape();
        Rectangle paddleShape = myPaddle.getShape();
        
    }

    private void handleKeyInput (KeyCode code) {
        Rectangle paddleShape = myPaddle.getShape();
        if (code == KeyCode.RIGHT) {
            paddleShape.setX(paddleShape.getX() + PADDLE_SPEED);
        }
        if (code == KeyCode.LEFT) {
            paddleShape.setX(paddleShape.getX() - PADDLE_SPEED);
        }

        if (code == KeyCode.SPACE) {
            if (myAnimation.getStatus() == Animation.Status.RUNNING) {
                myAnimation.pause();
            }
            else {
                myAnimation.play();
            }
        }

    }

    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
