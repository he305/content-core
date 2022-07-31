package com.github.he305.contentcore.application.mapper.contentcreator;

import com.github.he305.contentcore.application.dto.AddContentAccount;
import com.github.he305.contentcore.domain.model.entities.ContentAccount;

public interface AddContentAccountMapper {

    ContentAccount toContentAccount(AddContentAccount dto);
}
