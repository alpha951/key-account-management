package org.kamsystem.authentication.service;

import org.kamsystem.authentication.model.UserLoginResponse;
import org.kamsystem.common.enums.UserRole;

public interface IAuthService {

    UserLoginResponse login(String mobile, String password);

    Long getUserIdOfLoggedInUser();

    UserRole getRoleOfLoggedInUser();
}
