package com.github.he305.contentcore.account.application.controller;

import com.github.he305.contentcore.account.application.commands.LoginAccountCommand;
import com.github.he305.contentcore.account.application.commands.RegisterAccountCommand;
import com.github.he305.contentcore.account.application.commands.RegisterServiceCommand;
import com.github.he305.contentcore.account.application.dto.JwtRefreshTokenDto;
import com.github.he305.contentcore.account.application.dto.JwtResponseDto;
import com.github.he305.contentcore.account.application.dto.LoginRequestDto;
import com.github.he305.contentcore.account.application.dto.RegisterServiceDto;
import com.github.he305.contentcore.account.application.service.LoginAccountService;
import com.github.he305.contentcore.account.application.service.RefreshTokenService;
import com.github.he305.contentcore.account.application.service.RegisterAccountService;
import com.github.he305.contentcore.account.application.service.RegisterServiceService;
import com.github.he305.contentcore.shared.exceptions.ContentCoreException;
import com.github.he305.contentcore.shared.validators.StringValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterServiceService registerServiceService;
    private final RegisterAccountService registerAccountService;
    private final LoginAccountService loginAccountService;
    private final RefreshTokenService refreshTokenService;
    @Value("${auth.service-register-key}")
    private String serviceRegisterKey;

    @PostMapping("/login")
    public JwtResponseDto login(@RequestBody LoginRequestDto dto) {
        LoginAccountCommand command = new LoginAccountCommand(dto.getUsername(), dto.getPassword());
        return loginAccountService.execute(command);
    }

    @PostMapping("/register")
    public JwtResponseDto register(@RequestBody LoginRequestDto dto) {
        String username = StringValidator.isNullOrEmpty(dto.getUsername());
        String password = StringValidator.isNullOrEmpty(dto.getPassword());
        RegisterAccountCommand command = new RegisterAccountCommand(username, password);
        return registerAccountService.execute(command);
    }

    @PostMapping("/register-service")
    public JwtResponseDto registerService(@RequestBody RegisterServiceDto dto) {
        if (!dto.getServiceRegisterKey().equals(serviceRegisterKey)) {
            throw new ContentCoreException("Service register key is invalid");
        }

        String username = StringValidator.isNullOrEmpty(dto.getUsername());
        String password = StringValidator.isNullOrEmpty(dto.getPassword());
        RegisterServiceCommand command = new RegisterServiceCommand(username, password);
        return registerServiceService.execute(command);
    }

    @PostMapping("/refresh")
    public JwtResponseDto refreshToken(@RequestBody JwtRefreshTokenDto refreshTokenDto) {
        return refreshTokenService.refreshToken(refreshTokenDto);
    }
}
