package com.github.he305.contentcore.account.application.service;

import com.github.he305.contentcore.account.application.commands.RegisterAccountCommand;
import com.github.he305.contentcore.account.application.dto.JwtResponseDto;

public interface RegisterAccountService {
    JwtResponseDto execute(RegisterAccountCommand command);
}
