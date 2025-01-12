package com.pjff.auth_server.controllers;

import com.pjff.auth_server.dtos.TokenDto;
import com.pjff.auth_server.dtos.UserDto;
import com.pjff.auth_server.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Vid 108
@RestController
@RequestMapping(path = "auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "login") // password secret
    public ResponseEntity<TokenDto> jwtCreate(@RequestBody UserDto user) {
        return ResponseEntity.ok(this.authService.login(user));
    }

    @PostMapping(path = "jwt")
    public ResponseEntity<TokenDto> jwtValidate(@RequestHeader String accessToken) {
        return ResponseEntity.ok(
                this.authService.validateToken(TokenDto.builder().accessToken(accessToken).build()));
    }
}
