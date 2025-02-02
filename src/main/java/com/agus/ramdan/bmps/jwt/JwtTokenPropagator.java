package com.agus.ramdan.bmps.jwt;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Profile("jwt")
public class JwtTokenPropagator implements RequestInterceptor {

    public void apply(RequestTemplate template) {
        // Retrieve the JWT token from the SecurityContextHolder
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String) {
            String jwtToken = (String) authentication.getCredentials();
            template.header("Authorization", "Bearer " + jwtToken);
        }
    }
}