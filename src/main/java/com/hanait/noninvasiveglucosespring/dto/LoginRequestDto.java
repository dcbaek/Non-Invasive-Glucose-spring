package com.hanait.noninvasiveglucosespring.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
    private String birthDay;
    private String sex;
}
