package com.github.he305.contentcore.watchinglist.application.exchange;

import com.github.he305.contentcore.notification.application.exchange.NotificationExchangeService;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Mockito.when(notificationExchangeService.getNotificationDataById(id)).thenReturn("test");
        String actual = underTest.getNotificationDataById(new NotificationId(id));
        assertEquals("test", actual);
    }
}