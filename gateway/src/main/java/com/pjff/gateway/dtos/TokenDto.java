package com.pjff.gateway.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//V-113-paso 3.47
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenDto {

    private String accessToken;
}
