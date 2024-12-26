package org.kamsystem.restaurant.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.kamsystem.restaurant.exception.RestaurantErrorCode;
import org.kamsystem.restaurant.exception.RestaurantException;
import org.kamsystem.restaurant.model.Restaurant;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantRepository implements IRestaurantRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RestaurantRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final String INSERT_RESTAURANT = "INSERT INTO restaurant "
        + "(restaurant_name, pincode, city, state, address, created_by) VALUES (:name, :pincode, :city, :state, :address, :createdBy)";

    private static final String SELECT_RESTAURANT_BY_CREATOR = "SELECT id, restaurant_name, "
        + " pincode, city, state, address, created_by FROM restaurant WHERE created_by = :createdBy";

    private static final String SELECT_RESTAURANT_BY_ID = "SELECT id, restaurant_name, "
        + " pincode, city, state, address, created_by FROM restaurant WHERE id = :id";

    private static final String UPDATE_RESTAURANT = "UPDATE restaurant SET "
        + "restaurant_name = :name, pincode = :pincode, city = :city, state = :state, address = :address, updated_at = now() WHERE id = :id";

    @Override
    public void createRestaurant(Restaurant restaurant) {
        MapSqlParameterSource mapSqlParameterSource = formParamSource(restaurant);
        mapSqlParameterSource.addValue("createdBy", restaurant.getCreatedBy());
        try {
            namedParameterJdbcTemplate.update(INSERT_RESTAURANT, mapSqlParameterSource);
        } catch (Exception e) {
            throw new RestaurantException(RestaurantErrorCode.RESTAURANT_CREATION_FAILED, e.getMessage());
        }
    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        MapSqlParameterSource mapSqlParameterSource = formParamSource(restaurant);
        mapSqlParameterSource.addValue("id", restaurant.getId());
        try {
            namedParameterJdbcTemplate.update(UPDATE_RESTAURANT, mapSqlParameterSource);
        } catch (Exception e) {
            throw new RestaurantException(RestaurantErrorCode.RESTAURANT_UPDATE_FAILED, e.getMessage());
        }
    }

    @Override
    public List<Restaurant> getRestaurantByCreator(Long createdBy) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", createdBy);
        try {
            namedParameterJdbcTemplate.query(SELECT_RESTAURANT_BY_CREATOR, mapSqlParameterSource,
                (rs, rowNum) -> transformResultSetToRestaurant(rs));
        } catch (Exception e) {
            throw new RestaurantException(RestaurantErrorCode.RESTAURANT_CREATOR_NOT_FOUND, e.getMessage());
        }
        return List.of();
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        try {
            namedParameterJdbcTemplate.queryForObject(SELECT_RESTAURANT_BY_ID, mapSqlParameterSource,
                (rs, rowNum) -> transformResultSetToRestaurant(rs));
        } catch (Exception e) {
            throw new RestaurantException(RestaurantErrorCode.RESTAURANT_NOT_FOUND, e.getMessage());
        }
        return null;
    }

    private MapSqlParameterSource formParamSource(Restaurant restaurant) {
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        mapSqlParameterSource.addValue("name", restaurant.getName());
        mapSqlParameterSource.addValue("pincode", restaurant.getPincode());
        mapSqlParameterSource.addValue("city", restaurant.getCity());
        mapSqlParameterSource.addValue("state", restaurant.getState());
        mapSqlParameterSource.addValue("address", restaurant.getAddress());
        return mapSqlParameterSource;
    }

    private Restaurant transformResultSetToRestaurant(ResultSet rs) throws SQLException {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(rs.getLong("id"));
        restaurant.setName(rs.getString("restaurant_name"));
        restaurant.setPincode(rs.getString("pincode"));
        restaurant.setCity(rs.getString("city"));
        restaurant.setState(rs.getString("state"));
        restaurant.setAddress(rs.getString("address"));
        restaurant.setCreatedBy(rs.getLong("created_by"));
        return restaurant;
    }
}
