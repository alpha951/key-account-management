package org.kamsystem.leads.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.leads.model.Lead;
import org.kamsystem.leads.model.LeadStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class LeadRepository implements ILeadRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CREATE_LEAD = "INSERT INTO lead (restaurant_id, created_by, lead_status)"
        + " VALUES (:restaurantId, :createdBy, :leadStatus)";

    private static final String UPDATE_LEAD_STATUS = "UPDATE lead SET lead_status = :leadStatus, updated_at = now() WHERE id = :leadId";

    private static final String GET_LEADS_BY_CREATOR = "SELECT lead_id,"
        + "restaurant_id, created_by, lead_status, created_at, updated_at FROM lead WHERE created_by = :creatorId";

    private static final String GET_ALL_LEADS = "SELECT lead_id, restaurant_id, created_by, lead_status, created_at, updated_at FROM lead";

    private static final String GET_LEAD_BY_ID = "SELECT lead_id, restaurant_id, created_by, lead_status, created_at,"
        + " updated_at FROM lead WHERE lead_id = :leadId";

    private static final String GET_LEAD_BY_ID_AND_CREATOR = "SELECT lead_id, restaurant_id, "
        + "created_by, lead_status, created_at, updated_at FROM lead WHERE lead_id = :leadId AND created_by = :creatorId";

    @Override
    public void createLead(Lead lead) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("restaurantId", lead.getRestaurantId());
        params.addValue("createdBy", lead.getCreatorId());
        params.addValue("leadStatus", lead.getStatus().name());
        namedParameterJdbcTemplate.update(CREATE_LEAD, params);
    }

    @Override
    public void updateLeadStatus(Long leadId, String status) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("leadId", leadId);
        params.addValue("leadStatus", status);
        namedParameterJdbcTemplate.update(UPDATE_LEAD_STATUS, params);
    }

    @Override
    public List<Lead> getLeadsByCreator(Long creatorId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("creatorId", creatorId);
        return namedParameterJdbcTemplate.query(GET_LEADS_BY_CREATOR, params,
            (rs, rowNum) -> translateResultSetToLead(rs));
    }

    @Override
    public List<Lead> getAllLeads() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        return namedParameterJdbcTemplate.query(GET_ALL_LEADS, params,
            (rs, rowNum) -> translateResultSetToLead(rs));
    }

    @Override
    public Lead getLeadById(Long leadId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("leadId", leadId);
        return namedParameterJdbcTemplate.queryForObject(GET_LEAD_BY_ID, params,
            (rs, rowNum) -> translateResultSetToLead(rs));
    }

    @Override
    public Lead getLeadByIdAndCreator(Long leadId, Long creatorId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("leadId", leadId);
        params.addValue("creatorId", creatorId);
        return namedParameterJdbcTemplate.queryForObject(GET_LEAD_BY_ID_AND_CREATOR, params,
            (rs, rowNum) -> translateResultSetToLead(rs));
    }

    private Lead translateResultSetToLead(ResultSet rs) throws SQLException {
        Lead lead = new Lead();
        lead.setId(rs.getLong("lead_id"));
        lead.setRestaurantId(rs.getLong("restaurant_id"));
        lead.setCreatorId(rs.getLong("created_by"));
        lead.setStatus(LeadStatus.valueOf(rs.getString("lead_status")));
        lead.setCreatedDate(rs.getTimestamp("created_at"));
        lead.setUpdatedDate(rs.getTimestamp("updated_at"));
        return lead;
    }
}
