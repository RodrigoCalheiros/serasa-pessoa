package com.serasaexperian.pessoa.controllers;

import com.serasaexperian.pessoa.services.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/api/token")
    public String token(Authentication authentication) {
        return tokenService.generateToken(authentication);
    }
}
