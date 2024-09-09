package com.example.coffeeshop.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto implements Serializable {

    private UUID id;

    @NotNull
    private UUID customerId;

    @NotNull
    private UUID shopId;

    @NotNull
    private String status;

    @NotNull
    private List<String> itemIds;
}
