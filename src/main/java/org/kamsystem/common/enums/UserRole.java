package org.kamsystem.common.enums;

public enum UserRole {

    SUPER_ADMIN(1),
    KEY_ACCOUNT_MANAGER(2);

    private final int id;

    UserRole(int id) {
        this.id = id;
    }
}
