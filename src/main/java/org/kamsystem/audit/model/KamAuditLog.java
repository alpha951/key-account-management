package org.kamsystem.audit.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.kamsystem.leads.model.Lead;
import org.kamsystem.restaurant.model.Restaurant;
import org.kamsystem.restaurantpoc.model.Poc;

@AllArgsConstructor
@Getter
@Setter
public class KamAuditLog {

    private Long oldKamId;
    private Long newKamId;
    List<Lead> leadsAffected;
    List<Poc> pocsAffected;
    List<Restaurant> restaurantsAffected;
}
