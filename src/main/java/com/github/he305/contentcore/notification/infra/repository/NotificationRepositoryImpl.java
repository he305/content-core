package com.github.he305.contentcore.notification.infra.repository;

import com.github.he305.contentcore.notification.domain.exceptions.NotificationNotFoundException;
import com.github.he305.contentcore.notification.domain.model.Notification;
import com.github.he305.contentcore.notification.domain.repository.NotificationRepository;
import com.github.he305.contentcore.notification.infra.data.NotificationJpa;
import com.github.he305.contentcore.notification.infra.jpa.JpaNotificationRepository;
import com.github.he305.contentcore.notification.infra.mapper.NotificationJpaMapper;
import com.github.he305.contentcore.shared.events.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
    private final JpaNotificationRepository jpaNotificationRepository;
    private final NotificationJpaMapper notificationJpaMapper;
    private final EventPublisher eventPublisher;

    @Override
    public void save(Notification notification) {
        NotificationJpa jpa = notificationJpaMapper.toJpa(notification);
        jpaNotificationRepository.save(jpa);
        eventPublisher.publishEvent(notification.getEvents());
    }

    @Override
    public Notification findById(UUID id) {
        Optional<NotificationJpa> jpa = jpaNotificationRepository.findById(id);
        if (jpa.isEmpty()) {
            throw new NotificationNotFoundException("Notification with id " + id + " not found");
        }

        return notificationJpaMapper.toDomain(jpa.get());
    }
}
