package com.hanait.noninvasiveglucosespring.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.sql.RowSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@DynamicUpdate
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String phoneNumber;
    private String nickname;

    @JsonIgnore
    private String password;

    private String roles;
    private String birthDay;
    private String sex;

    private String refreshToken;


    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<userCaregiver> userCaregivers = new ArrayList<>();

    // ENUM으로 안하고 ,로 해서 구분해서 ROLE을 입력 -> 그걸 파싱!!
    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
    }

    public void updateNickName(String nickname){
        this.nickname = nickname;
    }
    public void updateSex(String sex){
        this.sex = sex;
    }

    public void updateBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void destroyRefreshToken(){
        this.refreshToken = null;
    }
}
