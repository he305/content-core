package com.github.he305.contentcore.application.service;

import com.github.he305.contentcore.application.dto.AddContentCreator;
import com.github.he305.contentcore.application.dto.ResponseContentCreator;
import com.github.he305.contentcore.application.mapper.contentcreator.AddContentCreatorMapper;
import com.github.he305.contentcore.application.mapper.contentcreator.ResponseContentCreatorMapper;
import com.github.he305.contentcore.domain.repository.ContentCreatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContentCreatorServiceImpl implements ContentCreatorService {
    private final AddContentCreatorMapper addContentCreatorMapper;
    private final ResponseContentCreatorMapper responseContentCreatorMapper;
    private final ContentCreatorRepository repository;

    @Override
    public UUID saveContentCreator(AddContentCreator dto) {
        return repository.save(addContentCreatorMapper.toContentCreator(dto));
    }

    @Override
    public ResponseContentCreator getContentCreatorById(UUID id) {
        return responseContentCreatorMapper.toDto(repository.findById(id));
    }
}
