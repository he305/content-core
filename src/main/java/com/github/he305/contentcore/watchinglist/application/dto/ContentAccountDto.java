package com.github.he305.contentcore.watchinglist.application.dto;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class ContentAccountDto {
    private String alias;
    private String name;
    private ContentAccountPlatform platform;
}
