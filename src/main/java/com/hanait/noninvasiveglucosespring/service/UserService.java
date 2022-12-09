package com.hanait.noninvasiveglucosespring.service;

import com.hanait.noninvasiveglucosespring.dto.UserResponseDto;
import com.hanait.noninvasiveglucosespring.model.User;
import com.hanait.noninvasiveglucosespring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /* 아이디 중복 여부 확인 */
    @Transactional(readOnly = true)
    public boolean checkUsernameDuplication(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    //https://binco.tistory.com/entry/SpringBoot-JPA-%EA%B2%8C%EC%8B%9C%ED%8C%90-CRUD-%EA%B5%AC%ED%98%84Read
    public UserResponseDto userinfo(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(IllegalAccessError::new);

        return UserResponseDto.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .nickname(user.getNickname())
                .birthDay(user.getBirthDay())
                .sex(user.getSex())
                .build();
    }


    @Transactional
    public void delete(String phoneNumber) {

        User user = userRepository.findByPhoneNumber(phoneNumber);
        userRepository.delete(user);

    }

    @Transactional(readOnly = true)
    public User getUserInfo(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

}
