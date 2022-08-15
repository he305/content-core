package com.github.he305.contentcore.streamlist.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class RequestUpdateStreamListEvent {
    private final UUID memberId;
}
