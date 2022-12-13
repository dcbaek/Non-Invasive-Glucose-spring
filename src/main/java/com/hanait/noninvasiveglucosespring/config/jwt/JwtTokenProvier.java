package com.hanait.noninvasiveglucosespring.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class JwtTokenProvier {

    private final JwtProperties properties;
    private final UserDetailsService userDetailsService;


}
