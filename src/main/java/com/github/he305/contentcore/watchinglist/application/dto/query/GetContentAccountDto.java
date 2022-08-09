package com.github.he305.contentcore.watchinglist.application.dto.query;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class GetContentAccountDto {
    private String name;
    private ContentAccountPlatform platform;
    private int notificationSize;
}
