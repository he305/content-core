package com.github.he305.contentcore.application.mapper.contentcreator;

import com.github.he305.contentcore.application.dto.ResponseContentCreator;
import com.github.he305.contentcore.domain.model.ContentCreator;

public interface ResponseContentCreatorMapper {
    ResponseContentCreator toDto(ContentCreator contentCreator);
}
