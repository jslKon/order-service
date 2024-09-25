package com.example.coffeeshop.order.entity;

import com.example.coffeeshop.common.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = OrderEntity.Constants.TABLE_NAME, indexes = {
        @Index(name = "idx_orderentity_customer_id", columnList = "customer_id, shop_id")
})
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = Constants.ORDER_ID, nullable = false)
    private UUID id;

    @Column(name = Constants.CUSTOMER_ID, nullable = false)
    private UUID customerId;

    @Column(name = Constants.SHOP_ID, nullable = false)
    private UUID shopId;

    @Column(name = Constants.COLUMN_STATUS_NAME, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Type(JsonType.class)
    @Column(name = Constants.COLUMN_ITEMIDS_NAME, nullable = false, columnDefinition = "json")
    private List<String> itemIds = new ArrayList<>();

    public static class Constants {
        public static final String TABLE_NAME = "orders";
        public static final String ORDER_ID = "id";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String SHOP_ID = "shop_id";
        public static final String COLUMN_STATUS_NAME = "status";
        public static final String COLUMN_ITEMIDS_NAME = "item_ids";

    }

    public enum Status {
        WAIT_FOR_CONFIRM,
        QUEUING,
        PROCESSING,
        CANCELED,
        DONE
    }
}