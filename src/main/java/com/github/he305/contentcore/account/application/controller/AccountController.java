package com.github.he305.contentcore.account.application.controller;

import com.github.he305.contentcore.account.application.commands.RegisterAccountCommand;
import com.github.he305.contentcore.account.application.service.RegisterAccountService;
import com.github.he305.contentcore.shared.exceptions.ContentCoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final RegisterAccountService registerAccountService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegisterAccountCommand command) {
        try {
            registerAccountService.register(command);
            return new ResponseEntity<>(HttpStatus.CREATED.name(), HttpStatus.CREATED);
        } catch (ContentCoreException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST);
        }
    }
}
