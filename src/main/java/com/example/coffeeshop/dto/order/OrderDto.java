package com.example.coffeeshop.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private UUID id;

    @NotNull
    private UUID customerId;

    @NotNull
    private UUID shopId;

    @NotNull
    private String status;

    @NotNull
    private List<String> itemIds;

    private Timestamp createdAt;
}
