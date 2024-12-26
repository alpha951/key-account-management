package org.kamsystem.leads.service;

import java.util.List;
import org.kamsystem.leads.model.Lead;
import org.kamsystem.leads.model.UpdateLeadStatusRequest;

public interface ILeadService {

    void createLead(Lead lead);

    void updateLeadStatus(UpdateLeadStatusRequest request);

    List<Lead> getLeadsByCreator();

    List<Lead> getAllLeads();

    Lead getLeadById(Long leadId);
}
