package org.kamsystem.audit.service;

import lombok.AllArgsConstructor;
import org.kamsystem.audit.model.EntityType;
import org.kamsystem.audit.model.KamAuditLog;
import org.kamsystem.audit.repository.IAuditRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuditService implements IAuditService {

    private final IAuditRepository auditRepository;

    /*
        This method logs the changes made to the KAM entity.
     */
    @Override
    public void logKamChange(KamAuditLog kamAuditLog) {
        Long oldKamId = kamAuditLog.getOldKamId();
        Long newKamId = kamAuditLog.getNewKamId();
        kamAuditLog.getLeadsAffected().forEach(lead -> {
            auditRepository.updateAudit(oldKamId, newKamId,
                EntityType.LEAD.name(), lead.getId());
        });

        kamAuditLog.getPocsAffected().forEach(poc -> {
            auditRepository.updateAudit(oldKamId, newKamId,
                EntityType.POC.name(), poc.getId());
        });

        kamAuditLog.getRestaurantsAffected().forEach(restaurant -> {
            auditRepository.updateAudit(oldKamId, newKamId,
                EntityType.RESTAURANT.name(), restaurant.getId());
        });
    }
}
