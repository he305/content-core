package com.github.he305.contentcore.watchinglist.application.dto;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ContentAccountDto {
    private String name;
    private ContentAccountPlatform platform;
}
