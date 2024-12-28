package org.kamsystem.orders.repository;

import java.util.List;
import java.util.UUID;
import org.kamsystem.orders.model.Order;

public interface IOrderRepository {

    void createOrder(Order order);

    void updateOrderStatus(UUID orderId, String orderStatus);

    Order getOrderById(UUID orderId);

    List<Order> getOrdersByRestaurantId(Long restaurantId);
}
