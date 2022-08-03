package com.github.he305.contentcore.watchinglist.domain.model.values;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@EqualsAndHashCode
public class ContentCreator {
    String name;

    public ContentCreator(String name) {
        this.name = name;
    }
}
