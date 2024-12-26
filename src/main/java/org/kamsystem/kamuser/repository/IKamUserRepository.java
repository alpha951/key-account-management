package org.kamsystem.kamuser.repository;

import org.kamsystem.kamuser.dao.KamUser;

public interface IKamUserRepository {

    void createUser(String mobile, String name, String password,
        int role, String employeeId, String email,
        Boolean isActive);

    void updateUserRole(String mobile, int role);

    KamUser getUserByMobile(String mobile);
}
