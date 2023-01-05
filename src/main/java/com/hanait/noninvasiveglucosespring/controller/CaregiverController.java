package com.hanait.noninvasiveglucosespring.controller;

import com.hanait.noninvasiveglucosespring.model.Caregiver;
import com.hanait.noninvasiveglucosespring.model.User;
import com.hanait.noninvasiveglucosespring.repository.CaregiverRepository;
import com.hanait.noninvasiveglucosespring.repository.UserRepository;
import com.hanait.noninvasiveglucosespring.service.UserService;
import com.hanait.noninvasiveglucosespring.validator.CheckUsernameValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Past;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/wellink")
@RequiredArgsConstructor
public class CaregiverController {

    private final UserRepository userRepository;

    private final UserService userService;

    private final CaregiverRepository caregiverRepository;

    private final CheckUsernameValidator checkUsernameValidator;

    @InitBinder
    public void validatorBinder(WebDataBinder binder){
        binder.addValidators(checkUsernameValidator);
    }

    @PostMapping("/caregiver/join")
    public String joinUser(@RequestHeader(value = "Authorization", required = false) String token,
                           @RequestBody Caregiver caregiver, HttpServletResponse response) {
        response.setContentType("application/json");

        caregiverRepository.save(caregiver);

        return "보호자추가";
    }

    @GetMapping("/caregiver/check/{phoneNumber}")
    public ResponseEntity<Boolean> findPhoneNumber(@PathVariable String phoneNumber, HttpServletResponse response) {

        return ResponseEntity.ok(userService.checkPhoneNumberDuplication(phoneNumber));

    }
}
