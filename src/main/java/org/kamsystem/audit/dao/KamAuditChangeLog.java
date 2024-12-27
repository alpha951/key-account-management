package org.kamsystem.audit.dao;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.kamsystem.audit.model.EntityType;

@AllArgsConstructor
@Getter
@Setter
public class KamAuditChangeLog {

    private Long id;
    private Long oldKamId;
    private Long newKamId;
    private EntityType entityType;
    private Long entityId;
    private Date updatedOn;
}
