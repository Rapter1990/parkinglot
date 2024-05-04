package com.project.parkinglot.model;

import com.project.parkinglot.common.model.BaseDomainModel;
import com.project.parkinglot.security.model.enums.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Represents a user domain model object named {@link User}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseDomainModel {

    private String id;
    private String fullName;
    private String username;
    private String email;
    private Role role;
    private List<Vehicle> vehicleList;

}
