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
    private int currentLevel;
    private Text lifeCounter;
    private Text scoreDisplay;
    private Text levelCounter;

    public static final int STATUS_DISPLAY_SIZE = 10;
    public static final int BLOCK_VAL = 10;

    public StatusDisplay(int lives) {
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
        statusPiece.setId(id);
        return statusPiece;
    }

    public void subtractLifeCounter(Ball ball) {
        numLives = numLives-1;
        lifeCounter.setText("Lives Remaining: " + numLives);
    }

    public void addLifeCounter() {
        numLives = numLives+1;
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

    public void displayLossStatus(ObservableList<Node> gameElements, Timeline animation) {
        Text gameOverMessage = new Text();
        gameOverMessage.setText("You Lose.");
        gameOverMessage.setFill(Color.RED);
        gameOverMessage.setX(GamePlay.SCENE_WIDTH / 4 - 10);
        gameOverMessage.setY(GamePlay.SCENE_HEIGHT / 2);
        setTextFont(gameOverMessage, "veranda", 60);
        gameOverMessage.setStrokeWidth(3);
        gameOverMessage.setStroke(Color.BLACK);

        gameElements.add(gameOverMessage);
        animation.stop();

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

}