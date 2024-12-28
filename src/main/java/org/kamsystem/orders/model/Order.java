package org.kamsystem.orders.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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

    private UUID orderId;

    @NonNull
    private Long restaurantId;

    @NonNull
    private Long leadId;

    @NonNull
    private String restaurantOrderId;

    @NonNull
    private Long interactionId;

    @NonNull
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
