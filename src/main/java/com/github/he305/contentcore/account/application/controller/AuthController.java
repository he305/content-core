package com.github.he305.contentcore.account.application.controller;

import com.github.he305.contentcore.account.application.commands.LoginAccountCommand;
import com.github.he305.contentcore.account.application.commands.RegisterAccountCommand;
import com.github.he305.contentcore.account.application.dto.JwtResponseDto;
import com.github.he305.contentcore.account.application.dto.LoginRequestDto;
import com.github.he305.contentcore.account.application.service.LoginAccountService;
import com.github.he305.contentcore.account.application.service.RegisterAccountService;
import com.github.he305.contentcore.account.domain.exceptions.AccountAlreadyExistsException;
import com.github.he305.contentcore.account.domain.exceptions.AccountLoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final RegisterAccountService registerAccountService;
    private final LoginAccountService loginAccountService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto dto) {
        try {
            LoginAccountCommand command = new LoginAccountCommand(dto.getUsername(), dto.getPassword());
            return ResponseEntity.ok(loginAccountService.execute(command));
        } catch (AccountLoginException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDto> register(@RequestBody LoginRequestDto dto) {
        try {
            RegisterAccountCommand command = new RegisterAccountCommand(dto.getUsername(), dto.getPassword());
            return ResponseEntity.ok(registerAccountService.execute(command));
        } catch (AccountAlreadyExistsException ex) {
            return ResponseEntity.badRequest().build();
        }

    }
}
