package org.kamsystem.authentication.service;

import org.kamsystem.authentication.model.UserLoginResponse;

public interface IAuthService {

    UserLoginResponse login(String mobile, String password);
}
