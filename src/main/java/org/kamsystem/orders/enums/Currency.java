package org.kamsystem.orders.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Currency {
    INR("Indian Rupee");

    private final String currencyName;
}
