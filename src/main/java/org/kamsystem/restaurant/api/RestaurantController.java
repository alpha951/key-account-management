package org.kamsystem.restaurant.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kamsystem.authentication.annotation.UserAuth;
import org.kamsystem.common.enums.UserRole;
import org.kamsystem.common.model.ApiResponse;
import org.kamsystem.restaurant.exception.RestaurantException;
import org.kamsystem.restaurant.model.Restaurant;
import org.kamsystem.restaurant.service.IRestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/restaurants")
@AllArgsConstructor
public class RestaurantController {

    private final IRestaurantService restaurantService;

    @UserAuth(allowedFor = {UserRole.SUPER_ADMIN, UserRole.KEY_ACCOUNT_MANAGER})
    @PostMapping("/create")
    public ResponseEntity<?> createRestaurant(@RequestBody Restaurant restaurant) {
        restaurantService.createRestaurant(restaurant);
        return new ResponseEntity<>(new ApiResponse<>(true, "Restaurant created successfully"), HttpStatus.OK);
    }

    @UserAuth(allowedFor = {UserRole.SUPER_ADMIN, UserRole.KEY_ACCOUNT_MANAGER})
    @PostMapping("/update")
    public ResponseEntity<?> updateRestaurant(@RequestBody @Valid Restaurant restaurant,
        BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new ApiResponse<>(false, result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        restaurantService.updateRestaurant(restaurant);
        return new ResponseEntity<>(new ApiResponse<>(true, "Restaurant updated successfully"), HttpStatus.OK);
    }

    @UserAuth(allowedFor = {UserRole.KEY_ACCOUNT_MANAGER})
    @GetMapping("/get")
    public ResponseEntity<?> getRestaurantsByCreator() {
        return new ResponseEntity<>(new ApiResponse<>(true, restaurantService.getRestaurantsByCreator()),
            HttpStatus.OK
        );
    }

    @UserAuth(allowedFor = {UserRole.SUPER_ADMIN,
        UserRole.KEY_ACCOUNT_MANAGER})
    @GetMapping("/id")
    public ResponseEntity<?> getRestaurantById(@RequestParam Long restaurantId) {
        return new ResponseEntity<>(new ApiResponse<>(true, restaurantService.getRestaurantById(restaurantId)),
            HttpStatus.OK
        );
    }

    @UserAuth(allowedFor = {UserRole.SUPER_ADMIN})
    @GetMapping("/all")
    public ResponseEntity<?> getAllRestaurants() {
        return new ResponseEntity<>(new ApiResponse<>(true, restaurantService.getAllRestaurants()), HttpStatus.OK);
    }

    @ExceptionHandler(RestaurantException.class)
    public ResponseEntity<?> handleResolutionException(RestaurantException e) {
        log.debug("Restaurant exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ApiResponse<>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
