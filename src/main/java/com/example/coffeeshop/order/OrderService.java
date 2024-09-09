package com.example.coffeeshop.order;

import com.example.coffeeshop.dto.order.OrderDto;
import com.example.coffeeshop.entity.OrderEntity;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderDto findOrderById(UUID orderId);

    OrderDto createOrder(OrderDto orderDto);

    OrderDto updateOrderStatus(UUID orderId, OrderEntity.Status status);

    OrderDto updateOrderItems(UUID orderId, List<String> items);

    OrderDto cancelOrder(UUID orderId);
}
