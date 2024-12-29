package org.kamsystem.restaurantpoc.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.authentication.service.IAuthService;
import org.kamsystem.common.enums.UserRole;
import org.kamsystem.common.utils.MobileUtils;
import org.kamsystem.restaurantpoc.model.Poc;
import org.kamsystem.restaurantpoc.model.PocUpdateRequest;
import org.kamsystem.restaurantpoc.repository.IPocRepository;
import org.kamsystem.restaurantpoc.service.access.PocAccessStrategy;
import org.kamsystem.restaurantpoc.service.access.PocAccessStrategyFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PocService implements IPocService {

    private final IPocRepository pocRepository;
    private final IAuthService authService;
    private final PocAccessStrategyFactory strategyFactory;

    @Override
    public void createPoc(Poc poc) {
        String sanitizedContact = MobileUtils.sanitizeMobile(poc.getContact());
        Long userId = authService.getUserIdOfLoggedInUser();
        poc.setCreatedBy(userId);
        poc.setContact(sanitizedContact);
        pocRepository.createPoc(poc);
    }

    @Override
    public void updatePoc(PocUpdateRequest poc) {
        pocRepository.updatePoc(poc.getId(), poc.getName(),
            poc.getContact(), poc.getRole().getId());
    }

    @Override
    public List<Poc> getPocByRestaurant(Long restaurantId) {
        UserRole userRole = authService.getRoleOfLoggedInUser();
        Long userId = authService.getUserIdOfLoggedInUser();

        PocAccessStrategy strategy = strategyFactory.getStrategy(userRole);
        return strategy.getPocsByRestaurant(restaurantId, userId);
    }

    @Override
    public Poc getPocById(Long pocId) {
        UserRole userRole = authService.getRoleOfLoggedInUser();
        Long userId = authService.getUserIdOfLoggedInUser();

        PocAccessStrategy strategy = strategyFactory.getStrategy(userRole);
        return strategy.getPocById(pocId, userId);
    }
}
