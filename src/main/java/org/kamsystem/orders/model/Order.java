package org.kamsystem.orders.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kamsystem.orders.enums.Currency;
import org.kamsystem.orders.enums.Offers;
import org.kamsystem.orders.enums.OrderStatus;
import org.kamsystem.orders.enums.PaymentMethod;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    private UUID orderId;
    private Long restaurantId;
    private Long leadId;
    private Long interactionId;
    private BigDecimal orderAmount;
    private Currency currency;
    private String cartInfo;
    private String shippingInfo;
    private OrderStatus orderStatus;
    private Offers offer;
    private Long createdBy;
    private PaymentMethod paymentMethod;
    private String remarks;
    private Date createdAt;
}
