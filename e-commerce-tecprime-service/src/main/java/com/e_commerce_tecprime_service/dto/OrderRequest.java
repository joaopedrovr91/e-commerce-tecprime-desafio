package com.e_commerce_tecprime_service.dto;

import com.e_commerce_tecprime_service.entity.Order;
import com.e_commerce_tecprime_service.entity.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private Order order;
    private List<OrderItem> orderItems;
}