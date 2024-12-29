package org.kamsystem.leads.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLeadStatusRequest {

    @NonNull
    private Long leadId;
    @NonNull
    private LeadStatus status;
}
