package com.github.he305.contentcore.streamlist.domain.model.values;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class StreamChannelId {
    private final UUID id;
}
