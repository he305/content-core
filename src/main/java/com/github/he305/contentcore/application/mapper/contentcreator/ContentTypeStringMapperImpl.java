package com.github.he305.contentcore.application.mapper.contentcreator;

import com.github.he305.contentcore.domain.model.enums.ContentType;
import org.springframework.stereotype.Component;

@Component
public class ContentTypeStringMapperImpl implements ContentTypeStringMapper {
    @Override
    public ContentType toContentType(String line) {
        line = line.toUpperCase();
        return ContentType.valueOf(line);
    }

    @Override
    public String toString(ContentType contentType) {
        return contentType.toString();
    }
}
