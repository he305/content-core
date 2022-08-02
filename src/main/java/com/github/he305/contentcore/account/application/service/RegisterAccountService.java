package com.github.he305.contentcore.account.application.service;

import com.github.he305.contentcore.account.application.commands.RegisterAccountCommand;

public interface RegisterAccountService {
    void register(RegisterAccountCommand command);
}
