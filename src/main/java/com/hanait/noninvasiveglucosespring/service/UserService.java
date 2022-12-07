package com.hanait.noninvasiveglucosespring.service;

import com.hanait.noninvasiveglucosespring.model.User;
import com.hanait.noninvasiveglucosespring.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService {

    private UserRepository userRepository;

    /* 아이디 중복 여부 확인 */
    @Transactional(readOnly = true)
    public boolean checkUsernameDuplication(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Transactional(readOnly = true)
    public User getUserInfo(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

}
