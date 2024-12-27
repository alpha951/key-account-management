package org.kamsystem.restaurantpoc.service.access.userroles;

import java.util.List;
import org.kamsystem.restaurantpoc.model.Poc;
import org.kamsystem.restaurantpoc.repository.IPocRepository;
import org.kamsystem.restaurantpoc.service.access.PocAccessStrategy;
import org.springframework.stereotype.Component;

@Component
public class SuperAdminPocAccessStrategy implements PocAccessStrategy {

    private final IPocRepository pocRepository;

    public SuperAdminPocAccessStrategy(IPocRepository pocRepository) {
        this.pocRepository = pocRepository;
    }

    @Override
    public List<Poc> getPocsByRestaurant(Long restaurantId, Long userId) {
        // Super Admin can access all POCs
        return pocRepository.getPocsByRestaurant(restaurantId);
    }

    @Override
    public Poc getPocById(Long pocId, Long userId) {
        // Super Admin can access any POC
        return pocRepository.getPocById(pocId);
    }
}
