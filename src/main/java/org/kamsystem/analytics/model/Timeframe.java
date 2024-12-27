package org.kamsystem.analytics.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Timeframe {
    DAILY(1),
    WEEKLY(7),
    MONTHLY(30),
    QUARTERLY(90),
    YEARLY(365);

    private final Integer days;
}
