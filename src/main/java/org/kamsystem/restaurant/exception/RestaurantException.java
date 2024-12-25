package org.kamsystem.restaurant.exception;

public class RestaurantException extends RuntimeException {

    private RestaurantErrorCode errorCode;

    public RestaurantException(RestaurantErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
