package breakout;

import javafx.scene.Group;
import javafx.scene.shape.Shape;

public class BallBlockCollision implements CollisionHandler {
    Block myBlock;
    Ball myBall;
    Group myRoot;

    public BallBlockCollision(Ball ball, Block block, Group root) {
        myBlock = block;
        myBall = ball;
        myRoot = root;
    }
    @Override
    public void collision() {
        if (Shape.intersect(myBlock, myBall).getBoundsInLocal().getWidth() != -1 &&
                Shape.intersect(myBlock, myBall).getBoundsInLocal().getHeight() < Shape.intersect(myBlock, myBall).getBoundsInLocal().getWidth()) {
            myBall.verticalCollision();
            myBlock.eliminateBlock(myRoot);
        }

        if (Shape.intersect(myBlock, myBall).getBoundsInLocal().getHeight() != -1 && Shape.intersect(myBlock, myBall).getBoundsInLocal().getHeight() > Shape.intersect(myBlock, myBall).getBoundsInLocal().getWidth()) {
            myBall.horizontalCollision();
            myBlock.eliminateBlock(myRoot);
        }
    }
}
