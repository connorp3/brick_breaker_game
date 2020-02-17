package breakout;

import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class StatusDisplay {
    private int myScore;
    private int numLives;
    private int maxLives;
    private int currentLevel;
    private Text lifeCounter;
    private Text scoreDisplay;
    private Text levelCounter;
    private Text gameOverMessage;

    public static final int STATUS_DISPLAY_SIZE = 10;
    public static final int BLOCK_VAL = 10;

    public StatusDisplay(int lives) {
        maxLives = lives;
        numLives = lives;
        lifeCounter = createStatusPiece("Lives Remaining: ", numLives, 10, "lifeCounter");
        myScore = 0;
        scoreDisplay = createStatusPiece("Score: ", myScore, GamePlay.SCENE_WIDTH - 75, "scoreDisplay");
        currentLevel = 1;
        levelCounter = createStatusPiece("Level: ", currentLevel, GamePlay.SCENE_WIDTH / 2, "levelCounter");

    }

    private void setTextFont(Text currText, String fontType, int textSize) {
        currText.setFont(Font.font(fontType, FontWeight.BOLD, FontPosture.REGULAR, textSize));
    }

    private Text createStatusPiece(String message, int count, double xPos, String id) {
        Text statusPiece = new Text();
        statusPiece.setX(xPos);
        statusPiece.setY(20);
        setTextFont(statusPiece, "verdana", STATUS_DISPLAY_SIZE);
        statusPiece.setText(message + count);
        statusPiece.setVisible(false);
        statusPiece.setId(id);
        return statusPiece;
    }

    public void resetStatusDisplay() {
        changeLifeCounter(maxLives-numLives);
        updateLevelDisplay(1);
        updateScoreDisplay(0);
    }

    public void changeLifeCounter(int amount) {
        numLives = numLives + amount;
        lifeCounter.setText("Lives Remaining: " + numLives);
    }


    public void updateScoreDisplay(int amount) {
        myScore += amount;
        scoreDisplay.setText("Score: " + myScore);
    }

    public void updateLevelDisplay(int level) {
        currentLevel = level;
        levelCounter.setText("Level: " + currentLevel);

    }

    public void displayFinalStatus(ObservableList<Node> gameElements, Timeline myAnimation, boolean win) {
        gameOverMessage = new Text();
        setTextFont(gameOverMessage, "veranda", 60);
        gameOverMessage.setX(GamePlay.SCENE_WIDTH / 4 - 10);
        gameOverMessage.setY(GamePlay.SCENE_HEIGHT / 2);
        gameOverMessage.setStrokeWidth(3);
        gameOverMessage.setStroke(Color.BLACK);
        if(win) {
            gameOverMessage.setText("You Win.\nClose Window");
            gameOverMessage.setFill(Color.GREEN);
        } else {
            gameOverMessage.setText("You Lose.\nClose Window");
            gameOverMessage.setFill(Color.RED);
        }
        gameElements.add(gameOverMessage);
        myAnimation.stop();
    }

    public int getNumLives() {
        return numLives;
    }

    public int getMyScore() {return myScore;}

    public Text getScoreDisplay() {
        return scoreDisplay;
    }

    public Text getLifeCounter() {
        return lifeCounter;
    }

    public Text getLevelCounter() {
        return levelCounter;
    }

    public void displayWinStatus(ObservableList<Node> gameElements) {
    }
}
