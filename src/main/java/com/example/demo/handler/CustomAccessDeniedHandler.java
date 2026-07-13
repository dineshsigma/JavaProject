package com.example.demo.handler;


import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

@Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {

        String role = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .toString();

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        response.getWriter().write(
                """
                {
                    "status": 403,
                    "message": "Access Denied. Insufficient permissions to access this resource.",
                    "data": null
                }
                """
        );
    }


}
