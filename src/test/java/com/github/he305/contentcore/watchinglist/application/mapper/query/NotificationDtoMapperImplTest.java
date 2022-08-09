package com.github.he305.contentcore.watchinglist.application.mapper.query;

import com.github.he305.contentcore.watchinglist.application.dto.query.NotificationDto;
import com.github.he305.contentcore.watchinglist.application.exchange.ExternalNotificationExchangeService;
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
class NotificationDtoMapperImplTest {

    @Mock
    private ExternalNotificationExchangeService externalNotificationExchangeService;
    @InjectMocks
    private NotificationDtoMapperImpl underTest;

    @Test
    void toDto() {
        NotificationId id = new NotificationId(UUID.randomUUID());
        String str = "test";
        Mockito.when(externalNotificationExchangeService.getNotificationDataById(id)).thenReturn(str);
        NotificationDto expected = new NotificationDto(str);

        NotificationDto actual = underTest.toDto(id);
        assertEquals(expected, actual);
    }
}