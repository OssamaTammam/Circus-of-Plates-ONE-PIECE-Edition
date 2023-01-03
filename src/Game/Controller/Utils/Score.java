package Game.Controller.Utils;

import Game.Controller.Logging;

import java.util.Observable;
import java.util.Observer;

/**
 * Observer Design Pattern
 * This class is responsible for updating the score of the game
 */
public class Score implements Observer {
    Logging log = new Logging();
    int score = 0;
    int maxScore;
    boolean won;

    public Score(int maxScore) {
        this.maxScore = maxScore;
        won = false;
    }

    /**
     * Update the score of the game
     * <p>
     * Override the update method of the Observer interface
     */
    @Override
    public void update(Observable observable, Object o) {
        score++;
        if (score == maxScore)
            won = true;
        log.help().info("the score of the player is " + score);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int val) {
        score = val;
    }

    public boolean getStatues() {
        return won;
    }
}
