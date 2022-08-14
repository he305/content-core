package com.github.he305.contentcore.watchinglist.application.mapper.query;

import com.github.he305.contentcore.notification.domain.model.values.NotificationData;
import com.github.he305.contentcore.watchinglist.application.dto.query.NotificationDto;
import com.github.he305.contentcore.watchinglist.application.exchange.ExternalNotificationExchangeService;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationDtoMapperImpl implements NotificationDtoMapper {
    private final ExternalNotificationExchangeService externalNotificationExchangeService;

    @Override
    public NotificationDto toDto(NotificationId id) {
        NotificationData data = externalNotificationExchangeService.getNotificationDataById(id);
        return new NotificationDto(data.getTime(), data.getMessage());
    }
}
