package breakout;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Paddle extends Rectangle {
    private static final int X_POS = 245;
    private static final int Y_POS = 430;
    private static final double WIDTH = 60;
    private static final double HEIGHT = 5;

    public Paddle() {

        this.setX(X_POS);
        this.setY(Y_POS);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setFill(Color.RED);
        this.setStroke(Color.BLACK);
        this.setId("paddle");
    }

    // Method to move the paddle to the right
    public void moveRight() { this.setX(this.getX() + 10); }

    // Method to move the paddle to the left
    public void moveLeft() {
        this.setX(this.getX() - 10);
    }

    //CGP19 I believe I extracted these methods from the update method to make it look a bit cleaner
    public boolean rWallReached (Scene scene) {
        return this.getX() + this.getWidth() >= scene.getWidth();
    }

    public boolean lWallReached () {
        return this.getX() <= 0;
    }

    public void lengthenWidth() {
        this.setWidth(WIDTH * 1.5);
    }

}
