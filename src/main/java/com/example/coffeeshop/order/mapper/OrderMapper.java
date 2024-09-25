package com.example.coffeeshop.order.mapper;

import com.example.coffeeshop.order.entity.OrderEntity;
import com.example.coffeeshop.order.dto.OrderDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderEntity toEntity(OrderDto orderDto);

    @Mappings(
            @Mapping(source = "createdAt", target = "createdAt")
    )
    OrderDto toDto(OrderEntity orderDto);
}
