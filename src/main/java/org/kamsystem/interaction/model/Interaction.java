package org.kamsystem.interaction.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Interaction {

    private Long id;
    private Long callerId;

    @NonNull
    private Long leadId;
    private Long restaurantId;

    @NonNull
    private Long pocId;
    private Long callScheduleId;
    private Long callDuration;
    private String interactionDetails;
    private InteractionType interactionType;
    private Date createdAt;
}
