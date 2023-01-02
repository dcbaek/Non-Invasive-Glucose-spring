package com.hanait.noninvasiveglucosespring.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanait.noninvasiveglucosespring.config.auth.PrincipalDetails;
import com.hanait.noninvasiveglucosespring.config.jwt.JwtProperties;
import com.hanait.noninvasiveglucosespring.model.User;
import com.hanait.noninvasiveglucosespring.repository.UserRepository;
import com.hanait.noninvasiveglucosespring.service.UserService;
import com.hanait.noninvasiveglucosespring.validator.CheckUsernameValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.io.DataInput;
import java.io.IOException;
import java.util.*;

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

    @PostMapping("/user/checkpw")
    public String checkPassword(@RequestHeader(value = "Authorization", required = false) String token, User user,
                                @RequestParam("password") String password,@RequestParam("newPassword") String newPassword,
                                Authentication authentication, HttpServletResponse response, HttpServletRequest request) throws IOException {

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        String [] tokens = token.split("\\.");

        String payload = new String(Base64.getDecoder().decode(tokens[1]));

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> editUser = mapper.readValue(payload, Map.class);

        Long id = Long.parseLong(String.valueOf(editUser.get("id")));

        Optional<User> updateUser = userRepository.findById(id);

        if (bCryptPasswordEncoder.matches(password, principal.getUser().getPassword())) {

            updateUser.ifPresent(selectUser -> {
                if (newPassword != null) {
                    selectUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
                }
                User newUser = userRepository.save(selectUser);
                log.info("비밀번호 변경완료");
            });
            return "비밀번호 변경";
        } else {
            response.sendError(404,"비밀번호 오류");
        }

        return null;
    }

    @PostMapping("/user/findpw")
    public Map<String, Object> findPassword(@RequestParam("phoneNumber") String phoneNumber,
                             @RequestParam("password") String password) throws IOException {

        User user = userRepository.findByPhoneNumber(phoneNumber);

        log.info("user = {}", user);

        ObjectMapper mapper = new ObjectMapper();

        String findpw1 = mapper.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        writeValueAsString(user);

        log.info("findpw1 = {}", findpw1);

        Map<String, Object> findpw = mapper.readValue(findpw1, Map.class);

        log.info("findpw = {}", findpw);

        Long id = Long.parseLong(String.valueOf(findpw.get("id")));

        Optional<User> findPassword = userRepository.findById(id);

        findPassword.ifPresent(selectUser -> {
            if (password != null) {
                selectUser.setPassword(bCryptPasswordEncoder.encode(password));
            }
            User newUser = userRepository.save(selectUser);
            log.info("비밀번호 변경완료");
        });

        return null;
    }

    // 유저 혹은 매니저 혹은 어드민이 접근 가능
    @PostMapping("/user/info")
    public Map<String, Object> infoUser(@RequestHeader("Authorization") String token, HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        String [] tokens = token.split("\\.");

        String payload = new String(Base64.getDecoder().decode(tokens[1]));

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> infoUser = mapper.readValue(payload, Map.class);

        log.info("infoUser = {}", infoUser);

        return infoUser;
    }

    @PutMapping("/user/edit")
    public Optional<User> editUser(@RequestHeader(value = "Authorization", required = false) String token,
                                   @RequestBody User user, HttpServletRequest request) throws IOException {

        token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        String [] tokens = token.split("\\.");

        String payload = new String(Base64.getDecoder().decode(tokens[1]));

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> editUser = mapper.readValue(payload, Map.class);

        Long id = Long.parseLong(String.valueOf(editUser.get("id")));

        Optional<User> updateUser = userRepository.findById(id);

        updateUser.ifPresent(selectUser -> {
            if(user.getNickname() != null) {
                selectUser.setNickname(user.getNickname());
            }
            if (user.getSex() != null) {
                selectUser.setSex(user.getSex());
            }
            if (user.getBirthDay() != null) {
                selectUser.setBirthDay(user.getBirthDay());
            }

            User newUser = userRepository.save(selectUser);

        });

        return updateUser;

    }

    @PostMapping("/user/delete")
    public Map<String, Object> deleteUser(@RequestHeader("Authorization")  String token,
                                          HttpServletRequest request) throws JsonProcessingException {
        token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        return userService.delete(token);

    }

}