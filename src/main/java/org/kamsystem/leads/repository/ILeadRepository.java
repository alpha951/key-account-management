package org.kamsystem.leads.repository;

import java.util.List;
import org.kamsystem.leads.model.Lead;

public interface ILeadRepository {

    void createLead(Lead lead);

    void updateLeadStatus(Long leadId, String status);

    List<Lead> getLeadsByCreator(Long creatorId);

    List<Lead> getAllLeads();

    Lead getLeadById(Long leadId);

    Lead getLeadByIdAndCreator(Long leadId, Long creatorId);
}
