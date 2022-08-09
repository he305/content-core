package com.github.he305.contentcore.watchinglist.application.dto.query;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class GetNotificationForContentAccountDto {
    private List<NotificationDto> notificationDtoList;
}
