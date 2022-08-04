package com.github.he305.contentcore.watchinglist.domain.model.values;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class MemberId {
    private final UUID id;

    public MemberId(UUID id) {
        this.id = id;
    }
}
