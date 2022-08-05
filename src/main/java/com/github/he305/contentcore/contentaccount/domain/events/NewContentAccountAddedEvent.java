package com.github.he305.contentcore.contentaccount.domain.events;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class NewContentAccountAddedEvent {
    private final UUID contentAccountId;
    private final Platform platform;
}
