package org.kamsystem.restaurant.exception;

public enum RestaurantErrorCode {
    RESTAURANT_NOT_FOUND("Restaurant not found"),
    RESTAURANT_CREATION_FAILED("Restaurant creation failed"),
    RESTAURANT_UPDATE_FAILED("Restaurant update failed"),
    RESTAURANT_CREATOR_NOT_FOUND("Restaurant creator not found");

    private final String message;

    RestaurantErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
