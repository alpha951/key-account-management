package org.kamsystem.analytics.repository.calculations;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IAnalyticsRepository {

    /**
     * Calculates the Average Order Value (AOV) for a given time range.
     *
     * @param startDate the start date of the timeframe
     * @param endDate   the end date of the timeframe
     * @return the average order value
     */
    BigDecimal getAverageOrderValue(LocalDate startDate, LocalDate endDate);

    /**
     * Calculates the Lead Conversion Rate (LCR) for a given time range.
     *
     * @param startDate the start date of the timeframe
     * @param endDate   the end date of the timeframe
     * @return the lead conversion rate as a percentage
     */
    BigDecimal getLeadConversionRate(LocalDate startDate, LocalDate endDate);

    /**
     * Calculates the number of interactions per lead for a given time range.
     *
     * @param startDate the start date of the timeframe
     * @param endDate   the end date of the timeframe
     * @return the number of interactions per lead
     */
    BigDecimal getInteractionsPerLead(LocalDate startDate, LocalDate endDate);

    /**
     * Calculates the number of interactions per KAM (Key Account Manager) for a given time range.
     *
     * @param startDate the start date of the timeframe
     * @param endDate   the end date of the timeframe
     * @return the number of interactions per KAM
     */
    BigDecimal getInteractionsPerKam(LocalDate startDate, LocalDate endDate);

    /**
     * Calculates the number of interactions per POC (Point of Contact) for a given time range.
     *
     * @param startDate the start date of the timeframe
     * @param endDate   the end date of the timeframe
     * @return the number of interactions per POC
     */
    BigDecimal getInteractionsPerPoc(LocalDate startDate, LocalDate endDate);

    /**
     * Calculates the Interaction Success Rate (ISR) for a given time range.
     *
     * @param startDate the start date of the timeframe
     * @param endDate   the end date of the timeframe
     * @return the interaction success rate as a percentage
     */
    BigDecimal getInteractionSuccessRate(LocalDate startDate, LocalDate endDate);

    /**
     * Calculates the Revenue per KAM for a given time range.
     *
     * @param startDate the start date of the timeframe
     * @param endDate   the end date of the timeframe
     * @return the revenue per KAM
     */
    BigDecimal getRevenuePerKam(LocalDate startDate, LocalDate endDate);

    /**
     * Calculates the Revenue per Restaurant for a given time range.
     *
     * @param startDate the start date of the timeframe
     * @param endDate   the end date of the timeframe
     * @return the revenue per restaurant
     */
    BigDecimal getRevenuePerRestaurant(LocalDate startDate, LocalDate endDate);
}
