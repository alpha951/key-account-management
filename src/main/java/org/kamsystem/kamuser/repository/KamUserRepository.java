package org.kamsystem.kamuser.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.kamsystem.common.enums.UserRole;
import org.kamsystem.common.exception.DatabaseOperationException;
import org.kamsystem.kamuser.dao.KamUser;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class KamUserRepository implements IKamUserRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CREATE_KAM_USER = "INSERT INTO kam_user (mobile, name, password, role, employee_id, email, is_active) VALUES (:mobile, :name, :password, :role, :employeeId, :email, :isActive)";

    private static final String UPDATE_USER_ROLE = "UPDATE kam_user SET role = :role, is_active = :isActive WHERE mobile = :mobile";

    private static final String GET_USER_BY_MOBILE = "SELECT id, mobile, "
        + "name, password, role, employee_id, email, is_active FROM kam_user WHERE mobile = :mobile";

    @Override
    public void createUser(String mobile, String name, String password, int role, String employeeId, String email,
        Boolean isActive) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("mobile", mobile);
        mapSqlParameterSource.addValue("name", name);
        mapSqlParameterSource.addValue("password", password);
        mapSqlParameterSource.addValue("role", role);
        mapSqlParameterSource.addValue("employeeId", employeeId);
        mapSqlParameterSource.addValue("email", email);
        mapSqlParameterSource.addValue("isActive", isActive);
        namedParameterJdbcTemplate.update(CREATE_KAM_USER, mapSqlParameterSource);
    }

    @Override
    public void updateUserRole(String mobile, int role, boolean isActive) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("mobile", mobile);
        mapSqlParameterSource.addValue("role", role);
        mapSqlParameterSource.addValue("isActive", isActive);
        int rowsAffected = namedParameterJdbcTemplate.update(UPDATE_USER_ROLE, mapSqlParameterSource);
        if (rowsAffected == 0) {
            throw new DatabaseOperationException("No user found with mobile: " + mobile);
        }
    }

    @Override
    public KamUser getUserByMobile(String mobile) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("mobile", mobile);
        return namedParameterJdbcTemplate.queryForObject(GET_USER_BY_MOBILE, mapSqlParameterSource,
            (rs, rowNum) -> transformResultSetToKamUser(rs));
    }

    private KamUser transformResultSetToKamUser(ResultSet rs) throws SQLException {
        KamUser kamUser = new KamUser();
        kamUser.setId(rs.getLong("id"));
        kamUser.setMobile(rs.getString("mobile"));
        kamUser.setName(rs.getString("name"));
        kamUser.setPassword(rs.getString("password"));
        kamUser.setRole(Objects.requireNonNull(UserRole.getUserRoleById(rs.getInt("role"))));
        kamUser.setEmployeeId(rs.getString("employee_id"));
        kamUser.setEmail(rs.getString("email"));
        kamUser.setIsActive(rs.getBoolean("is_active"));
        return kamUser;
    }
}
