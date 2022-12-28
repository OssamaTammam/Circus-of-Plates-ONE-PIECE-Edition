package Game.Utils;

import Game.logging;

import java.util.Observable;
import java.util.Observer;

public class Score implements Observer {
    logging log = new logging();
    int score = 0;
    int maxScore;
    boolean won;

    public Score(int maxScore) {
        this.maxScore = maxScore;
        won = false;
    }

    @Override
    public void update(Observable observable, Object o) {
        score++;
        if (score == maxScore)
            won = true;
        log.help().info("the score of the player is " + Integer.toString(score));
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
