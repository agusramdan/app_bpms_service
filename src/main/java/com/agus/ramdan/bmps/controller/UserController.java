package com.agus.ramdan.bmps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 *
 */
@RestController()
@RequestMapping("/api/users")
public class UserController {


    @GetMapping("me")
    @Operation(summary = "Get info from OpenID Connect (OIDC) Bearer token authentication")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",
                    content = {
                    @Content(mediaType = "application/json", schema = @Schema()),
                             })
    })
    public Map<String, Object> getUserInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = null;
        if (authentication != null && authentication.getCredentials() instanceof String) {
            jwtToken = (String) authentication.getCredentials();
            ///template.header("Authorization", "Bearer " + jwtToken);
        }
        return Collections.singletonMap("jwtToken", jwtToken);
    }
}
