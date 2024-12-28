package org.kamsystem.authentication.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kamsystem.authentication.model.UserLoginRequest;
import org.kamsystem.authentication.model.UserLoginResponse;
import org.kamsystem.authentication.service.AuthService;
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
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest userLoginRequest, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Validation error in login request: {}", result.getAllErrors());
            return new ResponseEntity<>(new ApiResponse<>(false, result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        UserLoginResponse userLoginResponse = authService.login(userLoginRequest.getMobile(),
            userLoginRequest.getPassword());
        return new ResponseEntity<>(new ApiResponse<>(true, userLoginResponse), HttpStatus.OK);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(DataAccessException e) {
        log.error("Data access exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(new ApiResponse<>(false, "User not found"), HttpStatus.BAD_REQUEST);
    }
}
