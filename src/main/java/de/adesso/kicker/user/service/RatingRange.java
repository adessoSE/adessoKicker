package de.adesso.kicker.user.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RatingRange {
    VERY_HIGH(2400),
    HIGH(2100);

    private final int rating;
}
