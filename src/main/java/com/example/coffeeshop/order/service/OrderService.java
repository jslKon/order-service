package com.example.coffeeshop.order.service;

import com.example.coffeeshop.order.dto.CustomerOrderDto;
import com.example.coffeeshop.order.dto.OrderDto;
import com.example.coffeeshop.order.entity.OrderEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderDto findOrderById(UUID orderId);

    CustomerOrderDto findOrdersByCustomerId(UUID customerId, Pageable pageable);

    OrderDto createOrder(OrderDto orderDto);

    OrderDto updateOrderStatus(UUID orderId, OrderEntity.Status status);

    OrderDto updateOrderItems(UUID orderId, List<String> items);

    OrderDto cancelOrder(UUID orderId);
}
