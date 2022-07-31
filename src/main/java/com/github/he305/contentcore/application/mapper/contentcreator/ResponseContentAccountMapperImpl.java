package com.github.he305.contentcore.application.mapper.contentcreator;

import com.github.he305.contentcore.application.dto.ResponseContentAccount;
import com.github.he305.contentcore.domain.model.entities.ContentAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseContentAccountMapperImpl implements ResponseContentAccountMapper {
    private final ContentTypeStringMapper contentTypeStringMapper;

    @Override
    public ResponseContentAccount toDto(ContentAccount contentAccount) {
        return new ResponseContentAccount(contentAccount.getContentAccountInfo().getName(), contentTypeStringMapper.toString(contentAccount.getContentType()));
    }
}
