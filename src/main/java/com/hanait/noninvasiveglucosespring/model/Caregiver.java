package com.hanait.noninvasiveglucosespring.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@DynamicUpdate
public class Caregiver extends BaseTimeEntity{

    @Id @GeneratedValue
    private long id;
    private String phoneNumber;

    @OneToMany(mappedBy = "caregiver")
    @ToString.Exclude
    private List<userCaregiver> userCaregivers = new ArrayList<>();
    /*
    1. 전화번호 사용자 조회
    2. 보호자 등록
    3. 로그인한 윶의 보호자 조회
    4. 보호 대상자 조회
    5. 보호자 및 보호 대상자 삭제제
    */
}
