package com.github.he305.contentcore.application.mapper.contentcreator;

import com.github.he305.contentcore.domain.model.enums.ContentType;

public interface ContentTypeStringMapper {
    ContentType toContentType(String line);

    String toString(ContentType contentType);
}
