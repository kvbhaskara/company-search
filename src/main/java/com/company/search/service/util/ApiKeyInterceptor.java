package com.company.search.service.util;

import com.company.search.exceptions.InvalidApiKeyException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Value("${api.key}")
    private String apiKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestApiKey = request.getHeader("x-api-key");

        if (apiKey.equals(requestApiKey)) {
            return true; // Proceed with the request
        } else {
            throw new InvalidApiKeyException("Invalid API Key");
        }
    }
}
