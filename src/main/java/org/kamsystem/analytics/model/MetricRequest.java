package org.kamsystem.analytics.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MetricRequest {
    private Matric metric;
    private Timeframe timeframe;
}
