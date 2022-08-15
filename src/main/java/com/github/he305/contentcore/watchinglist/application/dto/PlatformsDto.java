package com.github.he305.contentcore.watchinglist.application.dto;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PlatformsDto {
    List<ContentAccountPlatform> platforms;
}
