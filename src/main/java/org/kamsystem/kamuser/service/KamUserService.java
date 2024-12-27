package org.kamsystem.kamuser.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.authentication.utils.PasswordUtil;
import org.kamsystem.common.utils.MobileUtils;
import org.kamsystem.kamuser.dao.KamUser;
import org.kamsystem.kamuser.model.KamUserUpdateModel;
import org.kamsystem.kamuser.model.UpdateKamRequest;
import org.kamsystem.kamuser.model.UpdateKamResponse;
import org.kamsystem.kamuser.repository.IKamUserRepository;
import org.kamsystem.leads.model.Lead;
import org.kamsystem.leads.repository.ILeadRepository;
import org.kamsystem.restaurant.model.Restaurant;
import org.kamsystem.restaurant.repository.IRestaurantRepository;
import org.kamsystem.restaurantpoc.model.Poc;
import org.kamsystem.restaurantpoc.repository.IPocRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class KamUserService implements IKamUserService {

    private final IKamUserRepository kamUserRepository;
    private final ILeadRepository leadRepository;
    private final IRestaurantRepository restaurantRepository;
    private final IPocRepository pocRepository;

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
            kamUserUpdateModel.getRole().getId(), kamUserUpdateModel.getIsActive());
    }

    @Override
    public KamUser getUserByMobile(String mobile) {
        return kamUserRepository.getUserByMobile(mobile);
    }

    @Override
    @Transactional
    public UpdateKamResponse updateKam(UpdateKamRequest updateKamRequest) {
        List<Lead> leads = leadRepository.getLeadsByCreator(updateKamRequest.getOldKamId());
        List<Poc> pocs = pocRepository.getPocsByCreator(updateKamRequest.getOldKamId());
        List<Restaurant> restaurants = restaurantRepository.getRestaurantByCreator(updateKamRequest.getOldKamId());

        leadRepository.updateLeadCreator(updateKamRequest.getOldKamId(), updateKamRequest.getNewKamId());
        pocRepository.updatePocCreator(updateKamRequest.getOldKamId(), updateKamRequest.getNewKamId());
        restaurantRepository.updateRestaurantCreator(updateKamRequest.getOldKamId(), updateKamRequest.getNewKamId());

        return new UpdateKamResponse(leads.size(), pocs.size(), restaurants.size());
    }
}
