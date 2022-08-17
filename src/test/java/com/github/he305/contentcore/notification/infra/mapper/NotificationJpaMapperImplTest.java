package com.github.he305.contentcore.notification.infra.mapper;

import com.github.he305.contentcore.notification.domain.model.Notification;
import com.github.he305.contentcore.notification.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.notification.domain.model.values.NotificationData;
import com.github.he305.contentcore.notification.infra.data.NotificationJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationJpaMapperImplTest {

    private NotificationJpaMapperImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new NotificationJpaMapperImpl();
    }

    @Test
    void toJpa() {
        UUID id = UUID.randomUUID();
        UUID contentAccountId = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        Notification notification = new Notification(
                id,
                new ContentAccountId(contentAccountId),
                new NotificationData(time, "mes")
        );
        NotificationJpa expected = new NotificationJpa(
                id,
                contentAccountId,
                "mes",
                time
        );

        NotificationJpa actual = underTest.toJpa(notification);
        assertEquals(expected, actual);
    }

    @Test
    void toDomain() {
        UUID id = UUID.randomUUID();
        UUID contentAccountId = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        Notification expected = new Notification(
                id,
                new ContentAccountId(contentAccountId),
                new NotificationData(time, "mes")
        );
        NotificationJpa data = new NotificationJpa(
                id,
                contentAccountId,
                "mes",
                time
        );

        Notification actual = underTest.toDomain(data);
        assertEquals(expected, actual);
    }
}