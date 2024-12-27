package org.kamsystem.orders.repository;

import java.util.List;
import org.kamsystem.orders.model.Order;

public interface IOrderRepository {

    void createOrder(Order order);

    void updateOrderStatus(String orderId, String orderStatus);

    Order getOrderById(String orderId);

    List<Order> getOrdersByRestaurantId(Long restaurantId);
}
