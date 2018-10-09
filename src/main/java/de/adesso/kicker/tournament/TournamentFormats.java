package de.adesso.kicker.tournament;

public enum TournamentFormats {
    SINGLEELIMINATION("Single Elimination"), LASTMANSTANDING("Last Man Standing");

    private String format;

    TournamentFormats(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}