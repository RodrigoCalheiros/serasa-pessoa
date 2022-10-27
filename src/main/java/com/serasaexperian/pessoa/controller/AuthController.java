package com.serasaexperian.pessoa.controller;

import com.serasaexperian.pessoa.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
public class AuthController {

    private AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping()
    public String token(Authentication authentication) {
        return authService.generateToken(authentication);
    }
}
