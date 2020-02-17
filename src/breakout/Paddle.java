package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class Paddle extends CollidableObject {
    private static final int X_POS = 245;
    private static final int Y_POS = 430;
    private static final double WIDTH = 60;
    private static final double HEIGHT = 5;
    
    private Rectangle myRectangle;

    private boolean moveR;
    private boolean moveL;

    public Paddle() {
        super();
        myRectangle = new Rectangle();
        myRectangle.setX(X_POS);
        myRectangle.setY(Y_POS);
        myRectangle.setWidth(WIDTH);
        myRectangle.setHeight(HEIGHT);
        myRectangle.setFill(Color.RED);
        myRectangle.setStroke(Color.BLACK);
        myRectangle.setId("paddle");
        myRectangle.setVisible(false);

        moveR = false;
        moveL = false;

        super.setShape(myRectangle);
    }

    public Rectangle getRectangle() {
        return myRectangle;
    }

    public double getWidth() {
        return myRectangle.getWidth();
    }

    public double getHeight() {
        return myRectangle.getHeight();
    }

    public double getX() {
        return myRectangle.getX();
    }

    public double getY() {
        return myRectangle.getY();
    }

    public void setX(double x) {
         myRectangle.setX(x);
    }

    public void setY(double y) {
        myRectangle.setX(y);
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
    public void moveRight() { myRectangle.setX(myRectangle.getX() + 10); }

    // Method to move the paddle to the left
    public void moveLeft() {
        myRectangle.setX(myRectangle.getX() - 10);
    }

    //CGP19 I believe I extracted these methods from the update method to make it look a bit cleaner
    public boolean rWallReached () {
        return myRectangle.getX() + myRectangle.getWidth() >= GamePlay.SCENE_WIDTH;
    }

    public boolean lWallReached () {
        return myRectangle.getX() <= 0;
    }

    public void changeWidth(double multiplier) {
        myRectangle.setWidth(WIDTH * multiplier);
    }

    public void restoreWidth() {
        myRectangle.setWidth(WIDTH);
    }

    @Override
    public void collision(boolean topHit) {
    }
}
