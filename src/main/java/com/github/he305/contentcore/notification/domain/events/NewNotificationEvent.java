package com.github.he305.contentcore.notification.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class NewNotificationEvent {
    private final UUID notificationId;
    private final UUID contentAccountId;
}
