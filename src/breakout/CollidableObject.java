package breakout;


import javafx.scene.shape.Shape;

/**
 * This is an abstract class that allows for the checking of collisions between objects that will collide in the game
 * (paddle and ball, paddle and block, paddle and powerup). Using this method, lists of objects that will collide can be easily
 * looped over and compared using the static methods in this class. This has a Shape object instance variable to allow for the use
 * of intersectSide and intersectTop between any two gameElements, no matter the specific Shape object they contain.
 * @author cgp19, jmt86
 */

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

    /**Returns true if objects intersect on the side
     * @param a A collidable object
     * @param b A collidable object
     * @return a boolean of whether two Collidable objects intersect on the side*/
    public static boolean intersectsSide(CollidableObject a, CollidableObject b) {
        if (Shape.intersect(a.getShape(), b.getShape()).getBoundsInLocal().getHeight() != -1 &&
            Shape.intersect(a.getShape(), b.getShape()).getBoundsInLocal().getHeight() > Shape.intersect(a.getShape(), b.getShape()).getBoundsInLocal().getWidth()) {
            return true;
        }
        return false;
    }
    /**Returns true if objects intersect on the top
     * @param a A collidable object
     * @param b A collidable object
     * @return a boolean of whether two Collidable objects intersect on the on the top*/
    public static boolean intersectsTop(CollidableObject a, CollidableObject b) {
        if(Shape.intersect(a.getShape(), b.getShape()).getBoundsInLocal().getWidth() != -1 &&
            Shape.intersect(a.getShape(), b.getShape()).getBoundsInLocal().getHeight() < Shape.intersect(a.getShape(), b.getShape()).getBoundsInLocal().getWidth()) {
            return true;
        }
        return false;
    }

}
