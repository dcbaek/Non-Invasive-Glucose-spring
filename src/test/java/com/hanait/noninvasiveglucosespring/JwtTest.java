package com.hanait.noninvasiveglucosespring;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hanait.noninvasiveglucosespring.config.jwt.JwtProperties;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.Size;
import java.util.Base64;

public class JwtTest {

    @Test
    void jwt_test() {

        String auth0 = JWT.create()
                .withClaim("name", "ugo")
                .withClaim("price", 1000)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        System.out.println(auth0);
        printToken(auth0);

    }

    public void printToken(String token) {
         String [] tokens = token.split("\\.");
        System.out.println("header = " + new String(Base64.getDecoder().decode(tokens[0])));
        System.out.println("body = " + new String(Base64.getDecoder().decode(tokens[1])));
    }
}
