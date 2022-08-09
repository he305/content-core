package com.github.he305.contentcore.watchinglist.domain.model.values;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class NotificationId {
    private UUID id;
}
