package org.kamsystem.restaurant.service;

import java.util.List;
import org.kamsystem.restaurant.model.Restaurant;

public interface IRestaurantService {

    void createRestaurant(Restaurant restaurant);

    void updateRestaurant(Restaurant restaurant);

    List<Restaurant> getRestaurantsByCreator();

    Restaurant getRestaurantById(Long restaurantId);

    List<Restaurant> getAllRestaurants();
}
