package com.github.he305.contentcore.streamlist.application.query;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class GetStreamListQuery {
    private final UUID memberId;
}
