package org.kamsystem.analytics.repository.calculations;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class AnalyticsRepository implements IAnalyticsRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    private static final String AVERAGE_ORDER_VALUE_FOR_TIMEFRAME =
        """
            SELECT
                CASE
                    WHEN COUNT(order_id) = 0 THEN 0
                    ELSE SUM(amount) / COUNT(order_id)
                END AS average_order_value
            FROM orders
            WHERE created_at BETWEEN :startDate AND :endDate
            """;

    private static final String LEAD_CONVERSION_RATE_FOR_TIMEFRAME =
        """
            SELECT
                COALESCE(
                    (CAST(SUM(CASE WHEN lead_status = 'CONVERTED' THEN 1 ELSE 0 END) AS FLOAT)
                     / NULLIF(COUNT(lead_id), 0)) * 100,
                    0
                ) AS lead_conversion_rate
            FROM lead
            WHERE created_at BETWEEN :startDate AND :endDate
            """;

    private static final String INTERACTIONS_PER_LEAD_FOR_TIMEFRAME =
        """
            SELECT
                CASE
                    WHEN COUNT(DISTINCT it.lead_id) = 0 THEN 0
                    ELSE (CAST(COUNT(it.id) AS FLOAT) / COUNT(DISTINCT it.lead_id))
                END AS interactions_per_lead
            FROM interaction it
            INNER JOIN lead ld ON it.lead_id = ld.lead_id
            WHERE it.created_at BETWEEN :startDate AND :endDate
            """;

    private static final String INTERACTIONS_PER_KAM_FOR_TIMEFRAME =
        """
            SELECT
                CASE
                    WHEN COUNT(DISTINCT caller_id) = 0 THEN 0
                    ELSE (CAST(COUNT(id) AS FLOAT) / COUNT(DISTINCT caller_id))
                END AS interactions_per_kam
            FROM interaction
            WHERE created_at BETWEEN :startDate AND :endDate
            """;

    private static final String INTERACTIONS_PER_POC_FOR_TIMEFRAME =
        """
            SELECT
                CASE
                    WHEN COUNT(DISTINCT poc_id) = 0 THEN 0
                    ELSE (CAST(COUNT(id) AS FLOAT) / COUNT(DISTINCT poc_id))
                END AS interactions_per_poc
            FROM interaction
            WHERE created_at BETWEEN :startDate AND :endDate
            """;

    private static final String INTERACTION_SUCCESS_RATE_FOR_TIMEFRAME =
        """
            SELECT
                CASE
                    WHEN COUNT(interaction_id) = 0 THEN 0
                    ELSE (CAST(COUNT(order_id) AS FLOAT) / COUNT(interaction_id)) * 100
                END AS interaction_success_rate
            FROM orders
            INNER JOIN interaction ON orders.interaction_id = interaction.id
            WHERE orders.created_at BETWEEN :startDate AND :endDate
            """;

    private static final String REVENUE_PER_KAM_FOR_TIMEFRAME =
        """
            SELECT
                CASE
                    WHEN COUNT(DISTINCT created_by) = 0 THEN 0
                    ELSE (SUM(amount) / COUNT(DISTINCT created_by))
                END AS revenue_per_kam
            FROM orders
            WHERE created_at BETWEEN :startDate AND :endDate
            """;

    private static final String REVENUE_PER_RESTAURANT_FOR_TIMEFRAME =
        """
            SELECT
                CASE
                    WHEN COUNT(DISTINCT restaurant_id) = 0 THEN 0
                    ELSE (SUM(amount) / COUNT(DISTINCT restaurant_id))
                END AS revenue_per_restaurant
            FROM orders
            WHERE created_at BETWEEN :startDate AND :endDate
            """;

    @Override
    public BigDecimal getAverageOrderValue(LocalDate startDate, LocalDate endDate) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("startDate", startDate)
            .addValue("endDate", endDate);
        return namedParameterJdbcTemplate.queryForObject(AVERAGE_ORDER_VALUE_FOR_TIMEFRAME, params, BigDecimal.class);
    }

    @Override
    public BigDecimal getLeadConversionRate(LocalDate startDate, LocalDate endDate) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("startDate", startDate)
            .addValue("endDate", endDate);
        return namedParameterJdbcTemplate.queryForObject(LEAD_CONVERSION_RATE_FOR_TIMEFRAME, params, BigDecimal.class);
    }

    @Override
    public BigDecimal getInteractionsPerLead(LocalDate startDate, LocalDate endDate) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("startDate", startDate)
            .addValue("endDate", endDate);
        return namedParameterJdbcTemplate.queryForObject(INTERACTIONS_PER_LEAD_FOR_TIMEFRAME, params, BigDecimal.class);
    }

    @Override
    public BigDecimal getInteractionsPerKam(LocalDate startDate, LocalDate endDate) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("startDate", startDate)
            .addValue("endDate", endDate);
        return namedParameterJdbcTemplate.queryForObject(INTERACTIONS_PER_KAM_FOR_TIMEFRAME, params, BigDecimal.class);
    }

    @Override
    public BigDecimal getInteractionsPerPoc(LocalDate startDate, LocalDate endDate) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("startDate", startDate)
            .addValue("endDate", endDate);
        return namedParameterJdbcTemplate.queryForObject(INTERACTIONS_PER_POC_FOR_TIMEFRAME, params, BigDecimal.class);
    }

    @Override
    public BigDecimal getInteractionSuccessRate(LocalDate startDate, LocalDate endDate) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("startDate", startDate)
            .addValue("endDate", endDate);
        return namedParameterJdbcTemplate.queryForObject(INTERACTION_SUCCESS_RATE_FOR_TIMEFRAME, params,
            BigDecimal.class);
    }

    @Override
    public BigDecimal getRevenuePerKam(LocalDate startDate, LocalDate endDate) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("startDate", startDate)
            .addValue("endDate", endDate);
        return namedParameterJdbcTemplate.queryForObject(REVENUE_PER_KAM_FOR_TIMEFRAME, params, BigDecimal.class);
    }

    @Override
    public BigDecimal getRevenuePerRestaurant(LocalDate startDate, LocalDate endDate) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("startDate", startDate)
            .addValue("endDate", endDate);
        return namedParameterJdbcTemplate.queryForObject(REVENUE_PER_RESTAURANT_FOR_TIMEFRAME, params,
            BigDecimal.class);
    }
}

