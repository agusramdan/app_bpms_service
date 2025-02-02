package com.agus.ramdan.bmps.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
@Component
@Profile("jwt")
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.secret:dwJkvudeRqVO8HGAgws9Km4hTeAX0EhMNcITQ6o515E=}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        val authentication_old = SecurityContextHolder.getContext().getAuthentication();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            String userId = claims.getSubject();
            // Set Authentication ke SecurityContext
            Object object = claims.get("Role");
            val authentication = new UsernamePasswordAuthenticationToken(userId, token, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        filterChain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(authentication_old);
    }
}
