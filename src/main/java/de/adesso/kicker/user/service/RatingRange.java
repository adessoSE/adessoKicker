package de.adesso.kicker.user.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RatingRange {
    VERY_HIGH(1500),
    HIGH(1250);

    private final int rating;
}
