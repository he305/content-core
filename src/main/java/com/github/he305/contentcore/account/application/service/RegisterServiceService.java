package com.github.he305.contentcore.account.application.service;

import com.github.he305.contentcore.account.application.commands.RegisterServiceCommand;
import com.github.he305.contentcore.account.application.dto.JwtResponseDto;

public interface RegisterServiceService {
    JwtResponseDto execute(RegisterServiceCommand command);
}
