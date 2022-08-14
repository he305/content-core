package com.github.he305.contentcore.notification.application.exchange;

import com.github.he305.contentcore.notification.domain.exceptions.NotificationNotFoundException;
import com.github.he305.contentcore.notification.domain.model.values.NotificationData;
import com.github.he305.contentcore.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationExchangeService {
    private final NotificationRepository notificationRepository;

    public NotificationData getNotificationDataById(UUID id) {
        try {
            return notificationRepository.findById(id).getData();
        } catch (NotificationNotFoundException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }
}
