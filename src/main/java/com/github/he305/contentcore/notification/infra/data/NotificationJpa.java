package com.github.he305.contentcore.notification.infra.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class NotificationJpa {
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "content_account_id")
    @Type(type = "uuid-char")
    private UUID contentAccountId;

    @Column(name = "message")
    private String message;

    @Column(name = "message_time", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime messageTime;
}
