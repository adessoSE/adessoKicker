package de.adesso.kicker.statistics.ranking.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Outcome {
    WON(1),
    LOST(0);

    private final int score;
}
