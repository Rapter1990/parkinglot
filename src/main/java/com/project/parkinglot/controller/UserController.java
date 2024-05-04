package com.project.parkinglot.controller;

import com.project.parkinglot.model.User;
import com.project.parkinglot.payload.response.CustomResponse;
import com.project.parkinglot.service.user.UserGetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class named {@link UserController} for managing user information.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Management", description = "APIs for managing user information")
public class UserController {

    private final UserGetService userGetService;

    /**
     * Retrieves detailed information for a specific user by their ID.
     *
     * @param userId The ID of the user.
     * @return A CustomResponse containing the user information.
     */
    @GetMapping("/user/{user-id}")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @Operation(summary = "Get user information by ID",
            description = "Retrieves detailed information for a specific user by their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved user information",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized to access this endpoint")
            },
            security = @SecurityRequirement(name = "bearerAuth"))
    public CustomResponse<User> getUserInformationById(@PathVariable("user-id") @UUID final String userId) {

        final User user = userGetService.getUserById(userId);
        return CustomResponse.ok(user);
    }

    /**
     * Retrieves detailed information for a specific admin by their ID.
     *
     * @param adminId The ID of the admin.
     * @return A CustomResponse containing the admin information.
     */
    @GetMapping("/admin/{admin-id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get admin information by ID",
            description = "Retrieves detailed information for a specific admin by their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved admin information",
                            content = @Content(schema = @Schema(implementation = CustomResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Admin not found"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized to access this endpoint")
            },
            security = @SecurityRequirement(name = "bearerAuth"))
    public CustomResponse<User> getAdminInformationById(@PathVariable("admin-id") @UUID final String adminId) {
        final User admin = userGetService.getAdminById(adminId);
        return CustomResponse.ok(admin);
    }

}
