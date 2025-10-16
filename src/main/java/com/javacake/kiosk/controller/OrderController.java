package com.javacake.kiosk.controller;

import com.javacake.kiosk.model.Order;
import com.javacake.kiosk.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ✅ CREATE new order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order orderRequest) {
        Order savedOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(savedOrder);
    }

    // ✅ GET all PENDING orders (for admin /orders)
    @GetMapping("/pending")
    public ResponseEntity<List<Order>> getActiveOrders() {
        List<Order> orders = orderService.getOrdersByStatus(List.of("PENDING"));
        return ResponseEntity.ok(orders);
    }

    // ✅ GET all CONFIRMED + CANCELLED (for admin /history)
    @GetMapping("/history")
    public ResponseEntity<List<Order>> getHistoryOrders() {
        List<Order> orders = orderService.getOrdersByStatus(List.of("CONFIRMED", "CANCELLED"));
        return ResponseEntity.ok(orders);
    }

    // ✅ CONFIRM an order (updates status to CONFIRMED)
    @PutMapping("/{orderId}/confirm")
    public ResponseEntity<Void> confirmOrder(@PathVariable Long orderId) {
        orderService.updateStatus(orderId, "CONFIRMED");
        return ResponseEntity.ok().build();
    }

    // ✅ CANCEL an order (updates status to CANCELLED)
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.updateStatus(orderId, "CANCELLED");
        return ResponseEntity.ok().build();
    }
}
