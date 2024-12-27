package org.kamsystem.restaurant.service.access.userroles;

import java.util.List;
import org.kamsystem.restaurant.model.Restaurant;
import org.kamsystem.restaurant.repository.IRestaurantRepository;
import org.kamsystem.restaurant.service.access.RestaurantAccessStrategy;
import org.springframework.stereotype.Component;

@Component
public class CreatorRestaurantAccessStrategy implements RestaurantAccessStrategy {

    private final IRestaurantRepository restaurantRepository;

    public CreatorRestaurantAccessStrategy(IRestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant getRestaurantById(Long restaurantId, Long userId) {
        // Creator can only access restaurants they created
        return restaurantRepository.getRestaurantByIdAndCreatorId(restaurantId, userId);
    }

    @Override
    public List<Restaurant> getRestaurants(Long userId) {
        // Creator can view only their restaurants
        return restaurantRepository.getRestaurantByCreator(userId);
    }
}

