package com.hanait.noninvasiveglucosespring.repository;

import com.hanait.noninvasiveglucosespring.dto.LoginRequestDto;
import com.hanait.noninvasiveglucosespring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);

    //void delete(String phoneNumber);


    //@Modifying
    //@Query("update User m set ")
    //String updateParam();
}
