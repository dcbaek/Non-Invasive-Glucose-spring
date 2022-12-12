package com.hanait.noninvasiveglucosespring;

import org.junit.jupiter.api.Test;

import javax.validation.constraints.Size;
import java.util.Base64;

public class JwtTest {

    @Test
    void jwt_test() {

        String payload = "eyJzdWIiOiIwMTA5MTE1ODk4OCIsInBob25lTnVtYmVyIjoiMDEwOTExNTg5ODgiLCJpZCI6MjAwMiwiZXhwIjoxNjcwODI3MDQ3fQ";
        Base64.Decoder decoder = Base64.getUrlDecoder();

        System.out.println(new String(decoder.decode(payload)));
    }
}
