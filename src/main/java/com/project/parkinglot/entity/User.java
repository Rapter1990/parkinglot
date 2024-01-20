package com.project.parkinglot.entity;

import com.project.parkinglot.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String username;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;

}
