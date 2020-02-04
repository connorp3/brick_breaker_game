package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Paddle extends Rectangle {
    private static final int X_POS = 250;
    private static final int Y_POS = 430;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 10;

    public Paddle() {
        this.setX(X_POS);
        this.setY(Y_POS);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setFill(Color.RED);
        this.setId("paddle");


    }


    public void moveRight() {
        this.setX(this.getX() + 5);
    }

    public void moveLeft() {
        this.setX(this.getX() - 5);
    }

}
