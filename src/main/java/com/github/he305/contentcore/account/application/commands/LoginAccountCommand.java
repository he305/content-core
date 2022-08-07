package com.github.he305.contentcore.account.application.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginAccountCommand {
    private String username;
    private String password;
}
