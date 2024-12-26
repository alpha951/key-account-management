package org.kamsystem.kamuser.service;

import lombok.AllArgsConstructor;
import org.kamsystem.authentication.utils.PasswordUtil;
import org.kamsystem.common.utils.MobileUtils;
import org.kamsystem.kamuser.dao.KamUser;
import org.kamsystem.kamuser.model.KamUserUpdateModel;
import org.kamsystem.kamuser.repository.IKamUserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KamUserService implements IKamUserService {

    private final IKamUserRepository kamUserRepository;

    @Override
    public void createUser(KamUser kamUser) {
        String sanitizedMobile = MobileUtils.sanitizeMobile(kamUser.getMobile());
        String hashedPassword = PasswordUtil.hashPassword(kamUser.getPassword());

        kamUserRepository.createUser(sanitizedMobile, kamUser.getName(), hashedPassword,
            kamUser.getRole().getId(), kamUser.getEmployeeId(), kamUser.getEmail(),
            kamUser.getIsActive());
    }

    @Override
    public void updateUserRole(KamUserUpdateModel kamUserUpdateModel) {
        kamUserRepository.updateUserRole(kamUserUpdateModel.getMobile(),
            kamUserUpdateModel.getRole().getId());
    }

    @Override
    public KamUser getUserByMobile(String mobile) {
        return kamUserRepository.getUserByMobile(mobile);
    }
}
