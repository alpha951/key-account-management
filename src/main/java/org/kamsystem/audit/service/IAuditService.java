package org.kamsystem.audit.service;

import org.kamsystem.audit.model.KamAuditLog;

public interface IAuditService {
    void logKamChange(KamAuditLog kamAuditLog);
}
