package org.kamsystem.restaurant.service.access.userroles;

import java.util.List;
import org.kamsystem.restaurant.model.Restaurant;
import org.kamsystem.restaurant.repository.IRestaurantRepository;
import org.kamsystem.restaurant.service.access.RestaurantAccessStrategy;
import org.springframework.stereotype.Component;

@Component
public class SuperAdminRestaurantAccessStrategy implements RestaurantAccessStrategy {

    private final IRestaurantRepository restaurantRepository;

    public SuperAdminRestaurantAccessStrategy(IRestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant getRestaurantById(Long restaurantId, Long userId) {
        // Super Admin can access any restaurant
        return restaurantRepository.getRestaurantById(restaurantId);
    }

    @Override
    public List<Restaurant> getRestaurants(Long userId) {
        // Super Admin can view all restaurants
        return restaurantRepository.getAllRestaurants();
    }
}

