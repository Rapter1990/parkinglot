package com.project.parkinglot.common.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "CREATED_USER")
    protected String createdUser;

    @Column(name = "CREATED_AT")
    protected LocalDateTime createdAt;

    @Column(name = "UPDATED_USER")
    protected String updatedUser;

    @Column(name = "UPDATED_AT")
    protected LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdUser = "createdUser";
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedUser = "updatedUser";
        this.updatedAt = LocalDateTime.now();
    }

}
