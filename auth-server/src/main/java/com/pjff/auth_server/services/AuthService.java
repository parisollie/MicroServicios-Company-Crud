package com.pjff.auth_server.services;

import com.pjff.auth_server.dtos.TokenDto;
import com.pjff.auth_server.dtos.UserDto;

//Vid 104
public interface AuthService {

    TokenDto login(UserDto user);
    TokenDto validateToken(TokenDto token);
}

