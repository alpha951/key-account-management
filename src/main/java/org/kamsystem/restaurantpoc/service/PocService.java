package org.kamsystem.restaurantpoc.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.authentication.service.IAuthService;
import org.kamsystem.common.enums.UserRole;
import org.kamsystem.restaurantpoc.model.Poc;
import org.kamsystem.restaurantpoc.model.PocUpdateRequest;
import org.kamsystem.restaurantpoc.repository.IPocRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PocService implements IPocService {

    private final IPocRepository pocRepository;
    private final IAuthService authService;

    @Override
    public void createPoc(Poc poc) {
        pocRepository.createPoc(poc);
    }

    @Override
    public void updatePoc(PocUpdateRequest poc) {
        // TODO: createBy == user or super admin
        pocRepository.updatePoc(poc.getId(), poc.getName(),
            poc.getContact(), poc.getRole().name());
    }

    @Override
    public List<Poc> getPocByRestaurant(Long restaurantId) {
        UserRole userRole = authService.getRoleOfLoggedInUser();
        if (userRole.equals(UserRole.SUPER_ADMIN)) {
            return pocRepository.getPocsByRestaurant(restaurantId);
        }
        Long userId = authService.getUserIdOfLoggedInUser();
        return pocRepository.getPocsByRestaurantAndCreator(restaurantId, userId);
    }

    @Override
    public Poc getPocById(Long pocId) {
        UserRole userRole = authService.getRoleOfLoggedInUser();
        if (userRole.equals(UserRole.SUPER_ADMIN)) {
            return pocRepository.getPocById(pocId);
        }
        Long userId = authService.getUserIdOfLoggedInUser();
        return pocRepository.getPocByIdAndCreatorId(pocId, userId);
    }
}
