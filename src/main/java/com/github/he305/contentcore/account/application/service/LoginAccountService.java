package com.github.he305.contentcore.account.application.service;

import com.github.he305.contentcore.account.application.commands.LoginAccountCommand;
import com.github.he305.contentcore.account.application.dto.JwtResponseDto;

public interface LoginAccountService {
    JwtResponseDto execute(LoginAccountCommand command);
}
