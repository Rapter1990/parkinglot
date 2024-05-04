package com.project.parkinglot.security;

import com.project.parkinglot.security.model.entity.UserEntity;
import com.project.parkinglot.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class named {@link CustomUserDetailsService} for loading user-specific data.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    /**
     * Locates the user based on the username.
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record.
     * @throws UsernameNotFoundException if the user could not be found or the user has no GrantedAuthority.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = null;
        try {
            userEntity = userService.findByEmail(username)
                    .orElseThrow(() -> new Exception("User Name " + username + " not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new CustomUserDetails(userEntity);

    }

}
