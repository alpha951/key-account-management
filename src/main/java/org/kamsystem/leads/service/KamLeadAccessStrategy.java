package org.kamsystem.leads.service;

import java.util.List;
import org.kamsystem.leads.model.Lead;
import org.kamsystem.leads.repository.ILeadRepository;
import org.springframework.stereotype.Component;

@Component
public class KamLeadAccessStrategy implements LeadAccessStrategy {

    private final ILeadRepository leadRepository;

    public KamLeadAccessStrategy(ILeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    @Override
    public Lead getLeadById(Long leadId, Long userId) {
        // KAM can only access their own leads
        return leadRepository.getLeadByIdAndCreator(leadId, userId);
    }

    @Override
    public List<Lead> getLeads(Long userId) {
        // KAM can only see leads they created
        return leadRepository.getLeadsByCreator(userId);
    }
}
