package org.kamsystem.leads.service.access.userroles;

import java.util.List;
import org.kamsystem.leads.model.Lead;
import org.kamsystem.leads.repository.ILeadRepository;
import org.kamsystem.leads.service.access.LeadAccessStrategy;
import org.springframework.stereotype.Component;

@Component
public class SuperAdminLeadAccessStrategy implements LeadAccessStrategy {

    private final ILeadRepository leadRepository;

    public SuperAdminLeadAccessStrategy(ILeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Override
    public Lead getLeadById(Long leadId, Long userId) {
        // Super Admin can access any lead
        return leadRepository.getLeadById(leadId);
    }

    @Override
    public List<Lead> getLeads(Long userId) {
        // Super Admin can see all leads
        return leadRepository.getAllLeads();
    }
}
