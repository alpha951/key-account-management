package org.kamsystem.authentication.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.kamsystem.authentication.annotation.UserAuth;
import org.kamsystem.authentication.exception.UnauthorizedAccessException;
import org.kamsystem.common.enums.UserRole;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAuthAspect {

    @Pointcut("@annotation(userAuth)") // Intercepts methods annotated with @UserAuth
    public void userAuthPointcut(UserAuth userAuth) {
    }

    @Before("userAuthPointcut(userAuth)") // Runs before the annotated method is executed
    public void checkUserRole(UserAuth userAuth) throws UnauthorizedAccessException {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
            .toArray()[0].toString(); // Get the role as a String

        // Check if the current user has one of the allowed roles
        boolean isAuthorized = false;
        for (UserRole allowedRole : userAuth.allowedFor()) {
            if (allowedRole.name().equals(role)) {
                isAuthorized = true;
                break;
            }
        }

        if (!isAuthorized) {
            throw new UnauthorizedAccessException("User not authorized to access this resource.");
        }
    }
}
