package de.adesso.kicker.ranking.persistence;

public enum Outcome {
    WON(1),
    LOST(0);

    private final int score;

    Outcome(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
