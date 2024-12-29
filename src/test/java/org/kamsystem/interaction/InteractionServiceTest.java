package org.kamsystem.interaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kamsystem.authentication.service.IAuthService;
import org.kamsystem.callscheduling.service.ICallScheduleService;
import org.kamsystem.interaction.model.Interaction;
import org.kamsystem.interaction.model.InteractionType;
import org.kamsystem.interaction.repository.IInteractionRepository;
import org.kamsystem.interaction.service.InteractionService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InteractionServiceTest {

    @Mock
    private IInteractionRepository interactionRepository;

    @Mock
    private IAuthService authService;

    @Mock
    private ICallScheduleService callScheduleService;

    @InjectMocks
    private InteractionService interactionService;

    @Test
    @DisplayName("Should create interaction successfully and update call schedule if applicable")
    void createInteractionSuccessfully_WithCallScheduleUpdate() {
        // Given
        Long mockUserId = 1L;
        Long mockCallScheduleId = 100L;
        Interaction mockInteraction = new Interaction();
        mockInteraction.setLeadId(10L);
        mockInteraction.setPocId(20L);
        mockInteraction.setCallScheduleId(mockCallScheduleId);
        mockInteraction.setInteractionType(InteractionType.ISSUE_RESOLUTION);

        when(authService.getUserIdOfLoggedInUser()).thenReturn(mockUserId);
        when(interactionRepository.createInteraction(mockInteraction)).thenReturn(1L);
        doNothing().when(callScheduleService).updateLastCallDate(mockCallScheduleId, LocalDate.now());

        // When
        interactionService.createInteraction(mockInteraction);

        // Then
        assertEquals(mockUserId, mockInteraction.getCallerId());
        verify(authService).getUserIdOfLoggedInUser();
        verify(interactionRepository).createInteraction(mockInteraction);
        verify(callScheduleService).updateLastCallDate(mockCallScheduleId, LocalDate.now());
    }

    @Test
    @DisplayName("Should create interaction without updating call schedule")
    void createInteractionSuccessfully_WithoutCallScheduleUpdate() {
        // Given
        Long mockUserId = 1L;
        Interaction mockInteraction = new Interaction();
        mockInteraction.setLeadId(10L);
        mockInteraction.setPocId(20L);
        mockInteraction.setInteractionType(InteractionType.CONTRACT_REVIEW);

        when(authService.getUserIdOfLoggedInUser()).thenReturn(mockUserId);
        when(interactionRepository.createInteraction(mockInteraction)).thenReturn(1L);

        // When
        interactionService.createInteraction(mockInteraction);

        // Then
        assertEquals(mockUserId, mockInteraction.getCallerId());
        verify(authService).getUserIdOfLoggedInUser();
        verify(interactionRepository).createInteraction(mockInteraction);
        verify(callScheduleService, never()).updateLastCallDate(anyLong(), any());
    }

    @Test
    @DisplayName("Should fetch interactions by lead ID")
    void getInteractionsByLeadIdSuccessfully() {
        // Given
        Long leadId = 10L;
        List<Interaction> mockInteractions = List.of(
            new Interaction(1L, 1L, leadId, 100L, 20L, null, 60L, "Details 1", InteractionType.DISCOVERY_CALL, new Date()),
            new Interaction(2L, 1L, leadId, 100L, 20L, null, 120L, "Details 2", InteractionType.FOLLOW_UP_CALL, new Date())
        );

        when(interactionRepository.getInteractionByLeadId(leadId)).thenReturn(mockInteractions);

        // When
        List<Interaction> fetchedInteractions = interactionService.getInteractionByLeadId(leadId);

        // Then
        assertEquals(mockInteractions, fetchedInteractions);
        verify(interactionRepository).getInteractionByLeadId(leadId);
    }

    @Test
    @DisplayName("Should fetch interactions by restaurant ID")
    void getInteractionsByRestaurantIdSuccessfully() {
        // Given
        Long restaurantId = 100L;
        List<Interaction> mockInteractions = List.of(
            new Interaction(1L, 1L, 10L, restaurantId, 20L, null, 60L, "Details 1", InteractionType.PRICING_NEGOTIATION, new Date())
        );

        when(interactionRepository.getInteractionByRestaurantId(restaurantId)).thenReturn(mockInteractions);

        // When
        List<Interaction> fetchedInteractions = interactionService.getInteractionByRestaurantId(restaurantId);

        // Then
        assertEquals(mockInteractions, fetchedInteractions);
        verify(interactionRepository).getInteractionByRestaurantId(restaurantId);
    }

    @Test
    @DisplayName("Should fetch interactions by POC ID")
    void getInteractionsByPocIdSuccessfully() {
        // Given
        Long pocId = 20L;
        List<Interaction> mockInteractions = List.of(
            new Interaction(1L, 1L, 10L, 100L, pocId, null, 60L, "Details 1", InteractionType.ORDER_PLACED
                , new Date())
        );

        when(interactionRepository.getInteractionByPocId(pocId)).thenReturn(mockInteractions);

        // When
        List<Interaction> fetchedInteractions = interactionService.getInteractionByPocId(pocId);

        // Then
        assertEquals(mockInteractions, fetchedInteractions);
        verify(interactionRepository).getInteractionByPocId(pocId);
    }

    @Test
    @DisplayName("Should fetch interactions by call schedule ID")
    void getInteractionsByCallScheduleIdSuccessfully() {
        // Given
        Long callScheduleId = 100L;
        List<Interaction> mockInteractions = List.of(
            new Interaction(1L, 1L, 10L, 100L, 20L, callScheduleId, 60L, "Details 1", InteractionType.PLATFORM_DEMO, new Date())
        );

        when(interactionRepository.getInteractionByCallScheduleId(callScheduleId)).thenReturn(mockInteractions);

        // When
        List<Interaction> fetchedInteractions = interactionService.getInteractionByCallScheduleId(callScheduleId);

        // Then
        assertEquals(mockInteractions, fetchedInteractions);
        verify(interactionRepository).getInteractionByCallScheduleId(callScheduleId);
    }

    @Test
    @DisplayName("Should fetch interactions by KAM ID")
    void getInteractionsByKamIdSuccessfully() {
        // Given
        Long kamId = 1L;
        List<Interaction> mockInteractions = List.of(
            new Interaction(1L, kamId, 10L, 100L, 20L, null, 60L, "Details 1", InteractionType.FOLLOW_UP_CALL, new Date())
        );

        when(interactionRepository.getInteractionByKAMId(kamId)).thenReturn(mockInteractions);

        // When
        List<Interaction> fetchedInteractions = interactionService.getInteractionByKAMId(kamId);

        // Then
        assertEquals(mockInteractions, fetchedInteractions);
        verify(interactionRepository).getInteractionByKAMId(kamId);
    }
}
