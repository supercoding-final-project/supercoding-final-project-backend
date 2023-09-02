package com.github.supercodingfinalprojectbackend.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Getter
@MappedSuperclass
public abstract class CommonEntity {
    @Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint default 0")
    private Boolean isDeleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", insertable = false)
    private Instant updatedAt;

    public boolean isValid() { return !isDeleted; }
}
