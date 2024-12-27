package org.kamsystem.restaurant.service.access;

import org.kamsystem.common.enums.UserRole;
import org.kamsystem.restaurant.service.access.userroles.CreatorRestaurantAccessStrategy;
import org.kamsystem.restaurant.service.access.userroles.SuperAdminRestaurantAccessStrategy;
import org.springframework.stereotype.Component;

@Component
public class RestaurantAccessStrategyFactory {

    private final SuperAdminRestaurantAccessStrategy superAdminStrategy;
    private final CreatorRestaurantAccessStrategy creatorStrategy;

    public RestaurantAccessStrategyFactory(SuperAdminRestaurantAccessStrategy superAdminStrategy,
                                           CreatorRestaurantAccessStrategy creatorStrategy) {
        this.superAdminStrategy = superAdminStrategy;
        this.creatorStrategy = creatorStrategy;
    }

    public RestaurantAccessStrategy getStrategy(UserRole role) {
        switch (role) {
            case SUPER_ADMIN:
                return superAdminStrategy;
            case KEY_ACCOUNT_MANAGER:
                return creatorStrategy;
            default:
                throw new IllegalArgumentException("Unsupported role: " + role);
        }
    }
}
