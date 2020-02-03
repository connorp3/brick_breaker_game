package breakout;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Paddle {
    private Rectangle paddle;
    private static final int X_POS = 250;
    private static final int Y_POS = 430;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 10;

    public Paddle() {
        paddle = new Rectangle(X_POS, Y_POS, WIDTH, HEIGHT);
        paddle.setFill(Color.RED);


    }

    public Rectangle getShape() {
        return paddle;
    }

    public void moveRight() {
        paddle.setX(paddle.getX() + 5);
    }

    public void moveLeft() {
        paddle.setX(paddle.getX() - 5);
    }

}
