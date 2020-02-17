package breakout;


import javafx.scene.shape.Shape;

/**
 * This is an abstract class that allows for the checking of collisions between objects that will collide in the game
 * (paddle and ball, paddle and block, paddle and powerup). Using this method, lists of objects that will collide can be easily
 * looped over and compared using the static methods in this class.
 * @author cgp19, jmt86*/

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
