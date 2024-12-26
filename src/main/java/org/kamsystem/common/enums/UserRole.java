package org.kamsystem.common.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    SUPER_ADMIN(1),
    KEY_ACCOUNT_MANAGER(2);

    private final int id;

    UserRole(int id) {
        this.id = id;
    }

    public static UserRole getUserRoleById(int id) {
        for (UserRole userRole : values()) {
            if (userRole.getId() == id) {
                return userRole;
            }
        }
        return null;
    }
}
