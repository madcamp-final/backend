package com.joongoprime.backend.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TokenInfo {
    private String grantType; // Bearer ( OAuth, Jwt )

    private String accessToken;

    private String refreshToken;

    private Long refreshTokenExpirationTime;
}
