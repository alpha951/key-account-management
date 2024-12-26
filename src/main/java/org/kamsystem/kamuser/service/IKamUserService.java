package org.kamsystem.kamuser.service;

import org.kamsystem.kamuser.dao.KamUser;
import org.kamsystem.kamuser.model.KamUserUpdateModel;

public interface IKamUserService {

    void createUser(KamUser kamUser);

    void updateUserRole(KamUserUpdateModel kamUserUpdateModel);

    KamUser getUserByMobile(String mobile);
}
