package org.kamsystem.restaurantpoc.service;

import java.util.List;
import org.kamsystem.restaurantpoc.model.Poc;
import org.kamsystem.restaurantpoc.model.PocUpdateRequest;

public interface IPocService {

    void createPoc(Poc poc);

    void updatePoc(PocUpdateRequest poc);

    List<Poc> getPocByRestaurant(Long restaurantId);

    Poc getPocById(Long pocId);
}
