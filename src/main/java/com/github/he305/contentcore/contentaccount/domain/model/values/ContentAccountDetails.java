package com.github.he305.contentcore.contentaccount.domain.model.values;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.shared.validators.StringValidator;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ContentAccountDetails {
    private final String name;
    private final Platform platform;

    public ContentAccountDetails(String name, Platform platform) {
        this.name = StringValidator.isNullOrEmpty(name);
        this.platform = platform;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentAccountDetails details = (ContentAccountDetails) o;
        return name.equals(details.name) && platform == details.platform;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, platform);
    }
}
