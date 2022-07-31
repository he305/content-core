package com.github.he305.contentcore.application.mapper.contentcreator;

import com.github.he305.contentcore.application.dto.AddContentAccount;
import com.github.he305.contentcore.domain.model.entities.ContentAccount;
import com.github.he305.contentcore.domain.model.entities.ContentAccountInfo;
import com.github.he305.contentcore.domain.model.enums.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddContentAccountMapperImpl implements AddContentAccountMapper {
    @Autowired
    ContentTypeStringMapper contentTypeStringMapper;

    @Override
    public ContentAccount toContentAccount(AddContentAccount dto) {
        ContentType contentType = contentTypeStringMapper.toContentType(dto.getContentAccountType());
        return new ContentAccount(new ContentAccountInfo(dto.getNickname()), contentType);
    }
}
