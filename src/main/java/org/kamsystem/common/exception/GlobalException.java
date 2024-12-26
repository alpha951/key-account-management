package org.kamsystem.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.kamsystem.common.constants.DtoConstants;
import org.kamsystem.common.model.ApiResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class GlobalException {

    /**
     * Handle all exceptions that are not handled in the controller or on the global level
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> processControllerException(HttpServletRequest request, Exception ex) {
        log.error("Global Exception Handler - Exception, url: {}", request.getRequestURL(), ex);
        ApiResponse<String> body = new ApiResponse<>(false, DtoConstants.GENERIC_ERROR_MSG);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Invalid URL - HTTP 404 Not Found
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<?> processControllerInvalidUrlException(HttpServletRequest request,
        NoHandlerFoundException ex) {
        log.info("Global Exception Handler - NoHandlerFoundException, url: {}, IP Address: {}, User Agent: {}",
            request.getRequestURL(), request.getHeader("X-Forwarded-For"), request.getHeader("User-Agent"), ex);
        ApiResponse<String> body = new ApiResponse<>(false, "Invalid request URL");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * If controller arguments (path variables & request params) are not compatible with request args & fails while
     * conversion
     */
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> processControllerInvalidMethodArgsException(HttpServletRequest request,
        MethodArgumentTypeMismatchException ex) {
        log.debug("Global Exception Handler - MethodArgumentTypeMismatchException, url: {}", request.getRequestURL(),
            ex);
        ApiResponse<String> body = new ApiResponse<>(false, "Invalid request");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Error if invalid JSON or failure while converting request JSON to java model
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<?> processControllerInvalidRequestException(HttpServletRequest request,
        HttpMessageNotReadableException ex) {
        log.debug("Global Exception Handler - HttpMessageNotReadableException, url: {}", request.getRequestURL(), ex);
        ApiResponse<String> body = new ApiResponse<>(false, "Invalid request body");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Invalid HTTP method for the end point
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> processControllerHttpMethodException(HttpServletRequest request,
        HttpRequestMethodNotSupportedException ex) {
        log.error("Global Exception Handler - UnsupportedHttpMethodException, url: {}", request.getRequestURL());
        ApiResponse<String> body = new ApiResponse<>(false, "Http method not allowed");
        return new ResponseEntity<>(body, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Database related exceptions
     */
    @ExceptionHandler(value = {DataAccessException.class})
    public ResponseEntity<?> processControllerDataAccessException(HttpServletRequest request, DataAccessException ex) {
        log.error("Global Exception Handler - DataAccessException, url: {}", request.getRequestURL(), ex);
        ApiResponse<String> body = new ApiResponse<>(false, DtoConstants.RESOURCE_NOT_FOUND);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
