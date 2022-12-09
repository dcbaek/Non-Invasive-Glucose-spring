package com.hanait.noninvasiveglucosespring.dto;


import com.hanait.noninvasiveglucosespring.model.User;
import com.hanait.noninvasiveglucosespring.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserResponseDto {

    private final Long id;

    private final String phoneNumber;

    private final String nickname;

    private final String birthDay;

    private final String sex;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.nickname = user.getNickname();
        this.sex = user.getSex();
        this.birthDay =user.getBirthDay();

    }

    @Builder
    public UserResponseDto(Long id, String phoneNumber, String nickname, String birthDay, String sex) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.birthDay = birthDay;
        this.sex = sex;
    }
}