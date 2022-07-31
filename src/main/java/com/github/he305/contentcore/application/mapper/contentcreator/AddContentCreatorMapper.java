package com.github.he305.contentcore.application.mapper.contentcreator;

import com.github.he305.contentcore.application.dto.AddContentCreator;
import com.github.he305.contentcore.domain.model.ContentCreator;

public interface AddContentCreatorMapper {
    ContentCreator toContentCreator(AddContentCreator dto);
}
