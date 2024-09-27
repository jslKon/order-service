package com.example.coffeeshop.order.service;

import com.example.coffeeshop.common.exception.BusinessException;
import com.example.coffeeshop.common.loghelper.LogExecutionTime;
import com.example.coffeeshop.order.dto.CustomerOrderDto;
import com.example.coffeeshop.order.dto.OrderDto;
import com.example.coffeeshop.order.entity.OrderEntity;
import com.example.coffeeshop.order.mapper.OrderMapper;
import com.example.coffeeshop.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Override
    @LogExecutionTime(
            messages = "Found order with id %s",
            params = { "[0]" }
    )
    public OrderDto findOrderById(UUID orderId) {

        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessException("No order with id " + orderId + " found")
        );

        return orderMapper.toDto(orderEntity);
    }

    @Override
    @LogExecutionTime(
            messages = "Found orders of customer with id %s",
            params = { "[0]" }
    )
    public CustomerOrderDto findOrdersByCustomerId(UUID customerId, Pageable pageable) {
        Page<OrderDto> orderDtoPage = orderRepository.findByCustomerIdOrderByCreatedAtDesc(customerId, pageable)
                .map(orderMapper::toDto);

        return CustomerOrderDto.builder()
                .customerId(customerId)
                .orderDtoPage(orderDtoPage)
                .build();
    }

    @Override
    @LogExecutionTime(
            messages = "Created order of customer's id %s for customer id %s",
            params = { "[0].id", "[0].customerId" }
    )
    public OrderDto createOrder(OrderDto orderDto) {

        OrderEntity newOrder = orderRepository.save(
                orderMapper.toEntity(orderDto)
        );

        return orderMapper.toDto(newOrder);
    }

    @Override
    @LogExecutionTime(
            messages = "Updated order id %s with status %s",
            params = { "[0]", "[1]" }
    )
    public OrderDto updateOrderStatus(UUID orderId, OrderEntity.Status status) {

        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessException("No order with id " + orderId + " found")
        );

        orderEntity.setStatus(status);

        return orderMapper.toDto(orderRepository.save(orderEntity));
    }

    @Override
    @LogExecutionTime(
            messages = "Updated order id %s with items %s",
            params = { "[0]", "[1]" }
    )
    public OrderDto updateOrderItems(UUID orderId, List<String> items) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessException("No order with id " + orderId + " found")
        );

        orderEntity.setItemIds(items);

        return orderMapper.toDto(orderRepository.save(orderEntity));
    }

    @Override
    @LogExecutionTime(
            messages = "Canceled order id %s",
            params = { "[0]" }
    )
    public OrderDto cancelOrder(UUID orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessException("No order with id " + orderId + " found")
        );

        if (OrderEntity.Status.PROCESSING.equals(orderEntity.getStatus())) {
            throw new BusinessException("Order are being processed. Too late to cancel babe");
        }

        orderEntity.setStatus(OrderEntity.Status.CANCELED);

        return orderMapper.toDto(orderRepository.save(orderEntity));
    }
}
