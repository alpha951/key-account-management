package org.kamsystem.restaurantpoc.service.access;

import java.util.List;
import org.kamsystem.restaurantpoc.model.Poc;

public interface PocAccessStrategy {
    List<Poc> getPocsByRestaurant(Long restaurantId, Long userId);
    Poc getPocById(Long pocId, Long userId);
}

