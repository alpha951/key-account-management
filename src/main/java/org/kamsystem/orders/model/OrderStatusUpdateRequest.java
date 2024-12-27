package org.kamsystem.orders.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.kamsystem.orders.enums.OrderStatus;

@AllArgsConstructor
@Getter
public class OrderStatusUpdateRequest {

    private UUID orderId;
    private OrderStatus orderStatus;
}