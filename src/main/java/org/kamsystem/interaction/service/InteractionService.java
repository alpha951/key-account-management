package org.kamsystem.interaction.service;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.authentication.service.IAuthService;
import org.kamsystem.callscheduling.service.ICallScheduleService;
import org.kamsystem.interaction.model.Interaction;
import org.kamsystem.interaction.repository.IInteractionRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InteractionService implements IInteractionService {

    private final IInteractionRepository interactionRepository;
    private final IAuthService authService;
    private final ICallScheduleService callScheduleService;

    @Override
    public void createInteraction(Interaction interaction) {
        Long userId = authService.getUserIdOfLoggedInUser();
        interaction.setCallerId(userId);
        Long id = interactionRepository.createInteraction(interaction);
        if (id != null && interaction.getCallScheduleId() != null) {
            callScheduleService.updateLastCallDate(interaction.getCallScheduleId(), LocalDate.now());
        }
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
