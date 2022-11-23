package com.hanait.noninvasiveglucosespring.controller;

import com.hanait.noninvasiveglucosespring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class DataController {

    private final UserRepository userRepository;


}
