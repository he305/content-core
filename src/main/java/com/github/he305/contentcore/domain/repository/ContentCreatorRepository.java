package com.github.he305.contentcore.domain.repository;

import com.github.he305.contentcore.domain.model.ContentCreator;

import java.util.UUID;

public interface ContentCreatorRepository {
    ContentCreator findById(UUID id);

    UUID save(ContentCreator contentCreator);
}
