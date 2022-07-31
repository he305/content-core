package com.github.he305.contentcore.infra.repository;

import com.github.he305.contentcore.domain.model.ContentCreator;
import com.github.he305.contentcore.domain.repository.ContentCreatorRepository;
import com.github.he305.contentcore.infra.dao.ContentCreatorDao;
import com.github.he305.contentcore.infra.jpa.JpaContentCreator;
import com.github.he305.contentcore.infra.mapper.JpaContentCreatorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ContentCreatorRepositoryImpl implements ContentCreatorRepository {
    private final ContentCreatorDao dao;
    private final JpaContentCreatorMapper jpaContentCreatorMapper;

    @Override
    public ContentCreator findById(UUID id) {
        return jpaContentCreatorMapper.toDomain(dao.getReferenceById(id));
    }

    @Override
    public UUID save(ContentCreator contentCreator) {
        JpaContentCreator saved = dao.save(jpaContentCreatorMapper.toJpaEntity(contentCreator));
        return saved.getId();
    }
}
