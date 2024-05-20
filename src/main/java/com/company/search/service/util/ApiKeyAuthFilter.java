package com.company.search.service.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends HttpFilter {

    @Value("${api.key}")
    private String apiKey;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String requestApiKey = request.getHeader("x-api-key");

        if (apiKey.equals(requestApiKey)) {
            chain.doFilter(request, response);
        } else {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid API Key");
        }
    }
}
