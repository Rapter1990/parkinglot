package com.project.parkinglot.controller;

import com.project.parkinglot.model.User;
import com.project.parkinglot.payload.response.CustomResponse;
import com.project.parkinglot.service.user.UserGetService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserGetService userGetService;

    @GetMapping("/{user-id}")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public CustomResponse<User> getUserInformationById(@PathVariable("user-id") @UUID final String userId){

        final User user = userGetService.getUserById(userId);
        return CustomResponse.ok(user);
    }

}
