package com.hanait.noninvasiveglucosespring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDto {

    private Long id;
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String phoneNumber;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "생일은 필수 입력 값입니다.")
    private String birthDay;

    @NotBlank(message = "성별은 필수 입력 값입니다.")
    private String sex;

}
