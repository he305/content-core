package com.github.he305.contentcore.watchinglist.application.exchange;

import com.github.he305.contentcore.notification.domain.model.values.NotificationData;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalNotificationExchangeService {
    private final com.github.he305.contentcore.notification.application.exchange.NotificationExchangeService externalService;

    public NotificationData getNotificationDataById(NotificationId id) {
        return externalService.getNotificationDataById(id.getId());
    }
}
