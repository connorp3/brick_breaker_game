package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle {
    private Rectangle myShape;

    public Paddle (int x, int y, int width, int height) {
        myShape = new Rectangle(x, y, width, height);
        myShape.setFill(Color.DARKSLATEGRAY);
        myShape.setId("paddle");
    }

    public Rectangle getShape() {
        return myShape;
    }
}
