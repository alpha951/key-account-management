package org.kamsystem.orders.api;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.kamsystem.common.model.ApiResponse;
import org.kamsystem.orders.model.Order;
import org.kamsystem.orders.model.OrderStatusUpdateRequest;
import org.kamsystem.orders.service.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid Order order,
        BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new ApiResponse<>(false, result.getAllErrors()),
                HttpStatus.BAD_REQUEST);
        }
        orderService.createOrder(order);
        return new ResponseEntity<>(new ApiResponse<>(true, "Order created successfully"),
            HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@RequestBody @Valid OrderStatusUpdateRequest request,
        BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new ApiResponse<>(false, result.getAllErrors()),
                HttpStatus.BAD_REQUEST);
        }
        orderService.updateOrderStatus(request.getOrderId(), request.getOrderStatus());
        return new ResponseEntity<>(new ApiResponse<>(true, "Order status updated successfully"),
            HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable UUID orderId) {
        Order order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(new ApiResponse<>(true, order), HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<?> getOrderByRestaurantId(@PathVariable Long restaurantId) {
        List<Order> orders = orderService.getOrderByRestaurantId(restaurantId);
        return new ResponseEntity<>(new ApiResponse<>(true, orders), HttpStatus.OK);
    }
}
