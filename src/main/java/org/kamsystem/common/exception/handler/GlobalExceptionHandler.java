package org.kamsystem.common.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.kamsystem.common.constants.DtoConstants;
import org.kamsystem.common.enums.ApiError;
import org.kamsystem.common.exception.DatabaseOperationException;
import org.kamsystem.common.exception.InvalidMobileException;
import org.kamsystem.common.exception.InvalidRequestBodyException;
import org.kamsystem.common.model.ApiResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handle all exceptions that are not handled in the controller or on the global level
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> processControllerException(HttpServletRequest request, Exception ex) {
        log.error("Global Exception Handler - Exception, url: {}, message: {}, trace : {}", request.getRequestURL(),
            DtoConstants.GENERIC_ERROR_MSG, ex.getStackTrace(), ex);
        ApiResponse<String> body = new ApiResponse<>(ApiError.SERVER_ISSUE.getCode(), Map.of(
            DtoConstants.ERROR_MESSAGE_KEY,
            DtoConstants.GENERIC_ERROR_MSG));
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Invalid URL - HTTP 404 Not Found
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<?> processControllerInvalidUrlException(HttpServletRequest request,
        NoHandlerFoundException ex) {
        log.info(
            "Global Exception Handler - NoHandlerFoundException, Invalid request URL : {}, IP Address: {}, User Agent: {}",
            request.getRequestURL(), request.getHeader("X-Forwarded-For"), request.getHeader("User-Agent"), ex);
        ApiResponse<String> body = new ApiResponse<>(ApiError.INVALID_REQUEST.getCode(), Map.of(
            DtoConstants.ERROR_MESSAGE_KEY,
            DtoConstants.RESOURCE_NOT_FOUND));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * If controller arguments (path variables & request params) are not compatible with request args & fails while
     * conversion
     */
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> processControllerInvalidMethodArgsException(HttpServletRequest request,
        MethodArgumentTypeMismatchException ex) {
        log.debug(
            "Global Exception Handler - MethodArgumentTypeMismatchException, url: {}, Invalid request, trace : {}",
            request.getRequestURL(), ex.getStackTrace(),
            ex);
        ApiResponse<String> body = new ApiResponse<>(ApiError.INVALID_REQUEST_PARAMS.getCode(), Map.of(
            DtoConstants.ERROR_MESSAGE_KEY,
            DtoConstants.INVALID_REQUEST_ARGUMENTS));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Error if invalid JSON or failure while converting request JSON to java model
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<?> processControllerInvalidRequestException(HttpServletRequest request,
        HttpMessageNotReadableException ex) {
        log.debug(
            "Global Exception Handler - HttpMessageNotReadableException, url: {}, Invalid request body, trace : {}",
            request.getRequestURL(), ex.getStackTrace(), ex);
        ApiResponse<String> body = new ApiResponse<>(ApiError.INVALID_REQUEST_BODY.getCode(), Map.of(
            DtoConstants.ERROR_MESSAGE_KEY,
            DtoConstants.JSON_PARSING_ERROR));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Invalid HTTP method for the end point
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> processControllerHttpMethodException(HttpServletRequest request,
        HttpRequestMethodNotSupportedException ex) {
        log.error("Global Exception Handler - UnsupportedHttpMethodException, url: {}, Http method not allowed",
            request.getRequestURL());
        ApiResponse<String> body = new ApiResponse<>(ApiError.INVALID_REQUEST.getCode(), Map.of(
            DtoConstants.ERROR_MESSAGE_KEY,
            DtoConstants.METHOD_NOT_ALLOWED));
        return new ResponseEntity<>(body, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Database related exceptions
     */
    @ExceptionHandler(value = {DataAccessException.class})
    public ResponseEntity<?> processControllerDataAccessException(HttpServletRequest request, DataAccessException ex) {
        log.error(" Exception Handler - DataAccessException, url: {}, message: {} , trace: {}", request.getRequestURL(),
            DtoConstants.RESOURCE_NOT_FOUND, ex.getStackTrace(), ex);
        ApiResponse<String> body = new ApiResponse<>(ApiError.RESOURCE_NOT_FOUND.getCode(), Map.of(
            DtoConstants.ERROR_MESSAGE_KEY,
            DtoConstants.RESOURCE_NOT_FOUND));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BadSqlGrammarException.class})
    public ResponseEntity<?> processControllerBadSqlGrammarException(HttpServletRequest request,
        BadSqlGrammarException ex) {
        log.error(" Exception Handler - BadSqlGrammarException, url: {}, message: {}, trace: {}",
            request.getRequestURL(),
            DtoConstants.RESOURCE_NOT_FOUND, ex.getStackTrace(), ex);

        ApiResponse<String> body = new ApiResponse<>(ApiError.SERVER_ISSUE.getCode(),
            Map.of(DtoConstants.ERROR_MESSAGE_KEY,
                DtoConstants.GENERIC_ERROR_MSG));
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<ApiResponse<?>> handleDatabaseOperationException(DatabaseOperationException ex) {
        log.error("Global Exception Handler - DatabaseOperationException, message: {}, trace: {}",
            ex.getMessage(), ex.getStackTrace(), ex);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponse<>(ApiError.SERVER_ISSUE.getCode(), Map.of(
                DtoConstants.ERROR_MESSAGE_KEY,
                ex.getMessage()
            )));
    }

    /**
     * Custom exception for invalid mobile number that does not match the pattern
     */
    @ExceptionHandler(value = {InvalidMobileException.class})
    public ResponseEntity<?> processInvalidMobileException(HttpServletRequest request, InvalidMobileException ex) {
        log.error("Global Exception Handler - InvalidMobileException, url: {}, message: {}, trace : {}",
            request.getRequestURL(),
            ex.getMessage(), ex.getStackTrace(), ex);
        Map<String, String> errors = new HashMap<>();
        errors.put(DtoConstants.ERROR_MESSAGE_KEY, ex.getMessage());
        ApiResponse<String> body = new ApiResponse<>(ApiError.INVALID_MOBILE.getCode(), errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestBodyException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidRequestBody(InvalidRequestBodyException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ApiResponse<>(ApiError.INVALID_REQUEST_BODY.getCode(), errors));
    }
}
