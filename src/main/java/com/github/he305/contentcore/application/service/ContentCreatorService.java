package com.github.he305.contentcore.application.service;

import com.github.he305.contentcore.application.dto.AddContentCreator;
import com.github.he305.contentcore.application.dto.ResponseContentCreator;

import java.util.UUID;

public interface ContentCreatorService {
    UUID saveContentCreator(AddContentCreator dto);

    ResponseContentCreator getContentCreatorById(UUID id);
}
