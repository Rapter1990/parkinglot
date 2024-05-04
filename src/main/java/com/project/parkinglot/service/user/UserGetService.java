package com.project.parkinglot.service.user;

import com.project.parkinglot.model.User;

/**
 * Service interface named {@link UserGetService} for retrieving user information.
 */
public interface UserGetService {

    /**
     * Retrieves a user by user ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the user
     */
    User getUserById(String userId);

    /**
     * Retrieves an admin user by admin ID.
     *
     * @param adminId the ID of the admin user to retrieve
     * @return the admin user
     */
    User getAdminById(final String adminId);

}
