package com.example.coffeeshop.order;

import com.example.coffeeshop.dto.order.OrderDto;
import com.example.coffeeshop.entity.OrderEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderEntity toEntity(OrderDto orderDto);

    @Mappings(
            @Mapping(source = "createdAt", target = "createdAt")
    )
    OrderDto toDto(OrderEntity orderDto);
}
