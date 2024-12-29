package org.kamsystem.audit.repository;

public interface IAuditRepository {

    void insertAudit(Long oldKamId, Long newKamId, String entityType, Long entityId);
}
