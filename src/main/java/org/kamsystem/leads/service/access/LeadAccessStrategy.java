package org.kamsystem.leads.service.access;

import java.util.List;
import org.kamsystem.leads.model.Lead;

public interface LeadAccessStrategy {
    Lead getLeadById(Long leadId, Long userId);
    List<Lead> getLeads(Long userId);
}
