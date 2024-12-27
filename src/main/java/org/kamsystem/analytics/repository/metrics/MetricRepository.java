package org.kamsystem.analytics.repository.metrics;

import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.analytics.dao.KamMetric;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MetricRepository implements IMetricRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CREATE_METRIC = """
        INSERT INTO public.kam_system_metrics (metric_name, metric_value,
                                               metric_value_type, timeframe, year, month, day, created_at)
        VALUES (:metricName, :metricValue, :valueType, :timeframe, :year, :month, :day, :createdAt)
        """;

    private static final String GET_FIRST_HUNDRED_METRIC_BY_ATTRIBUTES = """
        SELECT id, metric_name, metric_value, metric_value_type, timeframe, year, month, day, created_at
        FROM public.kam_system_metrics
        WHERE metric_name = :metricName AND timeframe = :timeframe order by created_at desc LIMIT 100
        """;

    @Override
    public void createMetric(KamMetric metric) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("metricName", metric.getMetricName());
        parameters.addValue("metricValue", metric.getMetricValue());
        parameters.addValue("valueType", metric.getValueType());
        parameters.addValue("timeframe", metric.getTimeframe());
        parameters.addValue("year", metric.getYear());
        parameters.addValue("month", metric.getMonth());
        parameters.addValue("day", metric.getDay());
        parameters.addValue("createdAt", metric.getCreatedAt());
        namedParameterJdbcTemplate.update(CREATE_METRIC, parameters);
    }

    @Override
    public List<KamMetric> getMetricsByAttributes(String metricName, String timeframe) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("metricName", metricName);
        parameters.addValue("timeframe", timeframe);
        return namedParameterJdbcTemplate.query(
            GET_FIRST_HUNDRED_METRIC_BY_ATTRIBUTES,
            parameters,
            (rs, rowNum) -> new KamMetric(
                rs.getLong("id"),
                rs.getString("metric_name"),
                rs.getBigDecimal("metric_value"),
                rs.getString("metric_value_type"),
                rs.getString("timeframe"),
                rs.getLong("year"),
                rs.getLong("month"),
                rs.getLong("day"),
                rs.getDate("created_at")
            )
        );
    }
}
