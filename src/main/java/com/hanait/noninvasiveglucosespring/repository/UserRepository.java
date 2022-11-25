package com.hanait.noninvasiveglucosespring.repository;

import com.hanait.noninvasiveglucosespring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhoneNumber(String phoneNumber);
}
