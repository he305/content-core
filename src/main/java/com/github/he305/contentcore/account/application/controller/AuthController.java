package com.github.he305.contentcore.account.application.controller;

import com.github.he305.contentcore.account.application.commands.LoginAccountCommand;
import com.github.he305.contentcore.account.application.commands.RegisterAccountCommand;
import com.github.he305.contentcore.account.application.commands.RegisterServiceCommand;
import com.github.he305.contentcore.account.application.dto.JwtRefreshTokenDto;
import com.github.he305.contentcore.account.application.dto.JwtResponseDto;
import com.github.he305.contentcore.account.application.dto.LoginRequestDto;
import com.github.he305.contentcore.account.application.dto.RegisterServiceDto;
import com.github.he305.contentcore.account.application.exceptions.JwtRefreshTokenNotValidException;
import com.github.he305.contentcore.account.application.service.LoginAccountService;
import com.github.he305.contentcore.account.application.service.RefreshTokenService;
import com.github.he305.contentcore.account.application.service.RegisterAccountService;
import com.github.he305.contentcore.account.application.service.RegisterServiceService;
import com.github.he305.contentcore.account.domain.exceptions.AccountAlreadyExistsException;
import com.github.he305.contentcore.account.domain.exceptions.AccountLoginException;
import com.github.he305.contentcore.shared.validators.StringValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    private final RegisterServiceService registerServiceService;
    private final RegisterAccountService registerAccountService;
    private final LoginAccountService loginAccountService;
    private final RefreshTokenService refreshTokenService;
    @Value("${auth.service-register-key}")
    private String serviceRegisterKey;

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
            String username = StringValidator.isNullOrEmpty(dto.getUsername());
            String password = StringValidator.isNullOrEmpty(dto.getPassword());
            RegisterAccountCommand command = new RegisterAccountCommand(username, password);
            return ResponseEntity.ok(registerAccountService.execute(command));
        } catch (AccountAlreadyExistsException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/registerService")
    public ResponseEntity<JwtResponseDto> registerService(@RequestBody RegisterServiceDto dto) {
        if (!dto.getServiceRegisterKey().equals(serviceRegisterKey)) {
            return ResponseEntity.badRequest().build();
        }

        try {
            String username = StringValidator.isNullOrEmpty(dto.getUsername());
            String password = StringValidator.isNullOrEmpty(dto.getPassword());
            RegisterServiceCommand command = new RegisterServiceCommand(username, password);
            return ResponseEntity.ok(registerServiceService.execute(command));
        } catch (AccountAlreadyExistsException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDto> refreshToken(@RequestBody JwtRefreshTokenDto refreshTokenDto) {
        try {
            return ResponseEntity.ok(refreshTokenService.refreshToken(refreshTokenDto));
        } catch (JwtRefreshTokenNotValidException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
