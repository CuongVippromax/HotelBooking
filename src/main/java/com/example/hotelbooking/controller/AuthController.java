package com.example.hotelbooking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("*/auth")
@RequiredArgsConstructor
@Slf4j(topic = "AUTH_CONTROLLER")
public class AuthController {
//    @PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password) {
//        log.info("username = {}, password = {}", username, password);
//
//    }
}
