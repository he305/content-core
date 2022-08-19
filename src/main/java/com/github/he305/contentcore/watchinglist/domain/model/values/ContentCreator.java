package com.github.he305.contentcore.watchinglist.domain.model.values;

import com.github.he305.contentcore.shared.validators.StringValidator;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
public class ContentCreator {
    String name;

    public ContentCreator(String name) {
        this.name = StringValidator.isNullOrEmpty(name);
    }
}
