package com.github.he305.contentcore.notification.infra.jpa;

import com.github.he305.contentcore.notification.infra.data.NotificationJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaNotificationRepository extends JpaRepository<NotificationJpa, UUID> {
}
