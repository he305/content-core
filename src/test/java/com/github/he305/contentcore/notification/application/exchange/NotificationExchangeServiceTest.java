package com.github.he305.contentcore.notification.application.exchange;

import com.github.he305.contentcore.notification.domain.exceptions.NotificationNotFoundException;
import com.github.he305.contentcore.notification.domain.model.Notification;
import com.github.he305.contentcore.notification.domain.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        String actual = underTest.getNotificationDataById(id);
        assertEquals("", actual);
    }

    @Test
    void getNotificationDataById_valid() {
        UUID id = UUID.randomUUID();
        String expected = "expected";
        Notification notification = new Notification(expected, UUID.randomUUID(), LocalDateTime.now());
        Mockito.when(notificationRepository.findById(id)).thenReturn(notification);

        String actual = underTest.getNotificationDataById(id);
        assertEquals(expected, actual);
    }
}