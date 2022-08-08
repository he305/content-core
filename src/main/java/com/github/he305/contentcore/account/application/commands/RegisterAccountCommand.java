package com.github.he305.contentcore.account.application.commands;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class RegisterAccountCommand {
    private String username;
    private String password;
}
