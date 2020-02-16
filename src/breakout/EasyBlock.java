package breakout;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class EasyBlock extends Block {

    public EasyBlock(int whichOne, int xPos, int yPos) {
        super(whichOne, xPos, yPos);
        this.getShape().setFill(Color.LIGHTSTEELBLUE);
        hitsLimit = 1;

    }



}
