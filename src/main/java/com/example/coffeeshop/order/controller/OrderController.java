package com.example.coffeeshop.order.controller;

import com.example.coffeeshop.common.GeneralResponse;
import com.example.coffeeshop.common.constants.Routes;
import com.example.coffeeshop.order.dto.CustomerOrderDto;
import com.example.coffeeshop.order.dto.OrderDto;
import com.example.coffeeshop.order.entity.OrderEntity;
import com.example.coffeeshop.order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = Routes.ROOT)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public GeneralResponse<OrderDto> getOderById(@PathVariable("orderId") UUID orderId) {
        log.info("this should not be printed out : {}", "get-order");
        MDC.put("taskName", "get-order");
        MDC.put("traceId", UUID.randomUUID().toString());
        log.info("this should be printed out : {}", "get-order");

        Marker getOrder = MarkerFactory.getMarker("GET_ORDER");
        Marker createOrder = MarkerFactory.getMarker("CREATE_ORDER");

        log.info(getOrder, "get order {}", orderId);
        log.info(createOrder, "create order {}", orderId);

        return GeneralResponse.<OrderDto>builder()
                .data(orderService.findOrderById(orderId))
                .timestamp(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC))
                .status(HttpStatus.OK.name())
                .build();
    }

    @GetMapping(Routes.CUSTOMER + "/{customerId}")
    public GeneralResponse<CustomerOrderDto> getOderByCustomerId(@PathVariable("customerId") UUID customerId,
                                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "size", defaultValue = "5") int size) {

        return GeneralResponse.<CustomerOrderDto>builder()
                .data(orderService.findOrdersByCustomerId(customerId, PageRequest.of(page, size)))
                .timestamp(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC))
                .status(HttpStatus.OK.name())
                .build();
    }

    @PostMapping
    public GeneralResponse<OrderDto> createNewOrder(@Valid @RequestBody OrderDto orderDto) {
        return GeneralResponse.<OrderDto>builder()
                .data(orderService.createOrder(orderDto))
                .timestamp(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC))
                .status(HttpStatus.CREATED.name())
                .build();
    }

    @PatchMapping("/{orderId}")
    public GeneralResponse<OrderDto> updateOrderItems(
            @PathVariable("orderId") UUID orderId,
            @Valid @NotNull @RequestParam List<String> itemIds) {
        return GeneralResponse.<OrderDto>builder()
                .data(orderService.updateOrderItems(orderId, itemIds))
                .timestamp(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC))
                .status(HttpStatus.OK.name())
                .build();
    }

    @PatchMapping("/{orderId}/status")
    public GeneralResponse<OrderDto> updateOrderStatus(
            @PathVariable("orderId") UUID orderId,
            @RequestParam OrderEntity.Status status) {

        return GeneralResponse.<OrderDto>builder()
                .timestamp(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC))
                .data(orderService.updateOrderStatus(orderId, status))
                .status(HttpStatus.OK.name())
                .build();
    }

    @DeleteMapping("/{orderId}")
    public GeneralResponse<OrderDto> cancelOrder(@PathVariable("orderId") UUID orderId) {
        return GeneralResponse.<OrderDto>builder()
                .timestamp(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC))
                .data(orderService.cancelOrder(orderId))
                .status(HttpStatus.NO_CONTENT.name())
                .build();
    }
}
