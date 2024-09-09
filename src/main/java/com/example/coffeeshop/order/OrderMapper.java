package com.example.coffeeshop.order;

import com.example.coffeeshop.dto.order.OrderDto;
import com.example.coffeeshop.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderEntity toEntity(OrderDto orderDto);

    OrderDto toDto(OrderEntity orderDto);
}
