package org.kamsystem.restaurant.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.authentication.service.IAuthService;
import org.kamsystem.common.enums.UserRole;
import org.kamsystem.restaurant.exception.RestaurantErrorCode;
import org.kamsystem.restaurant.exception.RestaurantException;
import org.kamsystem.restaurant.model.Restaurant;
import org.kamsystem.restaurant.repository.IRestaurantRepository;
import org.kamsystem.restaurant.service.access.RestaurantAccessStrategy;
import org.kamsystem.restaurant.service.access.RestaurantAccessStrategyFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RestaurantService implements IRestaurantService {

    private final IAuthService authService;
    private final IRestaurantRepository restaurantRepository;
    private final RestaurantAccessStrategyFactory strategyFactory;

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
            throw new RestaurantException(RestaurantErrorCode.RESTAURANT_NOT_FOUND,
                "You are not allowed to update this restaurant");
        }
        restaurantRepository.updateRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> getRestaurantsByCreator() {
        Long userId = authService.getUserIdOfLoggedInUser();
        return restaurantRepository.getRestaurantByCreator(userId);
    }

    @Override
    public Restaurant getRestaurantById(Long restaurantId) {
        UserRole role = authService.getRoleOfLoggedInUser();
        Long userId = authService.getUserIdOfLoggedInUser();

        RestaurantAccessStrategy strategy = strategyFactory.getStrategy(role);
        return strategy.getRestaurantById(restaurantId, userId);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        UserRole role = authService.getRoleOfLoggedInUser();
        Long userId = authService.getUserIdOfLoggedInUser();

        RestaurantAccessStrategy strategy = strategyFactory.getStrategy(role);
        return strategy.getRestaurants(userId);
    }
}
