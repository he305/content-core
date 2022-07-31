package com.github.he305.contentcore.domain.model.entities;

import com.github.he305.contentcore.domain.model.enums.ContentType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
public class ContentAccount extends BaseEntity {
    private final ContentAccountInfo contentAccountInfo;
    private final ContentType contentType;

    public ContentAccount(UUID id, ContentAccountInfo contentAccountInfo, ContentType contentType) {
        this.id = id;
        this.contentAccountInfo = contentAccountInfo;
        this.contentType = contentType;
    }

    public ContentAccount(ContentAccountInfo contentAccountInfo, ContentType contentType) {
        this.contentAccountInfo = contentAccountInfo;
        this.contentType = contentType;
    }
}
