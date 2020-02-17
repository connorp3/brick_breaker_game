package breakout;

import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * This class handles the status display at the top of the game screen. It keeps track of the current level, the
 * current score, and the number of lives the player has left. These values are all updated within this class by
 * external calls from other classes.
 *
 * @author cgp19, jmt86
 */
public class StatusDisplay {
    private int myScore;
    private int numLives;
    private int maxLives;
    private int currentLevel;
    private Text lifeCounter;
    private Text scoreDisplay;
    private Text levelCounter;
    private Text gameOverMessage;
    private Text closeWindowMessage;

    public static final int STATUS_DISPLAY_SIZE = 10;

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
        updateScoreDisplay(myScore*-1);
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
        createGameOverMessage();

        closeWindowMessage = new Text();
        createCloseWindowMessage();

        if(win) {
            gameOverMessage.setText("You Win.");
            gameOverMessage.setFill(Color.GREEN);

        } else {
            gameOverMessage.setText("You Lose.");
            gameOverMessage.setFill(Color.RED);
        }

        showCloseWindowMessage(closeWindowMessage);
        gameElements.add(gameOverMessage);
        gameElements.add(closeWindowMessage);
        myAnimation.stop();
    }

    private void createCloseWindowMessage() {
        setTextFont(closeWindowMessage, "veranda", 30);
        closeWindowMessage.setX(gameOverMessage.getX() - 30);
        closeWindowMessage.setY(gameOverMessage.getY() + 50);
    }

    private void createGameOverMessage() {
        setTextFont(gameOverMessage, "veranda", 60);
        gameOverMessage.setX(GamePlay.SCENE_WIDTH / 4 - 10);
        gameOverMessage.setY(GamePlay.SCENE_HEIGHT / 2);
        gameOverMessage.setStrokeWidth(3);
        gameOverMessage.setStroke(Color.BLACK);
    }

    private void showCloseWindowMessage(Text closeWindowMessage) {
        closeWindowMessage.setText("Close window to restart.");
    }

    public int getNumLives() {
        return numLives;
    }

    public int getMyScore() {return myScore;}

    public int getCurrentLevel() {return currentLevel;}

    public Text getScoreDisplay() {
        return scoreDisplay;
    }

    public Text getLifeCounter() {
        return lifeCounter;
    }

    public Text getLevelCounter() {
        return levelCounter;
    }
}
