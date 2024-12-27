package org.kamsystem.restaurantpoc.service.access;

import org.kamsystem.common.enums.UserRole;
import org.kamsystem.restaurantpoc.service.access.userroles.KamPocAccessStrategy;
import org.kamsystem.restaurantpoc.service.access.userroles.SuperAdminPocAccessStrategy;
import org.springframework.stereotype.Component;

@Component
public class PocAccessStrategyFactory {

    private final SuperAdminPocAccessStrategy superAdminStrategy;
    private final KamPocAccessStrategy kamStrategy;

    public PocAccessStrategyFactory(SuperAdminPocAccessStrategy superAdminStrategy,
        KamPocAccessStrategy kamStrategy) {
        this.superAdminStrategy = superAdminStrategy;
        this.kamStrategy = kamStrategy;
    }

    public PocAccessStrategy getStrategy(UserRole role) {
        switch (role) {
            case SUPER_ADMIN:
                return superAdminStrategy;
            case KEY_ACCOUNT_MANAGER:
                return kamStrategy;
            default:
                throw new IllegalArgumentException("Unsupported role: " + role);
        }
    }
}
