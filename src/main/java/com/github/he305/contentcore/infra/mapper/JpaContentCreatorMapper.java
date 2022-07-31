package com.github.he305.contentcore.infra.mapper;

import com.github.he305.contentcore.domain.model.ContentCreator;
import com.github.he305.contentcore.infra.jpa.JpaContentCreator;

public interface JpaContentCreatorMapper {
    JpaContentCreator toJpaEntity(ContentCreator contentCreator);

    ContentCreator toDomain(JpaContentCreator jpaContentCreator);
}
