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
 * external calls from the GamePlay class.
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

    /**Constructs a Status display with three different text pieces for number of lives, score, and level. StatusDisplay also
     * holds the values for these text pieces as integer instance variables
     * @param lives the number of lives the game is set to give the player*/

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

    /**Set status display to score of 0, level 1, and default number of lives*/
    public void resetStatusDisplay() {
        changeLifeCounter(maxLives-numLives);
        updateLevelDisplay(1);
        updateScoreDisplay(myScore*-1);
    }
    /** Change life counter in life display when cheat key is pressed or when ball passes bottom wall*/
    public void changeLifeCounter(int amount) {
        numLives = numLives + amount;
        lifeCounter.setText("Lives Remaining: " + numLives);
    }

    /**Change score in score display when block is hit or power-up is obtained*/
    public void updateScoreDisplay(int amount) {
        myScore += amount;
        scoreDisplay.setText("Score: " + myScore);
    }
    /**Change level in level display when all blocks are cleared or a cheat key is pressed*/
    public void updateLevelDisplay(int level) {
        currentLevel = level;
        levelCounter.setText("Level: " + currentLevel);

    }
    /**Displays a message when the game ends indicating whether player won or lost
     * @param gameElements a list of the elements in the scene. This allows the method to add the game over message to the scene
     * @param myAnimation the animation timeline of the scene. This allows the method to stop the animation when the game is over
     * @param win a boolean to indicate whether the game was won or lost*/
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

    /**Returns number of lives player has in status display*/
    public int getNumLives() {
        return numLives;
    }

    /**Returns score player has in status display*/
    public int getMyScore() {return myScore;}

    /**Returns the current level of the game*/
    public int getCurrentLevel() {return currentLevel;}

    /**Returns the text for the score display. Used to add the text to the game elements list in GamePlay*/
    public Text getScoreDisplay() {
        return scoreDisplay;
    }

    /**Returns the text for the life display. Used to add the text to the game elements list in GamePlay*/
    public Text getLifeCounter() {
        return lifeCounter;
    }

    /**Returns the text for the level display. Used to add the text to the game elements list in GamePlay*/
    public Text getLevelCounter() {
        return levelCounter;
    }
}
