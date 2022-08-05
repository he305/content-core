package com.github.he305.contentcore.contentaccount.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ContentAccountBecameFrozenEvent {
    private final UUID contentAccountId;
}

