package com.project.parkinglot.security.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumerator class named {@link Role} representing user roles.
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_DRIVER,
    ROLE_ADMIN
}
