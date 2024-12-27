package org.kamsystem.interaction.repository;

import java.util.List;
import org.kamsystem.interaction.model.Interaction;

public interface IInteractionRepository {

    Long createInteraction(Interaction interaction);

    List<Interaction> getInteractionByLeadId(Long leadId);

    List<Interaction> getInteractionByRestaurantId(Long restaurantId);

    List<Interaction> getInteractionByPocId(Long pocId);

    List<Interaction> getInteractionByCallScheduleId(Long callScheduleId);

    List<Interaction> getInteractionByKAMId(Long kamId);
}
