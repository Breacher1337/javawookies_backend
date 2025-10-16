package com.javacake.kiosk.service;

import com.javacake.kiosk.model.Order;
import com.javacake.kiosk.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // 🟢 Fetch orders by list of statuses
    public List<Order> getOrdersByStatus(List<String> statuses) {
        return orderRepository.findByStatusIn(statuses);
    }

    // 🟢 Create order (default status PENDING)
    public Order createOrder(Order order) {
        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

    // 🟢 Update order status (CONFIRMED, CANCELLED, etc.)
    public void updateStatus(Long orderId, String status) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
    }
}
