package com.hanait.noninvasiveglucosespring.controller;

import com.hanait.noninvasiveglucosespring.model.Caregiver;
import com.hanait.noninvasiveglucosespring.model.User;
import com.hanait.noninvasiveglucosespring.repository.CaregiverRepository;
import com.hanait.noninvasiveglucosespring.repository.UserRepository;
import com.hanait.noninvasiveglucosespring.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/wellink")
@RequiredArgsConstructor
public class CaregiverController {

    private final UserRepository userRepository;
    private final CaregiverRepository caregiverRepository;
    private final UserService userService;

    @PostMapping("/caregiver/join")
    public String joinUser(@RequestHeader(value = "Authorization", required = false) String token,
                           @RequestParam("phoneNumber") Caregiver caregiver) {

        caregiverRepository.save(caregiver);

        return "보호자추가";
    }

    @PostMapping("/caregiver/check")
    public String findPhoneNumber(@Validated @RequestBody String phoneNumber, Model model,
                                  BindingResult bindingResult, HttpServletResponse response) {

        response.setCharacterEncoding("utf-8");

        if (bindingResult.hasErrors()) {
            // 회원 가입 실패시 입력 데이터 값을 유지
            log.info("ca phoneNumber={}", phoneNumber);
            model.addAttribute("ca phoneNumber", phoneNumber);

            // 유효서 통과 못한 필드와 메시지를 핸들링
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
                log.info("Error message : {}", error.getDefaultMessage());
            }
            // 회원 가입 페이지로 다시 리턴
            return "/user/check";
        }
        caregiverRepository.findByPhoneNumber(phoneNumber);

        return "등록 가능한 보호자 입니다.";
    }
}
