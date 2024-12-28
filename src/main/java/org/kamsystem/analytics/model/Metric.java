package org.kamsystem.analytics.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Metric {
    /*
        total revenue / total number of orders
     */
    AVERAGE_ORDER_VALUE("NUMBER"),

    /*
        leads that converted to orders / total number of leads
     */
    LEAD_CONVERTION_RATE("PERCENTAGE"),

    /*
        total number of interactions / total number of leads
     */
    INTERACTION_PER_LEAD("NUMBER"),

    /*
        total number of interactions / total number of KAMs
     */
    INTERACTION_PER_KAM("NUMBER"),

    /*
        total number of interactions / total number of POCs
     */
    INTERACTION_PER_POC("NUMBER"),

    /*
        total number of orders / total number of interactions
     */
    INTERACTION_SUCCESS_RATE("PERCENTAGE"),

    /*
        total revenue / total number KAMs
     */
    REVENUE_PER_KAM("NUMBER"),

    /*
        total revenue / total number of restaurants
     */
    REVENUE_PER_RESTAURANT("NUMBER");

    private final String valueType;
}
