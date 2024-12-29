package org.kamsystem.authentication.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kamsystem.authentication.exception.AuthException;
import org.kamsystem.authentication.model.UserLoginRequest;
import org.kamsystem.authentication.model.UserLoginResponse;
import org.kamsystem.authentication.service.IAuthService;
import org.kamsystem.common.exception.InvalidRequestBodyException;
import org.kamsystem.common.model.ApiResponse;
import org.kamsystem.kamuser.service.IKamUserService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final IKamUserService kamUserService;
    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest userLoginRequest, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Validation error in login request: {}", result.getAllErrors());
            throw new InvalidRequestBodyException(result);
        }
        UserLoginResponse userLoginResponse = authService.login(userLoginRequest.getMobile(),
            userLoginRequest.getPassword());
        return new ResponseEntity<>(new ApiResponse<>(true, userLoginResponse), HttpStatus.OK);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuthException(AuthException e) {
        log.error("Authentication exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ApiResponse<>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(DataAccessException e) {
        log.error("Data access exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ApiResponse<>(false, "User not found"), HttpStatus.BAD_REQUEST);
    }
}
