package breakout;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class CollidableObject {
    Shape myShape;

    protected void setShape(Shape shape) {
        myShape = shape;
    }

    protected Shape getShape() {
        return myShape;
    }

    void collision(boolean topHit) {

    }

    public static boolean intersectsSide(CollidableObject a, CollidableObject b) {
        if (Shape.intersect(a.getShape(), b.getShape()).getBoundsInLocal().getHeight() != -1 &&
            Shape.intersect(a.getShape(), b.getShape()).getBoundsInLocal().getHeight() > Shape.intersect(a.getShape(), b.getShape()).getBoundsInLocal().getWidth()) {
            return true;
        }
        return false;
    }

    public static boolean intersectsTop(CollidableObject a, CollidableObject b) {
        if(Shape.intersect(a.getShape(), b.getShape()).getBoundsInLocal().getWidth() != -1 &&
            Shape.intersect(a.getShape(), b.getShape()).getBoundsInLocal().getHeight() < Shape.intersect(a.getShape(), b.getShape()).getBoundsInLocal().getWidth()) {
            return true;
        }
        return false;
    }

}
