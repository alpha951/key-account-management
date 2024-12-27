package org.kamsystem.analytics.repository.metrics;

import java.util.List;
import org.kamsystem.analytics.dao.KamMetric;

public interface IMetricRepository {

    void createMetric(KamMetric metric);

    List<KamMetric> getMetricsByAttributes(String metricName, String timeframe);
}
