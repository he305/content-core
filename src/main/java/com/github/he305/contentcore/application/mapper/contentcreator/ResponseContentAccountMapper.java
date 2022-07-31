package com.github.he305.contentcore.application.mapper.contentcreator;

import com.github.he305.contentcore.application.dto.ResponseContentAccount;
import com.github.he305.contentcore.domain.model.entities.ContentAccount;

public interface ResponseContentAccountMapper {
    ResponseContentAccount toDto(ContentAccount contentAccount);
}
