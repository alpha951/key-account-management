package org.kamsystem.restaurant.service.access;

import java.util.List;
import org.kamsystem.restaurant.model.Restaurant;

public interface RestaurantAccessStrategy {
    Restaurant getRestaurantById(Long restaurantId, Long userId);
    List<Restaurant> getRestaurants(Long userId);
}

