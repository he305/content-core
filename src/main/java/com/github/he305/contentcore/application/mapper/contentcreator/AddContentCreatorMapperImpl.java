package com.github.he305.contentcore.application.mapper.contentcreator;

import com.github.he305.contentcore.application.dto.AddContentCreator;
import com.github.he305.contentcore.domain.model.ContentCreator;
import com.github.he305.contentcore.domain.model.entities.ContentAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddContentCreatorMapperImpl implements AddContentCreatorMapper {
    @Autowired
    private AddContentAccountMapper contentAccountMapper;

    @Override
    public ContentCreator toContentCreator(AddContentCreator dto) {
        List<ContentAccount> contentAccountList = dto
                .getContent()
                .stream()
                .map(contentAccountMapper::toContentAccount)
                .collect(Collectors.toList());

        return new ContentCreator(dto.getName(), contentAccountList);
    }
}
