package com.github.he305.contentcore.watchinglist.application.dto;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentAccountDto {
    private String name;
    private ContentAccountPlatform platform;
}
