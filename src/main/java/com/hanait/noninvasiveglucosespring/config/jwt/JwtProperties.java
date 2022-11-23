package com.hanait.noninvasiveglucosespring.config.jwt;

public interface JwtProperties {
    String SECRET = "hanait1010";
    int EXPIRATION_TIME = 1000000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
