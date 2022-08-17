package com.github.he305.contentcore.notification.infra.repository;

import com.github.he305.contentcore.notification.domain.exceptions.NotificationNotFoundException;
import com.github.he305.contentcore.notification.domain.model.Notification;
import com.github.he305.contentcore.notification.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.notification.domain.model.values.NotificationData;
import com.github.he305.contentcore.notification.infra.data.NotificationJpa;
import com.github.he305.contentcore.notification.infra.jpa.JpaNotificationRepository;
import com.github.he305.contentcore.notification.infra.mapper.NotificationJpaMapper;
import com.github.he305.contentcore.shared.events.EventPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificationRepositoryImplTest {

    @Mock
    private JpaNotificationRepository jpaNotificationRepository;
    @Mock
    private NotificationJpaMapper notificationJpaMapper;
    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private NotificationRepositoryImpl underTest;

    @Test
    void save() {
        Notification notification = new Notification("mes", UUID.randomUUID(), LocalDateTime.now(ZoneOffset.UTC));
        Mockito.when(notificationJpaMapper.toJpa(notification)).thenReturn(new NotificationJpa());
        assertDoesNotThrow(() -> underTest.save(notification));
    }

    @Test
    void findById_empty_shouldThrow() {
        UUID id = UUID.randomUUID();
        Mockito.when(jpaNotificationRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotificationNotFoundException.class, () ->
                underTest.findById(id));
    }

    @Test
    void findById_valid() {
        UUID id = UUID.randomUUID();
        NotificationJpa notificationJpa = new NotificationJpa();
        Notification expected = new Notification(id, new ContentAccountId(id), new NotificationData(LocalDateTime.now(ZoneOffset.UTC), "mes"));
        Mockito.when(jpaNotificationRepository.findById(id)).thenReturn(Optional.of(notificationJpa));
        Mockito.when(notificationJpaMapper.toDomain(notificationJpa)).thenReturn(expected);

        Notification actual = underTest.findById(id);
        assertEquals(expected, actual);
    }
}