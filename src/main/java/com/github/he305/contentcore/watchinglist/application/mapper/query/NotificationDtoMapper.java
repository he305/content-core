package com.github.he305.contentcore.watchinglist.application.mapper.query;

import com.github.he305.contentcore.watchinglist.application.dto.query.NotificationDto;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;

public interface NotificationDtoMapper {
    NotificationDto toDto(NotificationId id);
}
