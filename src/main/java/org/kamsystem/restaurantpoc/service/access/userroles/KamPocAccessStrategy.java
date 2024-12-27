package org.kamsystem.restaurantpoc.service.access.userroles;

import java.util.List;
import org.kamsystem.restaurantpoc.model.Poc;
import org.kamsystem.restaurantpoc.repository.IPocRepository;
import org.kamsystem.restaurantpoc.service.access.PocAccessStrategy;
import org.springframework.stereotype.Component;

@Component
public class KamPocAccessStrategy implements PocAccessStrategy {

    private final IPocRepository pocRepository;

    public KamPocAccessStrategy(IPocRepository pocRepository) {
        this.pocRepository = pocRepository;
    }

    @Override
    public List<Poc> getPocsByRestaurant(Long restaurantId, Long userId) {
        // KAM can only access POCs they created for the restaurant created by them
        return pocRepository.getPocsByRestaurantAndCreator(restaurantId, userId);
    }

    @Override
    public Poc getPocById(Long pocId, Long userId) {
        // KAM can only access POCs they created
        return pocRepository.getPocByIdAndCreatorId(pocId, userId);
    }
}
