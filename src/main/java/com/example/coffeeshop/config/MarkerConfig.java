package com.example.coffeeshop.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MarkerConfig {

    enum TaskMarker {
        GET_ORDER,
        CREATE_ORDER
    }
}
