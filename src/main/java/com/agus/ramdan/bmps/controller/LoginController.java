package com.agus.ramdan.bmps.controller;

import com.agus.ramdan.bmps.dto.LoginRequest;
import com.agus.ramdan.bmps.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/api/login")
@Log4j2
public class LoginController {

    @Value("${app.keycloak.login.url:none}")
    private String loginUrl;
    @Value("${app.keycloak.client-secret:none}")
    private String clientSecret;
    @Value("${app.keycloak.grant-type:none}")
    private String grantType;
    @Value("${app.keycloak.client-id:none}" )
    private String clientId;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping
    @Operation(summary = "For testing and development integration with keycloak")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest request) throws Exception {
        log.info("Executing login");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", request.getUsername());
        map.add("password", request.getPassword());
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", grantType);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(loginUrl, httpEntity, LoginResponse.class);

        return ResponseEntity.status(loginResponse.getStatusCodeValue()).body(loginResponse.getBody());
    }


}
