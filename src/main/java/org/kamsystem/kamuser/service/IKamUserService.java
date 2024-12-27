package org.kamsystem.kamuser.service;

import org.kamsystem.kamuser.dao.KamUser;
import org.kamsystem.kamuser.model.KamUserUpdateModel;
import org.kamsystem.kamuser.model.UpdateKamRequest;
import org.kamsystem.kamuser.model.UpdateKamResponse;

public interface IKamUserService {

    void createUser(KamUser kamUser);

    void updateUserRole(KamUserUpdateModel kamUserUpdateModel);

    KamUser getUserByMobile(String mobile);

    UpdateKamResponse updateKam(UpdateKamRequest updateKamRequest);
}
