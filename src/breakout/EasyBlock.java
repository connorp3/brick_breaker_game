package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class EasyBlock extends Block {

    public EasyBlock(int whichOne, int xPos, int yPos, ObservableList<Node> gameElements) {
        super(whichOne, xPos, yPos, gameElements);
        this.getRectangle().setFill(Color.LIGHTSTEELBLUE);
        hitsLimit = 1;
    }





}
