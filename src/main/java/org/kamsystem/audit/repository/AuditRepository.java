package org.kamsystem.audit.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AuditRepository implements IAuditRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_CHANGE_LOG = "INSERT INTO audit_change_log "
        + "(old_kam_id, new_kam_id, entity_type, entity_id) VALUES (:oldKamId, :newKamId, :entityType, :entityId)";

    @Override
    public void insertAudit(Long oldKamId, Long newKamId, String entityType, Long entityId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("oldKamId", oldKamId);
        mapSqlParameterSource.addValue("newKamId", newKamId);
        mapSqlParameterSource.addValue("entityType", entityType);
        mapSqlParameterSource.addValue("entityId", entityId);
        namedParameterJdbcTemplate.update(INSERT_CHANGE_LOG, mapSqlParameterSource);
    }
}
