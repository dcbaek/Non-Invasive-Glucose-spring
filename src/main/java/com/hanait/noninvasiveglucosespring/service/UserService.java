package com.hanait.noninvasiveglucosespring.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanait.noninvasiveglucosespring.config.jwt.JwtProperties;
import com.hanait.noninvasiveglucosespring.dto.LoginRequestDto;
import com.hanait.noninvasiveglucosespring.model.User;
import com.hanait.noninvasiveglucosespring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /* 아이디 중복 여부 확인 */
    public boolean checkPhoneNumberDuplication(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    //https://binco.tistory.com/entry/SpringBoot-JPA-%EA%B2%8C%EC%8B%9C%ED%8C%90-CRUD-%EA%B5%AC%ED%98%84Read
    public String update(Long id) {

        Optional<User> updateUser = userRepository.findById(id);

        updateUser.ifPresent(selectUser -> {
            selectUser.setNickname(selectUser.getNickname());

            User newUser = userRepository.save(selectUser);
            log.info("newUser = {}", newUser);
        });

        return "수정 완료";
    }

    @Transactional
    public Map<String, Object> delete(String token) throws JsonProcessingException {

        String [] tokens = token.split("\\.");
        //System.out.println("header = " + new String(Base64.getDecoder().decode(tokens[0])));
        log.info("body = {}", new String(Base64.getUrlDecoder().decode(tokens[1])));

        String payload = new String(Base64.getUrlDecoder().decode(tokens[1]));

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> returnMap = mapper.readValue(payload, Map.class);

        String phoneNumber = (String) returnMap.get("phoneNumber");

        User user = userRepository.findByPhoneNumber(phoneNumber);

        userRepository.delete(user);

        log.info("회원 삭제 완료");

        return null;
    }

    public void updateRefreshToken(String phoneNumber, String refreshToken) {

        User user = userRepository.findByPhoneNumber(phoneNumber);

        user.updateRefreshToken(refreshToken);

    }

//    public Map<String, String> refresh(String refreshToken) {
//
//        // Refresh Token 유효성 검사사
//        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build();
//        DecodedJWT decodedJWT = verifier.verify(refreshToken);
//
//        // Access Token 재발급
//        long now = System.currentTimeMillis();
//        String phoneNumber = decodedJWT.getSubject();
//        User user = userRepository.findByPhoneNumber(phoneNumber);
//
//        if (!user.getRefreshToken().equals(refreshToken)) {
//            throw new JWTVerificationException("유효하지 않은 Refresh Token 입니다.");
//        }
//
//        String accessToken = JWT.create()
//                .withSubject(user.getPhoneNumber())
//                .withExpiresAt(new Date(now + JwtProperties.EXPIRATION_TIME))
//                .withClaim("roles", user.getRoles())
//                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
//
//        return null;
//    }

    @Transactional(readOnly = true)
    public User getUserInfo(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

}
