package org.kamsystem.analytics.cron;

import lombok.AllArgsConstructor;
import org.kamsystem.analytics.model.Timeframe;
import org.kamsystem.analytics.service.IMetricService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@AllArgsConstructor
public class AnalyticsCron {

    private final IMetricService metricService;

    // Run at midnight every day
    @Scheduled(cron = "0 0 0 * * ?")
    public void calculateDailyMetrics() {
        metricService.calculateAndStoreMetrics(Timeframe.DAILY);
    }

    // Run at midnight every Monday
    @Scheduled(cron = "0 0 0 ? * MON")
    public void calculateWeeklyMetrics() {
        metricService.calculateAndStoreMetrics(Timeframe.WEEKLY);
    }

    // Run at midnight on the first of every month
    @Scheduled(cron = "0 0 0 1 * ?")  // Run at midnight on the first of every month
    public void calculateMonthlyMetrics() {
        metricService.calculateAndStoreMetrics(Timeframe.MONTHLY);
    }

    // Run at midnight on the first of January, April, July, and October
    @Scheduled(cron = "0 0 0 1 1,4,7,10 ?")
    public void calculateQuarterlyMetrics() {
        metricService.calculateAndStoreMetrics(Timeframe.QUARTERLY);
    }

    // Run at midnight on the first of January
    @Scheduled(cron = "0 0 0 1 1 ?")
    public void calculateYearlyMetrics() {
        metricService.calculateAndStoreMetrics(Timeframe.YEARLY);
    }
}
