package com.github.he305.contentcore.notification.application.exchange;

import com.github.he305.contentcore.notification.domain.exceptions.NotificationNotFoundException;
import com.github.he305.contentcore.notification.domain.model.Notification;
import com.github.he305.contentcore.notification.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.notification.domain.model.values.NotificationData;
import com.github.he305.contentcore.notification.domain.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class NotificationExchangeServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationExchangeService underTest;

    @Test
    void getNotificationDataById_returnBlank() {
        UUID id = UUID.randomUUID();
        Mockito.when(notificationRepository.findById(id)).thenThrow(NotificationNotFoundException.class);

        NotificationData actual = underTest.getNotificationDataById(id);
        assertNull(actual);
    }

    @Test
    void getNotificationDataById_valid() {
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
        NotificationData expected = new NotificationData(time, "mess");
        Notification notification = new Notification(UUID.randomUUID(), new ContentAccountId(UUID.randomUUID()), expected);
        Mockito.when(notificationRepository.findById(id)).thenReturn(notification);

        NotificationData actual = underTest.getNotificationDataById(id);
        assertEquals(expected, actual);
    }
}