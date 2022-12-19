package com.hanait.noninvasiveglucosespring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanait.noninvasiveglucosespring.dto.LoginRequestDto;
import com.hanait.noninvasiveglucosespring.model.User;
import com.hanait.noninvasiveglucosespring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Map;

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
    public LoginRequestDto update(String phoneNumber) {

        User user = userRepository.findByPhoneNumber(phoneNumber);

        return LoginRequestDto.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .nickname(user.getNickname())
                .birthDay(user.getBirthDay())
                .sex(user.getSex())
                .build();
    }

    @Transactional
    public Map<String, Object> delete(String token) throws JsonProcessingException {

        String [] tokens = token.split("\\.");

        //System.out.println("header = " + new String(Base64.getDecoder().decode(tokens[0])));
        log.info("body = {}", new String(Base64.getDecoder().decode(tokens[1])));

        String payload = new String(Base64.getDecoder().decode(tokens[1]));

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> returnMap = mapper.readValue(payload, Map.class);

        String phoneNumber = (String) returnMap.get("phoneNumber");

        User user = userRepository.findByPhoneNumber(phoneNumber);

        userRepository.delete(user);

        log.info("회원 삭제 완료");

        return null;
    }

    @Transactional(readOnly = true)
    public User getUserInfo(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

}
