package org.kamsystem.orders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kamsystem.authentication.service.IAuthService;
import org.kamsystem.orders.enums.Currency;
import org.kamsystem.orders.enums.OrderStatus;
import org.kamsystem.orders.enums.PaymentMethod;
import org.kamsystem.orders.model.Order;
import org.kamsystem.orders.repository.IOrderRepository;
import org.kamsystem.orders.service.OrderService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IAuthService authService;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("Should create an order successfully")
    void createOrderSuccessfully() {
        // Given
        Long mockUserId = 1L;
        Order mockOrder = new Order(
            null,
            101L,
            201L,
            "RES123",
            301L,
            BigDecimal.valueOf(150.75),
            Currency.INR,
            "Cart Info",
            "Shipping Info",
            null,
            null,
            null,
            PaymentMethod.CREDIT_CARD,
            "Remarks",
            null
        );

        when(authService.getUserIdOfLoggedInUser()).thenReturn(mockUserId);
        doNothing().when(orderRepository).createOrder(mockOrder);

        // When
        orderService.createOrder(mockOrder);

        // Then
        assertNotNull(mockOrder.getOrderId());
        assertEquals(mockUserId, mockOrder.getCreatedBy());
        verify(authService).getUserIdOfLoggedInUser();
        verify(orderRepository).createOrder(mockOrder);
    }

    @Test
    @DisplayName("Should update order status successfully")
    void updateOrderStatusSuccessfully() {
        // Given
        UUID orderId = UUID.randomUUID();
        OrderStatus newStatus = OrderStatus.DELIVERED;

        doNothing().when(orderRepository).updateOrderStatus(orderId, newStatus.name());

        // When
        orderService.updateOrderStatus(orderId, newStatus);

        // Then
        verify(orderRepository).updateOrderStatus(orderId, newStatus.name());
    }

    @Test
    @DisplayName("Should fetch order by ID")
    void getOrderByIdSuccessfully() {
        // Given
        UUID orderId = UUID.randomUUID();
        Order mockOrder = new Order(
            orderId,
            101L,
            201L,
            "RES123",
            301L,
            BigDecimal.valueOf(150.75),
            Currency.INR,
            "Cart Info",
            "Shipping Info",
            OrderStatus.PENDING,
            null,
            1L,
            PaymentMethod.CREDIT_CARD,
            "Remarks",
            new Date()
        );

        when(orderRepository.getOrderById(orderId)).thenReturn(mockOrder);

        // When
        Order fetchedOrder = orderService.getOrderById(orderId);

        // Then
        assertEquals(mockOrder, fetchedOrder);
        verify(orderRepository).getOrderById(orderId);
    }

    @Test
    @DisplayName("Should fetch orders by restaurant ID")
    void getOrderByRestaurantIdSuccessfully() {
        // Given
        Long restaurantId = 101L;
        List<Order> mockOrders = List.of(
            new Order(
                UUID.randomUUID(),
                restaurantId,
                201L,
                "RES123",
                301L,
                BigDecimal.valueOf(150.75),
                Currency.INR,
                "Cart Info",
                "Shipping Info",
                OrderStatus.PENDING,
                null,
                1L,
                PaymentMethod.CREDIT_CARD,
                "Remarks",
                new Date()
            ),
            new Order(
                UUID.randomUUID(),
                restaurantId,
                202L,
                "RES124",
                302L,
                BigDecimal.valueOf(200.00),
                Currency.INR,
                "Cart Info",
                "Shipping Info",
                OrderStatus.DELIVERED,
                null,
                2L,
                PaymentMethod.DEBIT_CARD,
                "Remarks",
                new Date()
            )
        );

        when(orderRepository.getOrdersByRestaurantId(restaurantId)).thenReturn(mockOrders);

        // When
        List<Order> fetchedOrders = orderService.getOrderByRestaurantId(restaurantId);

        // Then
        assertEquals(mockOrders, fetchedOrders);
        verify(orderRepository).getOrdersByRestaurantId(restaurantId);
    }
}

