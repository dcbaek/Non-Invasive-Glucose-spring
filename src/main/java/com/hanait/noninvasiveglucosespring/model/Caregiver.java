package com.hanait.noninvasiveglucosespring.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@DynamicUpdate
public class Caregiver extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "caregiverId")
    private long id;
    private String phoneNumber;
    private int userId;
    /*
    1. 전화번호 사용자 조회
    2. 보호자 등록
    3. 로그인한 윶의 보호자 조회
    4. 보호 대상자 조회
    5. 보호자 및 보호 대상자 삭제제
    */
}
