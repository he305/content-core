package com.github.he305.contentcore.watchinglist.application.exchange;

import com.github.he305.contentcore.notification.application.exchange.NotificationExchangeService;
import com.github.he305.contentcore.notification.domain.model.values.NotificationData;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
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
class ExternalNotificationExchangeServiceTest {
    @Mock
    private NotificationExchangeService notificationExchangeService;

    @InjectMocks
    private ExternalNotificationExchangeService underTest;

    @Test
    void getNotificationDataById() {
        UUID id = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        NotificationData notificationData = new NotificationData(time, "test");
        Mockito.when(notificationExchangeService.getNotificationDataById(id)).thenReturn(notificationData);
        NotificationData actual = underTest.getNotificationDataById(new NotificationId(id));
        assertEquals(notificationData, actual);
    }
}