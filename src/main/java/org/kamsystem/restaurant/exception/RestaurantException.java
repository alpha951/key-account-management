package org.kamsystem.restaurant.exception;

import lombok.Getter;

@Getter
public class RestaurantException extends RuntimeException {

    private RestaurantErrorCode errorCode;

    public RestaurantException(RestaurantErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
