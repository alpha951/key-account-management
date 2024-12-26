package org.kamsystem.leads.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.authentication.service.IAuthService;
import org.kamsystem.common.enums.UserRole;
import org.kamsystem.leads.model.Lead;
import org.kamsystem.leads.model.UpdateLeadStatusRequest;
import org.kamsystem.leads.repository.ILeadRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LeadService implements ILeadService {

    private final ILeadRepository leadRepository;
    private final IAuthService authService;

    @Override
    public void createLead(Lead lead) {
        leadRepository.createLead(lead);
    }

    @Override
    public void updateLeadStatus(UpdateLeadStatusRequest request) {
        leadRepository.updateLeadStatus(request.getLeadId(), request.getStatus().name());
    }

    @Override
    public List<Lead> getLeadsByCreator() {
        Long userId = authService.getUserIdOfLoggedInUser();
        return leadRepository.getLeadsByCreator(userId);
    }

    @Override
    public List<Lead> getAllLeads() {
        return leadRepository.getAllLeads();
    }

    @Override
    public Lead getLeadById(Long leadId) {
        UserRole role = authService.getRoleOfLoggedInUser();
        if(role.equals(UserRole.SUPER_ADMIN)) {
            return leadRepository.getLeadById(leadId);
        }
        Long userId = authService.getUserIdOfLoggedInUser();
        return leadRepository.getLeadByIdAndCreator(leadId, userId);
    }
}
