package com.climaware.wind.model;

/**
 * Created by greg on 3/9/19.
 */
public class WindScore {

    int value;
    private Score score;
    private WindFact windFact;

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public WindFact getWindFact() {
        return windFact;
    }

    public void setWindFact(WindFact windFact) {
        this.windFact = windFact;
    }

    public enum Score {
        LOW,
        MEDIUM,
        HIGH
    }
}
