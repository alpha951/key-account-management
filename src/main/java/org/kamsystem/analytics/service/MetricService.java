package org.kamsystem.analytics.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.analytics.dao.KamMetric;
import org.kamsystem.analytics.model.Metric;
import org.kamsystem.analytics.model.Timeframe;
import org.kamsystem.analytics.repository.calculations.IAnalyticsRepository;
import org.kamsystem.analytics.repository.metrics.IMetricRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MetricService implements IMetricService {

    private final IAnalyticsRepository analyticsRepository;
    private final IMetricRepository metricRepository;

    @Transactional
    public void calculateAndStoreMetrics(Timeframe timeframe) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusDays(timeframe.getDays());

        for (Metric metric : Metric.values()) {
            BigDecimal value = calculateMetric(metric, startDate, now);
            metricRepository.createMetric(new KamMetric(
                null,
                metric.name(),
                value,
                metric.getValueType(),
                timeframe.name(),
                (long) now.getYear(),
                (long) now.getMonthValue(),
                (long) now.getDayOfMonth(),
                new Date()
            ));
        }
    }

    @Override
    public List<KamMetric> getMetrics(Metric metric, Timeframe timeframe) {
       return metricRepository.getMetricsByAttributes(metric.name(), timeframe.name());
    }

    private BigDecimal calculateMetric(Metric metric, LocalDate startDate, LocalDate endDate) {
        return switch (metric) {
            case AVERAGE_ORDER_VALUE -> analyticsRepository.getAverageOrderValue(startDate, endDate);
            case LEAD_CONVERTION_RATE -> analyticsRepository.getLeadConversionRate(startDate, endDate);
            case INTERACTION_PER_LEAD -> analyticsRepository.getInteractionsPerLead(startDate, endDate);
            case INTERACTION_PER_KAM -> analyticsRepository.getInteractionsPerKam(startDate, endDate);
            case INTERACTION_PER_POC -> analyticsRepository.getInteractionsPerPoc(startDate, endDate);
            case INTERACTION_SUCCESS_RATE -> analyticsRepository.getInteractionSuccessRate(startDate, endDate);
            case REVENUE_PER_KAM -> analyticsRepository.getRevenuePerKam(startDate, endDate);
            case REVENUE_PER_RESTAURANT -> analyticsRepository.getRevenuePerRestaurant(startDate, endDate);
        };
    }
}
