package com.example.orderservice.service;

import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl; // Injected from application.properties or env

    public OrderService(OrderRepository orderRepository,
                        RestTemplate restTemplate,
                        RedisTemplate<String, Object> redisTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    public Order placeOrder(Order order) {
        String cacheKey = "product_" + order.getProductId();
        Map product = (Map) redisTemplate.opsForValue().get(cacheKey);

        if (product == null) {
            product = restTemplate.getForObject(
                    productServiceUrl + "/" + order.getProductId(),
                    Map.class
            );
            if (product != null) {
                redisTemplate.opsForValue().set(cacheKey, product);
            }
        }

        if (product != null && (Integer) product.get("stockQuantity") >= order.getQuantity()) {
            order.setStatus("CONFIRMED");
        } else {
            order.setStatus("FAILED");
        }

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
