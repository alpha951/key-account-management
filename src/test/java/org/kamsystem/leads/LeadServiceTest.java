package org.kamsystem.leads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kamsystem.authentication.service.IAuthService;
import org.kamsystem.common.enums.UserRole;
import org.kamsystem.leads.model.Lead;
import org.kamsystem.leads.model.LeadStatus;
import org.kamsystem.leads.model.UpdateLeadStatusRequest;
import org.kamsystem.leads.repository.ILeadRepository;
import org.kamsystem.leads.service.LeadService;
import org.kamsystem.leads.service.access.LeadAccessStrategy;
import org.kamsystem.leads.service.access.LeadAccessStrategyFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LeadServiceTest {

    @Mock
    private ILeadRepository leadRepository;

    @Mock
    private IAuthService authService;

    @Mock
    private LeadAccessStrategyFactory strategyFactory;

    @InjectMocks
    private LeadService leadService;

    @Test
    @DisplayName("Should create a lead successfully")
    void createLeadSuccessfully() {
        // Given
        Long mockUserId = 1L;
        Lead mockLead = new Lead();
        mockLead.setRestaurantId(101L);
        mockLead.setStatus(LeadStatus.NEW);

        when(authService.getUserIdOfLoggedInUser()).thenReturn(mockUserId);
        doNothing().when(leadRepository).createLead(mockLead);

        // When
        leadService.createLead(mockLead);

        // Then
        assertEquals(mockUserId, mockLead.getCreatorId());
        verify(authService).getUserIdOfLoggedInUser();
        verify(leadRepository).createLead(mockLead);
    }

    @Test
    @DisplayName("Should update lead status successfully")
    void updateLeadStatusSuccessfully() {
        // Given
        Long mockLeadId = 1L;
        LeadStatus newStatus = LeadStatus.PITCHED;
        UpdateLeadStatusRequest request = new UpdateLeadStatusRequest();
        request.setLeadId(mockLeadId);
        request.setStatus(newStatus);

        doNothing().when(leadRepository).updateLeadStatus(mockLeadId, newStatus.name());

        // When
        leadService.updateLeadStatus(request);

        // Then
        verify(leadRepository).updateLeadStatus(mockLeadId, newStatus.name());
    }

    @Test
    @DisplayName("Should fetch leads by creator ID")
    void getLeadsByCreatorSuccessfully() {
        // Given
        Long mockUserId = 1L;
        List<Lead> mockLeads = List.of(
            new Lead(1L, 101L, mockUserId, LeadStatus.NEW, new Date(), null),
            new Lead(2L, 102L, mockUserId, LeadStatus.IN_PROGRESS, new Date(), null)
        );

        when(authService.getUserIdOfLoggedInUser()).thenReturn(mockUserId);
        when(leadRepository.getLeadsByCreator(mockUserId)).thenReturn(mockLeads);

        // When
        List<Lead> fetchedLeads = leadService.getLeadsByCreator();

        // Then
        assertEquals(mockLeads, fetchedLeads);
        verify(authService).getUserIdOfLoggedInUser();
        verify(leadRepository).getLeadsByCreator(mockUserId);
    }

    @Test
    @DisplayName("Should fetch all leads")
    void getAllLeadsSuccessfully() {
        // Given
        List<Lead> mockLeads = List.of(
            new Lead(1L, 101L, 1L, LeadStatus.NEW, new Date(), null),
            new Lead(2L, 102L, 2L, LeadStatus.PITCHED, new Date(), null)
        );

        when(leadRepository.getAllLeads()).thenReturn(mockLeads);

        // When
        List<Lead> fetchedLeads = leadService.getAllLeads();

        // Then
        assertEquals(mockLeads, fetchedLeads);
        verify(leadRepository).getAllLeads();
    }

    @Test
    @DisplayName("Should fetch lead by ID using strategy")
    void getLeadByIdUsingStrategySuccessfully() {
        // Given
        Long mockLeadId = 1L;
        Long mockUserId = 1L;
        UserRole mockRole = UserRole.KEY_ACCOUNT_MANAGER;
        Lead mockLead = new Lead(mockLeadId, 101L, mockUserId, LeadStatus.NEW, new Date(), null);

        LeadAccessStrategy mockStrategy = mock(LeadAccessStrategy.class);

        when(authService.getRoleOfLoggedInUser()).thenReturn(mockRole);
        when(authService.getUserIdOfLoggedInUser()).thenReturn(mockUserId);
        when(strategyFactory.getStrategy(mockRole)).thenReturn(mockStrategy);
        when(mockStrategy.getLeadById(mockLeadId, mockUserId)).thenReturn(mockLead);

        // When
        Lead fetchedLead = leadService.getLeadById(mockLeadId);

        // Then
        assertEquals(mockLead, fetchedLead);
        verify(authService).getRoleOfLoggedInUser();
        verify(authService).getUserIdOfLoggedInUser();
        verify(strategyFactory).getStrategy(mockRole);
        verify(mockStrategy).getLeadById(mockLeadId, mockUserId);
    }
}
