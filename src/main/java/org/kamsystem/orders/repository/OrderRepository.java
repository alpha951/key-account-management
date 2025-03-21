package org.kamsystem.orders.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.kamsystem.common.exception.DatabaseOperationException;
import org.kamsystem.orders.enums.Currency;
import org.kamsystem.orders.enums.Offers;
import org.kamsystem.orders.enums.OrderStatus;
import org.kamsystem.orders.enums.PaymentMethod;
import org.kamsystem.orders.model.Order;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class OrderRepository implements IOrderRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CREATE_ORDER = "INSERT INTO orders (order_id, lead_id,"
        + " restaurant_id, interaction_id,"
        + " restaurant_order_id, amount, currency, cart_info, "
        + "shipping_info, offer_details, created_by, "
        + "payment_methods, remarks, order_status) VALUES "
        + "(:orderId, :leadId, :restaurantId, :interactionId, :restaurantOrderId,"
        + " :amount, :currency, :cartInfo, :shippingInfo, "
        + ":offerDetails, :createdBy, :paymentMethods, :remarks, :orderStatus)";

    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET order_status = :orderStatus WHERE order_id = :orderId";

    private static final String GET_ORDER_BY_ID = "SELECT"
        + " order_id, lead_id,"
        + " restaurant_id, interaction_id,"
        + " restaurant_order_id, amount, currency, cart_info, "
        + "shipping_info, offer_details, created_by, "
        + "payment_methods, remarks, order_status, created_at FROM orders WHERE order_id = :orderId";

    private static final String GET_ORDER_BY_RESTAURANT_ID = "SELECT"
        + " order_id, lead_id,"
        + " restaurant_id, interaction_id,"
        + " restaurant_order_id, amount, currency, cart_info, "
        + "shipping_info, offer_details, created_by, "
        + "payment_methods, remarks, order_status, created_at FROM orders WHERE restaurant_id = :restaurantId";


    @Override
    public void createOrder(Order order) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("orderId", order.getOrderId());
        parameterSource.addValue("leadId", order.getLeadId());
        parameterSource.addValue("restaurantId", order.getRestaurantId());
        parameterSource.addValue("interactionId", order.getInteractionId());
        parameterSource.addValue("restaurantOrderId", order.getRestaurantOrderId());
        parameterSource.addValue("amount", order.getOrderAmount());
        parameterSource.addValue("currency", order.getCurrency().name());
        parameterSource.addValue("cartInfo", order.getCartInfo());
        parameterSource.addValue("shippingInfo", order.getShippingInfo());
        parameterSource.addValue("offerDetails", order.getOffer().name());
        parameterSource.addValue("createdBy", order.getCreatedBy());
        parameterSource.addValue("paymentMethods", order.getPaymentMethod().name());
        parameterSource.addValue("remarks", order.getRemarks());
        parameterSource.addValue("orderStatus", order.getOrderStatus().name());
        namedParameterJdbcTemplate.update(CREATE_ORDER, parameterSource);
    }

    @Override
    public void updateOrderStatus(UUID orderId, String orderStatus) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("orderId", orderId);
        parameterSource.addValue("orderStatus", orderStatus);
        int rowsAffected = namedParameterJdbcTemplate.update(UPDATE_ORDER_STATUS, parameterSource);
        if (rowsAffected == 0) {
            throw new DatabaseOperationException("No order found with id: " + orderId);
        }
    }

    @Override
    public Order getOrderById(UUID orderId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("orderId", orderId);
        return namedParameterJdbcTemplate.queryForObject(GET_ORDER_BY_ID, parameterSource,
            (rs, rowNum) -> translateResultSetToOrder(rs));
    }

    @Override
    public List<Order> getOrdersByRestaurantId(Long restaurantId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("restaurantId", restaurantId);
        return namedParameterJdbcTemplate.query(GET_ORDER_BY_RESTAURANT_ID, parameterSource,
            (rs, rowNum) -> translateResultSetToOrder(rs));
    }

    private Order translateResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(UUID.fromString(rs.getString("order_id")));
        order.setLeadId(rs.getLong("lead_id"));
        order.setRestaurantId(rs.getLong("restaurant_id"));
        order.setInteractionId(rs.getLong("interaction_id"));
        order.setRestaurantOrderId(rs.getString("restaurant_order_id"));
        order.setOrderAmount(rs.getBigDecimal("amount"));
        order.setCurrency(Currency.valueOf(rs.getString("currency")));
        order.setCartInfo(rs.getString("cart_info"));
        order.setShippingInfo(rs.getString("shipping_info"));
        order.setOffer(rs.getString("offer_details") != null ?
            Offers.valueOf(rs.getString("offer_details")) : null);
        order.setCreatedBy(rs.getLong("created_by"));
        order.setPaymentMethod(PaymentMethod.valueOf(rs.getString("payment_methods")));
        order.setRemarks(rs.getString("remarks"));
        order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
        order.setCreatedAt(rs.getTimestamp("created_at"));
        return order;
    }
}
