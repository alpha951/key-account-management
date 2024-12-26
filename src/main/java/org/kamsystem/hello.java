package org.kamsystem;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.kamsystem.authentication.utils.PasswordUtil;

public class hello {

    public static void main(String[] args) {
        String pwd = PasswordUtil.hashPassword("password");
        System.out.printf(pwd);

        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
    }
}
