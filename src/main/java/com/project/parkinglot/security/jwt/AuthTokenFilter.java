package com.project.parkinglot.security.jwt;

import com.project.parkinglot.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Authentication Token Filter named {@link AuthTokenFilter} for validating JWT tokens.
 */
@RequiredArgsConstructor
@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Filters incoming requests.
     *
     * @param request     The incoming request.
     * @param response    The response to be generated.
     * @param filterChain The chain of filters to proceed the request.
     * @throws ServletException If a servlet error occurs.
     * @throws IOException      If an input or output error occurs.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        try {

            String jwt = parseJwt(request);
            log.error("AuthTokenFilter | doFilterInternal | jwt: {}", jwt);

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

                String email = jwtUtils.getEmailFromToken(jwt);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("AuthTokenFilter | doFilterInternal | Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Parses JWT token from the request header.
     *
     * @param request The incoming HTTP request.
     * @return JWT token if exists, otherwise null.
     */
    private String parseJwt(HttpServletRequest request) {

        String headerAuth = request.getHeader("Authorization");

        log.info("AuthTokenFilter | parseJwt | headerAuth: {}", headerAuth);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {

            log.info("AuthTokenFilter | parseJwt | parseJwt: {}", headerAuth.substring(7));

            return headerAuth.substring(7);
        }

        return null;
    }

}
