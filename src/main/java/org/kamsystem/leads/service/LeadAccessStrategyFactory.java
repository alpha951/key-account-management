package org.kamsystem.leads.service;

import org.kamsystem.common.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
public class LeadAccessStrategyFactory {

    private final SuperAdminLeadAccessStrategy superAdminStrategy;
    private final KamLeadAccessStrategy kamStrategy;

    public LeadAccessStrategyFactory(SuperAdminLeadAccessStrategy superAdminStrategy,
        KamLeadAccessStrategy kamStrategy) {
        this.superAdminStrategy = superAdminStrategy;
        this.kamStrategy = kamStrategy;
    }

    public LeadAccessStrategy getStrategy(UserRole role) {
        switch (role) {
            case SUPER_ADMIN:
                return superAdminStrategy;
            case KEY_ACCOUNT_MANAGER:
                return kamStrategy;
            default:
                throw new IllegalArgumentException("Invalid user role: " + role);
        }
    }
}

