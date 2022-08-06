package com.github.he305.contentcore.watchinglist.application.mapper;

import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.service.ContentAccountExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentAccountDtoIdMapperImpl implements ContentAccountDtoIdMapper {
    private final ContentAccountExchangeService contentAccountExchangeService;

    @Override
    public ContentAccountId toContentAccountId(ContentAccountDto dto) {
        ContentAccount account = new ContentAccount(dto.getName(), dto.getPlatform());
        return contentAccountExchangeService.getContentAccountId(account);
    }

    @Override
    public ContentAccountDto toContentAccountDto(ContentAccountId id) {
        ContentAccount account = contentAccountExchangeService.getContentAccount(id);
        return new ContentAccountDto(account.getName(), account.getPlatform());
    }
}
