package org.kamsystem.restaurantpoc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PocRole {
    OWNER(1),
    GENERAL_MANAGER(2),
    OPERATION_MANAGER(3),
    SALES_MANAGER(4),
    INVENTORY_MANAGER(5),
    ACCOUNTANT(6),
    DELIVERY_MANAGER(7);

    private final int id;

    public static PocRole getById(int id) {
        for (PocRole pocRole : values()) {
            if (pocRole.getId() == id) {
                return pocRole;
            }
        }
        return null;
    }
}
