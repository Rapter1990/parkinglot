package com.project.parkinglot.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom authentication entry point named {@link AuthEntryPointJwt} for handling unauthorized requests.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    /**
     * Commences the authentication process for unauthorized requests.
     *
     * @param request       The request object.
     * @param response      The response object.
     * @param authException The authentication exception.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet exception occurs.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        LOGGER.error("AuthEntryPointJwt | commence | Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        LOGGER.info("AuthEntryPointJwt | commence | status: {}", HttpServletResponse.SC_UNAUTHORIZED);
        LOGGER.info("AuthEntryPointJwt | commence | error: {}", "Unauthorized");
        LOGGER.info("AuthEntryPointJwt | commence | message: {}", authException.getMessage());
        LOGGER.info("AuthEntryPointJwt | commence | path: {}", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);

    }
}
