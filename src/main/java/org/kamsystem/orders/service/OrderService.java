package org.kamsystem.orders.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.kamsystem.authentication.service.IAuthService;
import org.kamsystem.orders.enums.OrderStatus;
import org.kamsystem.orders.model.Order;
import org.kamsystem.orders.repository.IOrderRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IAuthService authService;

    @Override
    public void createOrder(Order order) {
        Long userId = authService.getUserIdOfLoggedInUser();
        order.setCreatedBy(userId);
        orderRepository.createOrder(order);
    }

    @Override
    public void updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
        orderRepository.updateOrderStatus(orderId.toString(), orderStatus.name());
    }

    @Override
    public Order getOrderById(UUID orderId) {
        return orderRepository.getOrderById(orderId.toString());
    }

    @Override
    public List<Order> getOrderByRestaurantId(Long restaurantId) {
        return orderRepository.getOrdersByRestaurantId(restaurantId);
    }
}
