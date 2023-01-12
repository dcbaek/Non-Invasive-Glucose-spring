package com.hanait.noninvasiveglucosespring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanait.noninvasiveglucosespring.model.Caregiver;
import com.hanait.noninvasiveglucosespring.model.User;
import com.hanait.noninvasiveglucosespring.repository.CaregiverRepository;
import com.hanait.noninvasiveglucosespring.repository.UserRepository;
import com.hanait.noninvasiveglucosespring.service.UserService;
import com.hanait.noninvasiveglucosespring.validator.CheckUsernameValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.DataInput;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/wellink")
@RequiredArgsConstructor
public class CaregiverController {

    private final UserRepository userRepository;

    private final UserService userService;

    private final CaregiverRepository caregiverRepository;

    @PostMapping("/caregiver/join")
    public String joinUser(@RequestHeader(value = "Authorization", required = false) String token,
                           @RequestBody Caregiver caregiver, HttpServletResponse response) {
        response.setContentType("application/json");

        caregiverRepository.save(caregiver);
        log.info("caregiver join");

        return "보호자추가";
    }

    @GetMapping("/caregiver/check/{phoneNumber}")
    public User findPhoneNumber(@PathVariable String phoneNumber, HttpServletResponse response) throws IOException {

        boolean user = userService.checkPhoneNumberDuplication(phoneNumber);

        if (!user) {
            response.setStatus(400);
        } else {

            response.setContentType("application/json");

            User user1 = userRepository.findByPhoneNumber(phoneNumber);

            log.info("caregiver info = {}", user1);

            return userRepository.findByPhoneNumber(phoneNumber);

        }

        return null;

    }

    @PostMapping("/caregiver/checkList")
    public List<User> checkList() {
        return userRepository.findAll();
    }

    @PostMapping("/caregiver/delete")
    public void caregiverDelete(User user) {

        userRepository.delete(user);

    }
}


