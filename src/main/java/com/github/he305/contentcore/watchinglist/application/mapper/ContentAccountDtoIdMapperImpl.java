package com.github.he305.contentcore.watchinglist.application.mapper;

import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
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
    public ContentAccountEntry toContentAccountEntry(ContentAccountDto dto) {
        ContentAccount account = new ContentAccount(dto.getName(), dto.getPlatform());
        ContentAccountId id = contentAccountExchangeService.getContentAccountId(account);
        return new ContentAccountEntry(dto.getAlias(), id);
    }

    @Override
    public ContentAccountDto toContentAccountDto(ContentAccountEntry entry) {
        ContentAccount account = contentAccountExchangeService.getContentAccount(entry.getContentAccountId());
        return new ContentAccountDto(entry.getAlias(), account.getName(), account.getPlatform());
    }
}
