package org.kamsystem.audit.repository;

public interface IAuditRepository {

    void updateAudit(Long oldKamId, Long newKamId, String entityType, Long entityId);
}
