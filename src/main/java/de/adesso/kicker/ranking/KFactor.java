package de.adesso.kicker.ranking;

public enum KFactor {
    LOW(16),
    MEDIUM(24),
    HIGH(32);

    private int kFactor;

    KFactor(int kFactor) {
        this.kFactor = kFactor;
    }

    public int getKFactor() {
        return kFactor;
    }
}
