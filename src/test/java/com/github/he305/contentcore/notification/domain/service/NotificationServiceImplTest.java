package com.github.he305.contentcore.notification.domain.service;

import com.github.he305.contentcore.notification.domain.model.Notification;
import com.github.he305.contentcore.notification.domain.repository.NotificationRepository;
import com.github.he305.contentcore.shared.common.ContentAccountData;
import com.github.he305.contentcore.shared.events.NewContentAccountDataEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl underTest;


    @Test
    void findById() {
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        Notification notification = new Notification("mess", id, time);
        Mockito.when(notificationRepository.findById(id)).thenReturn(notification);
        Notification actual = underTest.findById(id);
        assertEquals(notification, actual);
    }

    @Test
    void onNewContentAccountDataEvent() {
        UUID id = UUID.randomUUID();
        NewContentAccountDataEvent<ContentAccountData> event = new NewContentAccountDataEvent<>(new ContentAccountData(id, "data"));
        assertDoesNotThrow(() -> underTest.onNewContentAccountDataEvent(event));
    }
}