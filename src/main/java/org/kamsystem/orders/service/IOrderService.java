package org.kamsystem.orders.service;

import java.util.List;
import java.util.UUID;
import org.kamsystem.orders.enums.OrderStatus;
import org.kamsystem.orders.model.Order;

public interface IOrderService {

    void createOrder(Order order);

    void updateOrderStatus(UUID orderId, OrderStatus orderStatus);

    Order getOrderById(UUID orderId);

    List<Order> getOrderByRestaurantId(Long restaurantId);
}
