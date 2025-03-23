package com.e_commerce_tecprime_service.service;

import com.e_commerce_tecprime_service.entity.Order;
import com.e_commerce_tecprime_service.entity.OrderItem;
import com.e_commerce_tecprime_service.repository.OrderItemRepository;
import com.e_commerce_tecprime_service.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public Order createOrder(Order order, List<OrderItem> orderItems) {
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));

        double totalAmount = 0.0;
        for (OrderItem item : orderItems) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        return savedOrder;
    }
}