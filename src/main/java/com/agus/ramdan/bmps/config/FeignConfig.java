package com.agus.ramdan.bmps.config;
import com.agus.ramdan.bmps.jwt.JwtTokenPropagator;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new JwtTokenPropagator();
    }
}
