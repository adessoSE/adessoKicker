package de.adesso.kicker.ranking.service;

public enum KFactor {
    LOW(16),
    MEDIUM(24),
    HIGH(32);

    private final int value;

    KFactor(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
