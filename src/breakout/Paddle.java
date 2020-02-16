package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Paddle extends Rectangle {
    private static final int X_POS = 245;
    private static final int Y_POS = 430;
    private static final double WIDTH = 60;
    private static final double HEIGHT = 5;

    private boolean moveR;
    private boolean moveL;

    public Paddle() {

        this.setX(X_POS);
        this.setY(Y_POS);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setFill(Color.RED);
        this.setStroke(Color.BLACK);
        this.setId("paddle");
        moveR = false;
        moveL = false;
    }

    public void handleInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            moveR = true;   // Move paddle right
        } else if (code == KeyCode.LEFT) {
            moveL = true;   // Move paddle left
        }
    }

    public void update() {
        // Move the paddle to the right.
        if(moveR && !this.rWallReached()) {
            this.moveRight();
        }

        // Move the paddle to the left.
        if(moveL && !this.lWallReached()) {
            this.moveLeft();
        }

        // Resets the boolean variables for paddle movement
        moveR = false;
        moveL = false;
    }
    // Method to move the paddle to the right
    public void moveRight() { this.setX(this.getX() + 10); }

    // Method to move the paddle to the left
    public void moveLeft() {
        this.setX(this.getX() - 10);
    }

    //CGP19 I believe I extracted these methods from the update method to make it look a bit cleaner
    public boolean rWallReached () {
        return this.getX() + this.getWidth() >= GamePlay.SCENE_WIDTH;
    }

    public boolean lWallReached () {
        return this.getX() <= 0;
    }

    public void lengthenWidth() {
        this.setWidth(WIDTH * 1.5);
    }

    public void restoreWidth() {
        this.setWidth(WIDTH);
    }

}
