package com.example.coffeeshop.order.dto;

import com.example.coffeeshop.order.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderDto {

    private UUID customerId;

    private Page<OrderDto> orderDtoPage;
}
