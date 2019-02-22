package de.adesso.kicker.ranking;

public enum RatingRange {
    VERY_HIGH(2400),
    HIGH(2100);

    private final int rating;

    RatingRange(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }
}
