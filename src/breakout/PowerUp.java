package breakout;

import javafx.scene.shape.Circle;

public class PowerUp extends Circle {
    public static final int Y_VEL = 150;

    public PowerUp(double centerX, double centerY) {
        this.setCenterX(centerX);
        this.setCenterY(centerY);


    }

    public void moveDown(double elapsedTime) {
        this.setCenterY(this.getCenterY() + Y_VEL * elapsedTime);
    }

    public void lengthenPaddle(Paddle paddle) {
        paddle.lengthenWidth();
    }
}
