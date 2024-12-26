package org.kamsystem.leads.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class UpdateLeadStatusRequest {

    @NonNull
    private Long leadId;
    @NonNull
    private LeadStatus status;
}
