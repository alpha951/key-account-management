package org.kamsystem.analytics.service;

import java.util.List;
import org.kamsystem.analytics.dao.KamMetric;
import org.kamsystem.analytics.model.Metric;
import org.kamsystem.analytics.model.Timeframe;

public interface IMetricService {
    void calculateAndStoreMetrics(Timeframe timeframe);

    List<KamMetric> getMetrics(Metric metric, Timeframe timeframe);
}
