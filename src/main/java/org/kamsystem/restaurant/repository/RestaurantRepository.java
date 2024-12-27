package org.kamsystem.restaurant.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.kamsystem.restaurant.model.Restaurant;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class RestaurantRepository implements IRestaurantRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_RESTAURANT = "INSERT INTO restaurant "
        + "(restaurant_name, pincode, city, state, address, created_by) VALUES (:name, :pincode, :city, :state, :address, :createdBy)";

    private static final String SELECT_RESTAURANT_BY_CREATOR = "SELECT id, restaurant_name, "
        + " pincode, city, state, address, created_by, created_at, updated_at FROM restaurant WHERE created_by = :createdBy";

    private static final String SELECT_RESTAURANT_BY_ID = "SELECT id, restaurant_name, "
        + " pincode, city, state, address, created_by, created_at, updated_at FROM restaurant WHERE id = :id";

    private static final String UPDATE_RESTAURANT = "UPDATE restaurant SET "
        + "restaurant_name = :name, pincode = :pincode, city = :city, state = :state, address = :address, updated_at = now() WHERE id = :id";

    private static final String GET_ALL_RESTAURANTS = "SELECT id, restaurant_name, "
        + " pincode, city, state, address, created_by, created_at, updated_at FROM restaurant";

    private static final String GET_RESTAURANT_BY_RESTAURANT_ID_AND_CREATOR_ID = "SELECT id, "
        + "restaurant_name, "
        + " pincode, city, state, address, created_by, created_at, "
        + "updated_at FROM restaurant WHERE id = :id AND created_by = :createdBy";

    private static final String UPDATE_RESTAURANT_CREATOR = "UPDATE restaurant SET created_by = :newCreatedBy WHERE created_by = :oldCreatedBy";

    @Override
    public void createRestaurant(Restaurant restaurant) {
        MapSqlParameterSource mapSqlParameterSource = formParamSource(restaurant);
        mapSqlParameterSource.addValue("createdBy", restaurant.getCreatedBy());
        namedParameterJdbcTemplate.update(INSERT_RESTAURANT, mapSqlParameterSource);
    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        MapSqlParameterSource mapSqlParameterSource = formParamSource(restaurant);
        mapSqlParameterSource.addValue("id", restaurant.getId());
        namedParameterJdbcTemplate.update(UPDATE_RESTAURANT, mapSqlParameterSource);
    }

    @Override
    public List<Restaurant> getRestaurantByCreator(Long createdBy) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", createdBy);

        return namedParameterJdbcTemplate.query(SELECT_RESTAURANT_BY_CREATOR, mapSqlParameterSource,
            (rs, rowNum) -> transformResultSetToRestaurant(rs));
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(SELECT_RESTAURANT_BY_ID, mapSqlParameterSource,
            (rs, rowNum) -> transformResultSetToRestaurant(rs));
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        return namedParameterJdbcTemplate.query(GET_ALL_RESTAURANTS, mapSqlParameterSource,
            (rs, rowNum) -> transformResultSetToRestaurant(rs));
    }

    @Override
    public Restaurant getRestaurantByIdAndCreatorId(Long restaurantId, Long userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", restaurantId);
        mapSqlParameterSource.addValue("createdBy", userId);
        return namedParameterJdbcTemplate.queryForObject(GET_RESTAURANT_BY_RESTAURANT_ID_AND_CREATOR_ID,
            mapSqlParameterSource,
            (rs, rowNum) -> transformResultSetToRestaurant(rs));
    }

    @Override
    public void updateRestaurantCreator(Long oldCreatorId, Long newCreatorId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("oldCreatedBy", oldCreatorId);
        mapSqlParameterSource.addValue("newCreatedBy", newCreatorId);
        namedParameterJdbcTemplate.update(UPDATE_RESTAURANT_CREATOR,
            mapSqlParameterSource);
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
        restaurant.setCreatedAt(String.valueOf(rs.getTimestamp("created_at").toLocalDateTime()));
        restaurant.setUpdatedAt(String.valueOf(rs.getTimestamp("updated_at").toLocalDateTime()));
        return restaurant;
    }
}
