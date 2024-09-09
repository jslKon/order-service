package com.example.coffeeshop.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class GeneralResponse<T> {

    private String status;

    private T data;

    private OffsetDateTime timestamp;

}
