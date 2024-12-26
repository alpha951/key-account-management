package org.kamsystem.restaurant.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.authentication.service.IAuthService;
import org.kamsystem.common.enums.UserRole;
import org.kamsystem.restaurant.model.Restaurant;
import org.kamsystem.restaurant.repository.IRestaurantRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RestaurantService implements IRestaurantService {

    private final IAuthService authService;
    private final IRestaurantRepository restaurantRepository;

    @Override
    public void createRestaurant(Restaurant restaurant) {
        Long creatorId = authService.getUserIdOfLoggedInUser();
        restaurant.setCreatedBy(creatorId);
        restaurantRepository.createRestaurant(restaurant);
    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        Long userId = authService.getUserIdOfLoggedInUser();
        UserRole role = authService.getRoleOfLoggedInUser();

        if (!role.equals(UserRole.SUPER_ADMIN) && !userId.equals(restaurant.getCreatedBy())) {
            throw new RuntimeException("You are not allowed to update this restaurant");
        }
        restaurantRepository.updateRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> getRestaurantsByCreator() {
        Long creatorId = authService.getUserIdOfLoggedInUser();
        return restaurantRepository.getRestaurantByCreator(creatorId);
    }

    @Override
    public Restaurant getRestaurantById(Long restaurantId) {
        UserRole role = authService.getRoleOfLoggedInUser();
        if(role.equals(UserRole.SUPER_ADMIN)) {
            return restaurantRepository.getRestaurantById(restaurantId);
        }
        Long userId = authService.getUserIdOfLoggedInUser();
        return restaurantRepository.getRestaurantByIdAndCreatorId(restaurantId, userId);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.getAllRestaurants();
    }
}
