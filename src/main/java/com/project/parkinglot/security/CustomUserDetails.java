package com.project.parkinglot.security;

import com.project.parkinglot.security.model.entity.UserEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Custom implementation of UserDetails named {@link CustomUserDetails} of Spring Security UserDetails.
 */
@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1673829563303819734L;

    private final UserEntity userEntity;

    /**
     * Retrieves the authorities granted to the user.
     *
     * @return A collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole().name()));
    }

    /**
     * Retrieves the password used to authenticate the user.
     *
     * @return The password.
     */
    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    /**
     * Retrieves the username used to authenticate the user.
     *
     * @return The username.
     */
    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return Always returns true.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return Always returns true.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     *
     * @return Always returns true.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return Always returns true.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Retrieves the user's email.
     *
     * @return The user's email.
     */
    public String getEmail() {
        return userEntity.getEmail();
    }

    /**
     * Retrieves the user's ID.
     *
     * @return The user's ID.
     */
    public String getId() {
        return userEntity.getId();
    }

    /**
     * Retrieves the user's claims.
     *
     * @return A map containing the user's claims.
     */
    public Map<String, Object> getClaims() {
        return userEntity.getClaims();
    }

}
