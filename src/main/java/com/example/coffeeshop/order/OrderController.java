package com.example.coffeeshop.order;

import com.example.coffeeshop.common.constants.Routes;
import com.example.coffeeshop.dto.GeneralResponse;
import com.example.coffeeshop.dto.order.CustomerOrderDto;
import com.example.coffeeshop.dto.order.OrderDto;
import com.example.coffeeshop.entity.OrderEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = Routes.ROOT)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public GeneralResponse<OrderDto> getOderById(@PathVariable("orderId") UUID orderId) {
        return GeneralResponse.<OrderDto>builder()
                .data(orderService.findOrderById(orderId))
                .timestamp(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC))
                .status(HttpStatus.OK.name())
                .build();
    }

    @GetMapping("/customers/{customerId}")
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
                .status(HttpStatus.OK.name())
                .build();
    }

    @PutMapping("/{orderId}")
    public GeneralResponse<OrderDto> updateOrderItems(
            @PathVariable("orderId") UUID orderId,
            @Valid @NotNull @RequestParam List<String> itemIds) {
        return GeneralResponse.<OrderDto>builder()
                .data(orderService.updateOrderItems(orderId, itemIds))
                .timestamp(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC))
                .status(HttpStatus.OK.name())
                .build();
    }

    @PutMapping("/{orderId}/status")
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
                .status(HttpStatus.OK.name())
                .build();
    }
}
