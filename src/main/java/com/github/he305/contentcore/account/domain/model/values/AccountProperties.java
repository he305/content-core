package com.github.he305.contentcore.account.domain.model.values;

import com.github.he305.contentcore.shared.validators.StringValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class AccountProperties {
    private final String username;
    private final String password;

    public AccountProperties(String username, String password) {
        this.username = StringValidator.isNullOrEmpty(username);
        this.password = StringValidator.isNullOrEmpty(password);
    }
}
