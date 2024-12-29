package org.kamsystem.authentication.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private final BCryptPasswordEncoder encoder;

    public PasswordUtil(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
