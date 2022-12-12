package com.hanait.noninvasiveglucosespring.controller;


import com.hanait.noninvasiveglucosespring.config.auth.PrincipalDetails;
import com.hanait.noninvasiveglucosespring.dto.LoginRequestDto;
import com.hanait.noninvasiveglucosespring.model.User;
import com.hanait.noninvasiveglucosespring.repository.UserRepository;
import com.hanait.noninvasiveglucosespring.service.UserService;
import com.hanait.noninvasiveglucosespring.validator.CheckUsernameValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/wellink")
@RequiredArgsConstructor
// @CrossOrigin  // CORS 허용
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final CheckUsernameValidator checkUsernameValidator;

    @InitBinder
    public void validatorBinder(WebDataBinder binder){
        binder.addValidators(checkUsernameValidator);
    }

    // 모든 사람이 접근 가능
    @GetMapping("/home")
    public String home() {
        return "<h1>home</h1>";
    }

    // Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용 불가능.
    // 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.

    // 유저 혹은 매니저 혹은 어드민이 접근 가능
    @PostMapping("/user/info")
    public Authentication infoUser(User user, Authentication authentication, HttpServletResponse response) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        log.info("Authentication info = {}", authentication);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        return authentication;
    }

    // 매니저 혹은 어드민이 접근 가능
    @GetMapping("/manager/reports")
    public String reports() {
        return "<h1>reports</h1>";
    }

    // 어드민이 접근 가능
    @PostMapping("/admin/users")
    public List<User> users(){
        return userRepository.findAll();
    }

    @PostMapping("/user/join")
    public String joinUser(@Validated @RequestBody User user, BindingResult bindingResult, Model model, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            // 회원 가입 실패시 입력 데이터 값을 유지
            model.addAttribute("user", user);

            // 유효서 통과 못한 필드와 메시지를 핸들링
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
                log.info("Error message : {}", error.getDefaultMessage());
            }
            // 회원 가입 페이지로 다시 리턴
            return "/home";
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");

        userRepository.save(user);

        return "회원가입완료";
    }

    @PostMapping("/user/check")
    public String findPhoneNumber(@Validated @RequestBody User user, Model model,
                                  BindingResult bindingResult, HttpServletResponse response) {

        LoginRequestDto dto = new LoginRequestDto();

        log.info("phoneNumber={}", user.getPhoneNumber());
        log.info("User={}", user);
        log.info("dto={}", dto);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        if (bindingResult.hasErrors()) {
            // 회원 가입 실패시 입력 데이터 값을 유지
            log.info("phoneNumber={}", user.getPhoneNumber());
            model.addAttribute("user", user);

            // 유효서 통과 못한 필드와 메시지를 핸들링
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
                log.info("Error message : {}", error.getDefaultMessage());
            }
            // 회원 가입 페이지로 다시 리턴
            return "/user/check";
        }
        userRepository.findByPhoneNumber(user.getPhoneNumber());
        log.info("phoneNumber2={}", user.getPhoneNumber());

        return "사용 가능한 번호 입니다.";
    }

    @PutMapping("/user/edit")
    public Optional<User> editUser(@RequestParam String phoneNumber, @RequestBody User user) {

        Optional<User> updateUser = Optional.ofNullable(userRepository.findByPhoneNumber(phoneNumber));

        log.info("updateUser = {}", updateUser);

        updateUser.ifPresent(selectUser -> {
            selectUser.setNickname(user.getNickname());
            selectUser.setBirthDay(user.getBirthDay());
            selectUser.setSex(user.getSex());

            userRepository.save(selectUser);
        });

        return updateUser;
    }

    @DeleteMapping("/user/delete/{phoneNumber}")
    public void deleteUser(@PathVariable("phoneNumber") String phoneNumber) {

        userService.delete(phoneNumber);
    }

}