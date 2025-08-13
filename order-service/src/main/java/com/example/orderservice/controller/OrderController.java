package com.example.orderservice.controller;

import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final WebClient webClient;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        this.webClient = WebClient.create("http://product-service:8081");
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        // Call product-service to decrease stock
        webClient.put()
            .uri("/products/{id}/decrease-stock?quantity={qty}", order.getProductId(), order.getQuantity())
            .retrieve()
            .bodyToMono(Void.class)
            .block();

        // Save the order
        Order savedOrder = orderService.placeOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}