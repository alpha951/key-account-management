package org.kamsystem.restaurant.repository;

import java.util.List;
import org.kamsystem.restaurant.model.Restaurant;

public interface IRestaurantRepository {

    void createRestaurant(Restaurant restaurant);

    void updateRestaurant(Restaurant restaurant);

    List<Restaurant> getRestaurantByCreator(Long createdBy);

    Restaurant getRestaurantById(Long id);

    List<Restaurant> getAllRestaurants();

    Restaurant getRestaurantByIdAndCreatorId(Long restaurantId, Long userId);

    void updateRestaurantCreator(Long oldCreatorId, Long newCreatorId);
}
