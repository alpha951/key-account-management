package org.kamsystem.restaurantpoc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.kamsystem.restaurantpoc.model.Poc;
import org.kamsystem.restaurantpoc.model.PocRole;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PocRepository implements IPocRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CREATE_POC =
        "INSERT INTO restaurant_poc (name, restaurant_id, contact, poc_role, created_by) VALUES (:name, :restaurant_id, :contact,"
            + " :role, :created_by)";

    private static final String UPDATE_POC =
        "UPDATE restaurant_poc SET name = :name, contact = :contact, poc_role = :role WHERE poc_id = :id";

    private static final String GET_POC_BY_RESTAURANT =
        "SELECT poc_id, name, restaurant_id,"
            + "poc_role, contact, created_by, created_at, updated_at"
            + " FROM restaurant_poc WHERE restaurant_id = :restaurant_id";

    private static final String GET_POC_BY_ID =
        "SELECT poc_id, name, restaurant_id,"
            + "poc_role, contact, created_by, created_at, updated_at"
            + " FROM restaurant_poc WHERE poc_id = :id";

    private static final String GET_POC_BY_ID_AND_CREATED_BY =
        "SELECT poc_id, name, restaurant_id,"
            + "poc_role, contact, created_by, created_at, updated_at"
            + " FROM restaurant_poc WHERE poc_id = :id AND created_by = :created_by";

    private static final String GET_POC_BY_RESTRAUNT_AND_CREATED_BY =
        "SELECT rp.poc_id, rp.name, rp.restaurant_id,"
            + "rp.poc_role, rp.contact, rp.created_by, rp.created_at, rp.updated_at"
            + " FROM restaurant_poc rp join restaurant rt on rp.restaurant_id = rt.id "
            + "WHERE rp.restaurant_id = :restaurant_id AND rp.created_by = :created_by";

    private static final String GET_POC_BY_CREATOR = "SELECT poc_id, name, restaurant_id,"
            + "poc_role, contact, created_by, created_at, updated_at"
            + " FROM restaurant_poc WHERE created_by = :createdBy";

    private static final String UPDATE_POC_CREATOR = "UPDATE restaurant_poc SET created_by = :newCreatedBy WHERE created_by = :oldCreatedBy";

    @Override
    public void createPoc(Poc poc) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("name", poc.getName());
        paramSource.addValue("restaurant_id", poc.getRestaurantId());
        paramSource.addValue("contact", poc.getContact());
        paramSource.addValue("role", poc.getPocRole().getId());
        paramSource.addValue("created_by", poc.getCreatedBy());
        namedParameterJdbcTemplate.update(CREATE_POC, paramSource);
    }

    public void updatePoc(Long id, String name, String contact, int role) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);
        paramSource.addValue("name", name);
        paramSource.addValue("contact", contact);
        paramSource.addValue("role", role);
        namedParameterJdbcTemplate.update(UPDATE_POC, paramSource);
    }

    @Override
    public List<Poc> getPocsByRestaurant(Long restaurantId) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("restaurant_id", restaurantId);
        return namedParameterJdbcTemplate.query(GET_POC_BY_RESTAURANT, paramSource,
            (rs, rowNum) -> translateResultSetToPoc(rs));
    }

    @Override
    public Poc getPocById(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(GET_POC_BY_ID, paramSource,
            (rs, rowNum) -> translateResultSetToPoc(rs));
    }

    @Override
    public Poc getPocByIdAndCreatorId(Long pocId, Long createdBy) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", pocId);
        paramSource.addValue("created_by", createdBy);
        return namedParameterJdbcTemplate.queryForObject(GET_POC_BY_ID_AND_CREATED_BY, paramSource,
            (rs, rowNum) -> translateResultSetToPoc(rs));
    }

    @Override
    public List<Poc> getPocsByRestaurantAndCreator(Long restaurantId, Long createdBy) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("restaurant_id", restaurantId);
        paramSource.addValue("created_by", createdBy);
        return namedParameterJdbcTemplate.query(GET_POC_BY_RESTRAUNT_AND_CREATED_BY, paramSource,
            (rs, rowNum) -> translateResultSetToPoc(rs));
    }

    @Override
    public List<Poc> getPocsByCreator(Long createdBy) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("createdBy", createdBy);
        return namedParameterJdbcTemplate.query(GET_POC_BY_CREATOR, paramSource,
            (rs, rowNum) -> translateResultSetToPoc(rs));
    }

    @Override
    public void updatePocCreator(Long oldCreatorId, Long newCreatorId) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("oldCreatedBy", oldCreatorId);
        paramSource.addValue("newCreatedBy", newCreatorId);
        namedParameterJdbcTemplate.update(UPDATE_POC_CREATOR, paramSource);
    }

    private Poc translateResultSetToPoc(ResultSet rs) throws SQLException {
        Poc poc = new Poc();
        poc.setId(rs.getLong("poc_id"));
        poc.setName(rs.getString("name"));
        poc.setRestaurantId(rs.getLong("restaurant_id"));
        poc.setPocRole(Objects.requireNonNull(PocRole.getById(rs.getInt("poc_role"))));
        poc.setContact(rs.getString("contact"));
        poc.setCreatedBy(rs.getLong("created_by"));
        poc.setCreatedAt(rs.getTimestamp("created_at"));
        poc.setUpdateAt(rs.getTimestamp("updated_at"));
        return poc;
    }
}
