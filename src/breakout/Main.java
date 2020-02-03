package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Paddle paddle = new Paddle();
        Group root = new Group(paddle.getShape());
        Scene scene = new Scene(root, 550, 450);

        scene.setOnKeyPressed(paddleMove);

        primaryStage.setTitle("Breakout");
        primaryStage.setScene(scene);
        primaryStage.show();


        Timeline myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.play();
    }
}
