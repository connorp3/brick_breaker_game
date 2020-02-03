package breakout;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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
        Rectangle paddle = new Rectangle(250, 430, 50, 10);
        paddle.setFill(Color.RED);
        Group root = new Group(paddle);
        Scene scene = new Scene(root, 550, 450);


        primaryStage.setTitle("Breakout");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
