package com.hanait.noninvasiveglucosespring.config.auth;


import com.hanait.noninvasiveglucosespring.model.User;
import com.hanait.noninvasiveglucosespring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//http://localhost:8080/login
@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

        log.info("PrincipalDetailsService : 진입");
        User user = userRepository.findByPhoneNumber(phoneNumber);
        log.info("user : {}", user);

        return new PrincipalDetails(user);
    }
}
