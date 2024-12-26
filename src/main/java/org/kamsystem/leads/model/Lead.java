package org.kamsystem.leads.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lead {
    private Long id;
    private Long restaurantId;
    private Long creatorId;
    private LeadStatus status;
    private Date createdDate;
    private Date updatedDate;
}
