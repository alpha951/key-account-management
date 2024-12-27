package org.kamsystem.analytics.dao;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KamMetric {
    private Long id;
    private String metricName;
    private BigDecimal metricValue;
    private String valueType;
    private String timeframe;
    private Long year;
    private Long month;
    private Long day;
    private Date createdAt;
}
