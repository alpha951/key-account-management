package org.kamsystem.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kamsystem.authentication.service.IAuthService;
import org.kamsystem.common.enums.UserRole;
import org.kamsystem.restaurant.exception.RestaurantErrorCode;
import org.kamsystem.restaurant.exception.RestaurantException;
import org.kamsystem.restaurant.model.Restaurant;
import org.kamsystem.restaurant.repository.IRestaurantRepository;
import org.kamsystem.restaurant.service.RestaurantService;
import org.kamsystem.restaurant.service.access.RestaurantAccessStrategy;
import org.kamsystem.restaurant.service.access.RestaurantAccessStrategyFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private IAuthService authService;

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private RestaurantAccessStrategyFactory strategyFactory;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    @DisplayName("Should create a restaurant successfully")
    void createRestaurantSuccessfully() {
        // Given
        Long mockCreatorId = 1L;
        Restaurant mockRestaurant = new Restaurant(null, "Test Restaurant", "123456", "City", "State", "Address", null, null, null);

        when(authService.getUserIdOfLoggedInUser()).thenReturn(mockCreatorId);
        doNothing().when(restaurantRepository).createRestaurant(mockRestaurant);

        // When
        restaurantService.createRestaurant(mockRestaurant);

        // Then
        assertEquals(mockCreatorId, mockRestaurant.getCreatedBy());
        verify(authService).getUserIdOfLoggedInUser();
        verify(restaurantRepository).createRestaurant(mockRestaurant);
    }

    @Test
    @DisplayName("Should update a restaurant successfully for the owner")
    void updateRestaurantSuccessfullyForOwner() {
        // Given
        Long mockUserId = 1L;
        Long restaurantId = 10L;
        UserRole mockRole = UserRole.KEY_ACCOUNT_MANAGER;
        Restaurant mockRestaurant = new Restaurant(restaurantId, "Updated Restaurant", "123456", "City", "State", "New Address", null, null, null);
        Restaurant existingRestaurant = new Restaurant(restaurantId, "Old Restaurant", "123456", "City", "State", "Old Address", mockUserId, null, null);

        when(authService.getUserIdOfLoggedInUser()).thenReturn(mockUserId);
        when(authService.getRoleOfLoggedInUser()).thenReturn(mockRole);
        when(restaurantRepository.getRestaurantById(restaurantId)).thenReturn(existingRestaurant);
        doNothing().when(restaurantRepository).updateRestaurant(mockRestaurant);

        // When
        restaurantService.updateRestaurant(mockRestaurant);

        // Then
        verify(authService).getUserIdOfLoggedInUser();
        verify(authService).getRoleOfLoggedInUser();
        verify(restaurantRepository).getRestaurantById(restaurantId);
        verify(restaurantRepository).updateRestaurant(mockRestaurant);
    }

    @Test
    @DisplayName("Should throw exception when non-owner tries to update restaurant")
    void updateRestaurantThrowsExceptionForNonOwner() {
        // Given
        Long mockUserId = 2L;
        Long restaurantId = 10L;
        UserRole mockRole = UserRole.KEY_ACCOUNT_MANAGER;
        Restaurant mockRestaurant = new Restaurant(restaurantId, "Updated Restaurant", "123456", "City", "State", "New Address", null, null, null);
        Restaurant existingRestaurant = new Restaurant(restaurantId, "Old Restaurant", "123456", "City", "State", "Old Address", 1L, null, null);

        when(authService.getUserIdOfLoggedInUser()).thenReturn(mockUserId);
        when(authService.getRoleOfLoggedInUser()).thenReturn(mockRole);
        when(restaurantRepository.getRestaurantById(restaurantId)).thenReturn(existingRestaurant);

        // When & Then
        RestaurantException exception = assertThrows(RestaurantException.class, () -> restaurantService.updateRestaurant(mockRestaurant));
        assertEquals(RestaurantErrorCode.RESTAURANT_NOT_FOUND, exception.getErrorCode());
        verify(authService).getUserIdOfLoggedInUser();
        verify(authService).getRoleOfLoggedInUser();
        verify(restaurantRepository).getRestaurantById(restaurantId);
        verify(restaurantRepository, never()).updateRestaurant(mockRestaurant);
    }

    @Test
    @DisplayName("Should fetch restaurants by creator")
    void getRestaurantsByCreator() {
        // Given
        Long mockUserId = 1L;
        List<Restaurant> mockRestaurants = List.of(
                new Restaurant(1L, "Restaurant 1", "123456", "City", "State", "Address 1", mockUserId, null, null),
                new Restaurant(2L, "Restaurant 2", "123456", "City", "State", "Address 2", mockUserId, null, null)
        );

        when(authService.getUserIdOfLoggedInUser()).thenReturn(mockUserId);
        when(restaurantRepository.getRestaurantByCreator(mockUserId)).thenReturn(mockRestaurants);

        // When
        List<Restaurant> restaurants = restaurantService.getRestaurantsByCreator();

        // Then
        assertEquals(mockRestaurants, restaurants);
        verify(authService).getUserIdOfLoggedInUser();
        verify(restaurantRepository).getRestaurantByCreator(mockUserId);
    }

    @Test
    @DisplayName("Should fetch restaurant by ID using access strategy")
    void getRestaurantByIdUsingAccessStrategy() {
        // Given
        Long restaurantId = 10L;
        Long mockUserId = 1L;
        UserRole mockRole = UserRole.KEY_ACCOUNT_MANAGER;
        Restaurant mockRestaurant = new Restaurant(restaurantId, "Test Restaurant", "123456", "City", "State", "Address", mockUserId, null, null);
        RestaurantAccessStrategy mockStrategy = mock(RestaurantAccessStrategy.class);

        when(authService.getUserIdOfLoggedInUser()).thenReturn(mockUserId);
        when(authService.getRoleOfLoggedInUser()).thenReturn(mockRole);
        when(strategyFactory.getStrategy(mockRole)).thenReturn(mockStrategy);
        when(mockStrategy.getRestaurantById(restaurantId, mockUserId)).thenReturn(mockRestaurant);

        // When
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        // Then
        assertEquals(mockRestaurant, restaurant);
        verify(authService).getUserIdOfLoggedInUser();
        verify(authService).getRoleOfLoggedInUser();
        verify(strategyFactory).getStrategy(mockRole);
        verify(mockStrategy).getRestaurantById(restaurantId, mockUserId);
    }

    @Test
    @DisplayName("Should fetch all restaurants using access strategy")
    void getAllRestaurantsUsingAccessStrategy() {
        // Given
        Long mockUserId = 1L;
        UserRole mockRole = UserRole.KEY_ACCOUNT_MANAGER;
        List<Restaurant> mockRestaurants = List.of(
                new Restaurant(1L, "Restaurant 1", "123456", "City", "State", "Address 1", mockUserId, null, null),
                new Restaurant(2L, "Restaurant 2", "123456", "City", "State", "Address 2", mockUserId, null, null)
        );
        RestaurantAccessStrategy mockStrategy = mock(RestaurantAccessStrategy.class);

        when(authService.getUserIdOfLoggedInUser()).thenReturn(mockUserId);
        when(authService.getRoleOfLoggedInUser()).thenReturn(mockRole);
        when(strategyFactory.getStrategy(mockRole)).thenReturn(mockStrategy);
        when(mockStrategy.getRestaurants(mockUserId)).thenReturn(mockRestaurants);

        // When
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();

        // Then
        assertEquals(mockRestaurants, restaurants);
        verify(authService).getUserIdOfLoggedInUser();
        verify(authService).getRoleOfLoggedInUser();
        verify(strategyFactory).getStrategy(mockRole);
        verify(mockStrategy).getRestaurants(mockUserId);
    }
}
