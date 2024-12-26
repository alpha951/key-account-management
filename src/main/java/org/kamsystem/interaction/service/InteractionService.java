package org.kamsystem.interaction.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.authentication.service.IAuthService;
import org.kamsystem.interaction.model.Interaction;
import org.kamsystem.interaction.repository.IInteractionRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InteractionService implements IInteractionService {

    private final IInteractionRepository interactionRepository;
    private final IAuthService authService;

    @Override
    public void createInteraction(Interaction interaction) {
        Long userId = authService.getUserIdOfLoggedInUser();
        interaction.setCallerId(userId);
        interactionRepository.createInteraction(interaction);
    }

    @Override
    public List<Interaction> getInteractionByLeadId(Long leadId) {
        return interactionRepository.getInteractionByLeadId(leadId);
    }

    @Override
    public List<Interaction> getInteractionByRestaurantId(Long restaurantId) {
        return interactionRepository.getInteractionByRestaurantId(restaurantId);
    }

    @Override
    public List<Interaction> getInteractionByPocId(Long pocId) {
        return interactionRepository.getInteractionByPocId(pocId);
    }

    @Override
    public List<Interaction> getInteractionByCallScheduleId(Long callScheduleId) {
        return interactionRepository.getInteractionByCallScheduleId(callScheduleId);
    }

    @Override
    public List<Interaction> getInteractionByKAMId(Long kamId) {
        return interactionRepository.getInteractionByKAMId(kamId);
    }
}
