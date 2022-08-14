package com.github.he305.contentcore.watchinglist.application.dto.query;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class NotificationDto {
    private LocalDateTime time;
    private String data;
}
