package com.project.parkinglot.controller;

import com.project.parkinglot.model.User;
import com.project.parkinglot.payload.response.CustomResponse;
import com.project.parkinglot.service.user.UserGetService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserGetService userGetService;

    @GetMapping("/user/{user-id}")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public CustomResponse<User> getUserInformationById(@PathVariable("user-id") @UUID final String userId) {

        final User user = userGetService.getUserById(userId);
        return CustomResponse.ok(user);
    }

    @GetMapping("/admin/{admin-id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CustomResponse<User> getAdminInformationById(@PathVariable("admin-id") @UUID final String adminId) {
        final User admin = userGetService.getAdminById(adminId);
        return CustomResponse.ok(admin);
    }

}
