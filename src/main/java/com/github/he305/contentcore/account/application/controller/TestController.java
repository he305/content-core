package com.github.he305.contentcore.account.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void get() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info(auth.toString());
        log.info(auth.getName());
    }
}
