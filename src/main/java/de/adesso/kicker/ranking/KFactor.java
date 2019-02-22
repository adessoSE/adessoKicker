package de.adesso.kicker.ranking;

public enum KFactor {
    LOW(16),
    MEDIUM(24),
    HIGH(32);

    private int value;

    KFactor(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
