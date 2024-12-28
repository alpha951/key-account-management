package org.kamsystem.analytics.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MetricRequest {
    private Metric metric;
    private Timeframe timeframe;
}
