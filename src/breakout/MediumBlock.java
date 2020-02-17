package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class MediumBlock extends Block {
    private static final int HIT_LIMIT = 2;

    public MediumBlock(int whichOne, int xPos, int yPos, ObservableList<Node> gameElements) {
        super(whichOne, xPos, yPos, gameElements);
        this.getRectangle().setFill(Color.LIGHTSKYBLUE);
        hitsLimit = 2;
    }

}
