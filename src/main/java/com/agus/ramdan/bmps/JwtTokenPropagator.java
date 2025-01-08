package com.agus.ramdan.bmps;

import feign.RequestInterceptor;
import feign.RequestTemplate;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenPropagator implements RequestInterceptor {

    public void apply(RequestTemplate template) {
        // Retrieve the JWT token from the SecurityContextHolder
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getCredentials() instanceof String) {
//            String jwtToken = (String) authentication.getCredentials();
//            template.header("Authorization", "Bearer " + jwtToken);
//        }
    }
}