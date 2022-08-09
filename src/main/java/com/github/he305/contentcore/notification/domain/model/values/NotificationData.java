package com.github.he305.contentcore.notification.domain.model.values;

import com.github.he305.contentcore.shared.validators.NotInFutureTimeValidator;
import com.github.he305.contentcore.shared.validators.StringValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class NotificationData {
    private final LocalDateTime time;
    private final String message;

    public NotificationData(LocalDateTime time, String message) {
        this.time = NotInFutureTimeValidator.validate(time);
        this.message = StringValidator.isNullOrEmpty(message);
    }
}
