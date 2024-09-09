package com.example.coffeeshop.order;

import com.example.coffeeshop.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    Page<OrderEntity> findByCustomerIdOrderByCreatedAtDesc(UUID customerId, Pageable pageable);
}
