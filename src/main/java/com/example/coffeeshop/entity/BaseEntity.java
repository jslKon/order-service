package com.example.coffeeshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = Constants.CREATED_AT, updatable = false)
    protected Timestamp createdAt;

    @LastModifiedDate
    @Column(name = Constants.LAST_MODIFIED_AT)
    protected Timestamp lastModifiedAt;

    @Version
    @Column(name = Constants.VERSION)
    @Builder.Default
    private Integer version = 0;

    public static final class Constants {
        public static final String CREATED_AT = "created_at";
        public static final String LAST_MODIFIED_AT = "last_modified_at";
        public static final String VERSION = "version";

    }
}
