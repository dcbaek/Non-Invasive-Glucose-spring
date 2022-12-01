package com.hanait.noninvasiveglucosespring.validator;

import com.hanait.noninvasiveglucosespring.model.User;
import com.hanait.noninvasiveglucosespring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckUsernameValidator extends AbstractValidator<User>{

    private final UserRepository userRepository;

    @Override
    public void doValidate(User user, Errors errors) {
        if(userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            errors.rejectValue("phoneNumber", "아이디 중복 오류", "이미 사용 중인 아이디 입니다.");
        }

    }

}
