package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class PowerUpBlock extends Block {

    public PowerUpBlock(int whichOne, int xPos, int yPos) {
        super(whichOne, xPos, yPos);
        this.setFill(Color.ORANGE);
        hitsLimit = 2;
    }

    @Override
    public void eliminateBlock(Group root) {
        hits++;
        if(hits == hitsLimit) {
            PowerUp powerUp = new PowerUp(this.getX() + WIDTH/2, this.getY() + HEIGHT);
            root.getChildren().add(powerUp);
            root.getChildren().remove(this);
        }
    }

}
