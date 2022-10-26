package com.serasaexperian.pessoa.controller;

import com.serasaexperian.pessoa.service.TokenService2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
public class AuthController {

    private final TokenService2 tokenService;

    public AuthController(TokenService2 tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping()
    public String token(Authentication authentication) {
        return tokenService.generateToken(authentication);
    }
}
