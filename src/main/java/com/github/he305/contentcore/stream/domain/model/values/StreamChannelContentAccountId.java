package com.github.he305.contentcore.stream.domain.model.values;

import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Value
@Getter
public class StreamChannelContentAccountId {
    UUID id;
}
