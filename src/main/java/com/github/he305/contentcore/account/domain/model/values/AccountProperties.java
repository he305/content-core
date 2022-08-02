package com.github.he305.contentcore.account.domain.model.values;

import com.github.he305.contentcore.shared.validators.StringValidator;
import lombok.Value;

@Value
public class AccountProperties {
    String name;
    String password;

    public AccountProperties(String name, String password) {
        this.name = StringValidator.isNullOrEmpty(name);
        this.password = StringValidator.isNullOrEmpty(password);
    }
}
