package com.github.he305.contentcore.watchinglist.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class WatchingListContentAccountRemovedEvent {
    private final UUID contentAccountId;
    private final UUID memberId;
}
