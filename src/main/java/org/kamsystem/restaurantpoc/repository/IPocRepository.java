package org.kamsystem.restaurantpoc.repository;

import java.util.List;
import org.kamsystem.restaurantpoc.model.Poc;

public interface IPocRepository {

    void createPoc(Poc poc);

    void updatePoc(Long id, String name, String contact, String role);

    List<Poc> getPocsByRestaurant(Long restaurantId);

    Poc getPocById(Long id);

    Poc getPocByIdAndCreatorId(Long pocId, Long createdBy);

    List<Poc> getPocsByRestaurantAndCreator(Long restaurantId, Long createdBy);
}
