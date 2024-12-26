package org.kamsystem.authentication.service;

import lombok.AllArgsConstructor;
import org.kamsystem.authentication.exception.AuthException;
import org.kamsystem.authentication.jwt.JwtTokenProvider;
import org.kamsystem.authentication.model.UserLoginResponse;
import org.kamsystem.authentication.utils.PasswordUtil;
import org.kamsystem.common.utils.MobileUtils;
import org.kamsystem.kamuser.dao.KamUser;
import org.kamsystem.kamuser.service.IKamUserService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final IKamUserService kamUserService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserLoginResponse login(String mobile, String password) {
        String sanitizedMobile = MobileUtils.sanitizeMobile(mobile);
        KamUser kamUser = kamUserService.getUserByMobile(sanitizedMobile);

        if (!PasswordUtil.matches(password, kamUser.getPassword())) {
            throw new AuthException("Invalid password");
        }

        if (!kamUser.getIsActive()) {
            throw new AuthException("User is not active");
        }

        String authToken = jwtTokenProvider.generateToken(kamUser.getMobile(), kamUser.getRole());
        return new UserLoginResponse(authToken);
    }
}
