package com.github.he305.contentcore.notification.domain.service;

import com.github.he305.contentcore.notification.domain.model.Notification;
import com.github.he305.contentcore.notification.domain.repository.NotificationRepository;
import com.github.he305.contentcore.shared.common.ContentAccountData;
import com.github.he305.contentcore.shared.events.NewContentAccountDataEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public Notification findById(UUID id) {
        return notificationRepository.findById(id);
    }

    @EventListener
    public void onNewContentAccountDataEvent(NewContentAccountDataEvent<ContentAccountData> event) {
        LocalDateTime time = LocalDateTime.now();
        Notification notification = new Notification(event.getData().getStringData(), event.getData().getContentAccountId(), time);
        notificationRepository.save(notification);
    }
}
