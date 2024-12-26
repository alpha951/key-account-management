package org.kamsystem.restaurant.service;

import java.util.List;
import org.kamsystem.restaurant.model.Restaurant;
import org.kamsystem.restaurant.repository.IRestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService implements IRestaurantService {

    private IRestaurantRepository restaurantRepository;

    @Override
    public void createRestaurant(Restaurant restaurant) {
        restaurantRepository.createRestaurant(restaurant);
    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        restaurantRepository.updateRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> getRestaurantsByCreator(Long creatorId) {
        return restaurantRepository.getRestaurantByCreator(creatorId);
    }

    @Override
    public Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.getRestaurantById(restaurantId);
    }
}
