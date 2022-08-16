package com.github.he305.contentcore.watchinglist.domain.model.values;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Value
@Getter
public class WatchingListContentAccountId {
    UUID id;
}
