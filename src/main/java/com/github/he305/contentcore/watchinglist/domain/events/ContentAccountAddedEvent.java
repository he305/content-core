package com.github.he305.contentcore.watchinglist.domain.events;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ContentAccountAddedEvent {
    private final UUID id;
    private final UUID memberId;
}
