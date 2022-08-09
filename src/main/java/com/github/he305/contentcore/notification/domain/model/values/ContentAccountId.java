package com.github.he305.contentcore.notification.domain.model.values;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ContentAccountId {
    private final UUID id;
}
