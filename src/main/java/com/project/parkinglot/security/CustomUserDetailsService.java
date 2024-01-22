package com.project.parkinglot.security;

import com.project.parkinglot.security.model.entity.User;
import com.project.parkinglot.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = null;
        try {
            user = userService.findByEmail(username)
                    .orElseThrow(() -> new Exception("User Name " + username + " not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new CustomUserDetails(user);

    }

}
