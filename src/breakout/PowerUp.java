package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PowerUp extends Circle {
    public static final double Y_VEL = 150;
    public static final double RADIUS = 4;

    public PowerUp(double centerX, double centerY) {
        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setRadius(RADIUS);
        this.setFill(Color.PURPLE);


    }

    public void moveDown(double elapsedTime) {
        this.setCenterY(this.getCenterY() + Y_VEL * elapsedTime);
    }

    public void lengthenPaddle(Paddle paddle) {
        paddle.lengthenWidth();
    }

    public void eliminatePowerUp(Group root) {
            root.getChildren().remove(this);
    }

}
