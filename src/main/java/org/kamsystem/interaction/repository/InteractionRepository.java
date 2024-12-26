package org.kamsystem.interaction.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.interaction.model.Interaction;
import org.kamsystem.interaction.model.InteractionType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InteractionRepository implements IInteractionRepository{
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CREATE_INTERACTION = "INSERT INTO interaction (caller_id, lead_id, "
        + "restaurant_id, poc_id, call_schedule_id, call_duration, interaction_details, interaction_type, "
        + "created_at) VALUES (:callerId, :leadId, :restaurantId, :pocId, :callScheduleId, :callDuration, :interactionDetails, :interactionType, :createdAt)";

    private static final String GET_INTERACTION_BY_LEAD_ID = "SELECT id, caller_id, "
        + "lead_id, restaurant_id, poc_id, call_schedule_id, call_duration,"
        + " interaction_details, interaction_type, created_at FROM interaction WHERE lead_id = :leadId";

    private static final String GET_INTERACTION_BY_RESTAURANT_ID = "SELECT id, caller_id, "
        + "lead_id, restaurant_id, poc_id, call_schedule_id, call_duration,"
        + " interaction_details, interaction_type, created_at "
        + "FROM interaction WHERE restaurant_id = :restaurantId";

    private static final String GET_INTERACTION_BY_POC_ID = "SELECT id, caller_id, "
        + "lead_id, restaurant_id, poc_id, call_schedule_id, call_duration,"
        + " interaction_details, interaction_type, created_at "
        + "FROM interaction WHERE poc_id = :pocId";

    private static final String GET_INTERACTION_BY_CALL_SCHEDULE_ID = "SELECT id, caller_id, "
        + "lead_id, restaurant_id, poc_id, call_schedule_id, call_duration,"
        + " interaction_details, interaction_type, created_at "
        + "FROM interaction WHERE call_schedule_id = :callScheduleId";

    private static final String GET_INTERACTION_BY_CALL_KAM_ID = "SELECT id, caller_id, "
        + "lead_id, restaurant_id, poc_id, call_schedule_id, call_duration,"
        + " interaction_details, interaction_type, created_at "
        + "FROM interaction WHERE caller_id = :callerId";


    @Override
    public void createInteraction(Interaction interaction) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("callerId", interaction.getCallerId());
        paramSource.addValue("leadId", interaction.getLeadId());
        paramSource.addValue("restaurantId", interaction.getRestaurantId());
        paramSource.addValue("pocId", interaction.getPocId());
        paramSource.addValue("callScheduleId", interaction.getCallScheduleId());
        paramSource.addValue("callDuration", interaction.getCallDuration());
        paramSource.addValue("interactionDetails", interaction.getInteractionDetails());
        paramSource.addValue("interactionType", interaction.getInteractionType());
        paramSource.addValue("createdAt", interaction.getCreatedAt());
        namedParameterJdbcTemplate.update(CREATE_INTERACTION, paramSource);
    }

    @Override
    public List<Interaction> getInteractionByLeadId(Long leadId) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("leadId", leadId);
        return namedParameterJdbcTemplate.query(GET_INTERACTION_BY_LEAD_ID, paramSource,
            (rs, rowNum) -> translateResultSetIntoInteraction(rs));
    }

    @Override
    public List<Interaction> getInteractionByRestaurantId(Long restaurantId) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("restaurantId", restaurantId);
        return namedParameterJdbcTemplate.query(GET_INTERACTION_BY_RESTAURANT_ID, paramSource,
            (rs, rowNum) -> translateResultSetIntoInteraction(rs));
    }

    @Override
    public List<Interaction> getInteractionByPocId(Long pocId) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("pocId", pocId);
        return namedParameterJdbcTemplate.query(GET_INTERACTION_BY_POC_ID, paramSource,
            (rs, rowNum) -> translateResultSetIntoInteraction(rs));
    }

    @Override
    public List<Interaction> getInteractionByCallScheduleId(Long callScheduleId) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("callScheduleId", callScheduleId);
        return namedParameterJdbcTemplate.query(GET_INTERACTION_BY_CALL_SCHEDULE_ID, paramSource,
            (rs, rowNum) -> translateResultSetIntoInteraction(rs));
    }

    @Override
    public List<Interaction> getInteractionByKAMId(Long kamId) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("callerId", kamId);
        return namedParameterJdbcTemplate.query(GET_INTERACTION_BY_CALL_KAM_ID, paramSource,
            (rs, rowNum) -> translateResultSetIntoInteraction(rs));
    }

    private Interaction translateResultSetIntoInteraction(ResultSet rs) throws SQLException {
        Interaction interaction = new Interaction();
        interaction.setId(rs.getLong("id"));
        interaction.setCallerId(rs.getLong("caller_id"));
        interaction.setLeadId(rs.getLong("lead_id"));
        interaction.setRestaurantId(rs.getLong("restaurant_id"));
        interaction.setPocId(rs.getLong("poc_id"));
        interaction.setCallScheduleId(rs.getLong("call_schedule_id"));
        interaction.setCallDuration(rs.getLong("call_duration"));
        interaction.setInteractionDetails(rs.getString("interaction_details"));
        interaction.setInteractionType(InteractionType.valueOf(rs.getString("interaction_type")));
        interaction.setCreatedAt(rs.getDate("created_at"));
        return interaction;
    }
}
